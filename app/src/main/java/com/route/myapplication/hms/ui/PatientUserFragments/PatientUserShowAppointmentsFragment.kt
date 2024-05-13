package com.route.myapplication.hms.ui.PatientUserFragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.route.myapplication.hms.Constants
import com.route.myapplication.hms.R
import com.route.myapplication.hms.ui.DoctorUserFragments.PrescriptionListAdapter
import com.route.myapplication.hms.ui.api.ApiManager
import com.route.myapplication.hms.ui.api.Model.GetAllPrescriptionsOfPatientResponseItem
import com.route.myapplication.hms.ui.api.Model.GetPatientAppointmentsResponseItem
import com.route.myapplication.hms.ui.api.Model.GetPatientByIdResponse
import com.route.myapplication.hms.ui.ui.AppointmentsDetails
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class PatientUserShowAppointmentsFragment : Fragment() {

    var appointmentList: List<GetPatientAppointmentsResponseItem> = arrayListOf()
    var Appointments: MutableList<PatientUpcomingAppointmentDetails> = arrayListOf()

    lateinit var recyclerView: RecyclerView
    val adapter = AppointmentListAdapter(null)
    lateinit var timeConvension:String

    val patientId = Constants.userID
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_patient_user_show_appointments, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = requireView().findViewById(R.id.appointments_recycler)



        getPatientAppointment(patientId.toInt())
        recyclerView.adapter = adapter

    }


    private fun getPatientAppointment(patientId: Int) {

        ApiManager.getApis().getAppointmentsByPatientId(patientId)
        .enqueue(object : Callback<ArrayList<GetPatientAppointmentsResponseItem>> {
                override fun onFailure(
                    call: Call<ArrayList<GetPatientAppointmentsResponseItem>>,
                    t: Throwable
                ) {
                    Toast.makeText(
                        requireContext(),
                        "Throwable" + t.localizedMessage,
                        Toast.LENGTH_LONG
                    ).show()

                }

                @RequiresApi(Build.VERSION_CODES.O)
                override fun onResponse(
                    call: Call<ArrayList<GetPatientAppointmentsResponseItem>>,
                    response: Response<ArrayList<GetPatientAppointmentsResponseItem>>
                ) {
                    appointmentList = response.body()!!.toList()
                    Appointments.clear()
                    for (appointment in appointmentList) {
                        var local_time = LocalTime.parse(appointment.slotTime, DateTimeFormatter.ISO_LOCAL_TIME)
                        var appointmentTime = ""

                        if(local_time.hour.toString()=="00"){
                            timeConvension="AM"
                            local_time=local_time.plusHours(12)
                            appointmentTime = local_time.toString().substring(0,5)
                        }

                        if (local_time.hour>=12)
                        {
                            timeConvension="PM"
                            local_time = local_time.minusHours(12)
                            appointmentTime = local_time.toString().substring(0,5)
                        }
                        else {
                            timeConvension = "AM"
                            appointmentTime = local_time.toString().substring(0,5)
                        }

                        Appointments.add(PatientUpcomingAppointmentDetails
                            (appointment.doctorName!!,appointmentTime, appointment.appointmentDate!!,
                                timeConvension)
                        )
                    }
                    adapter.changeData(Appointments)
                }
            })
    }



}