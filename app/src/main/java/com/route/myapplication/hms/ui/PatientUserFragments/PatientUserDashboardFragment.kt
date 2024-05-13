package com.route.myapplication.hms.ui.PatientUserFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.route.myapplication.hms.Constants
import com.route.myapplication.hms.R
import com.route.myapplication.hms.ui.DoctorUserFragments.DoctorProfileFragment
import com.route.myapplication.hms.ui.DoctorUserFragments.DoctorUserAppointmentsFragment
import com.route.myapplication.hms.ui.api.ApiManager
import com.route.myapplication.hms.ui.api.Model.GetPatientByIdResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PatientUserDashboardFragment : Fragment() {

    var patient : GetPatientByIdResponse = GetPatientByIdResponse()
    lateinit var firstName: TextView
    lateinit var lastName: TextView


    lateinit var editIcon : ImageView
    lateinit var editText : TextView
    lateinit var appointmentCard : CardView
    lateinit var newAppointmentsCard : CardView

    lateinit var prescriptionCard : CardView
    lateinit var testsCard : CardView
    lateinit var scansCard : CardView

    lateinit var medicalRecordsCard : CardView


    val patientId = Constants.userID


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_patient_user_dashboard, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editIcon = requireView().findViewById(R.id.edit_icon)
        editText = requireView().findViewById(R.id.editText)

        firstName = requireView().findViewById(R.id.fname)
        lastName = requireView().findViewById(R.id.lname)


        appointmentCard = requireView().findViewById(R.id.appointmentsCard)
        newAppointmentsCard = requireView().findViewById(R.id.newAppointmentsCard)
        prescriptionCard = requireView().findViewById(R.id.prescriptionCard)
        testsCard = requireView().findViewById(R.id.testsCard)
        scansCard = requireView().findViewById(R.id.scansCard)
        medicalRecordsCard = requireView().findViewById(R.id.medicalRecordsCard)



        getpatientById(patientId.toInt())
        editIcon.setOnClickListener {
            pushFragment(PatientProfileFragment())

        }
        newAppointmentsCard.setOnClickListener {
            pushFragment(MakeAppointmentFragment())
        }

        prescriptionCard.setOnClickListener{
            pushFragment(PatientUserShowPrescriptionsFragment())
        }

        editText.setOnClickListener {
            pushFragment(PatientProfileFragment())

        }

        appointmentCard.setOnClickListener {
            pushFragment(PatientUserShowAppointmentsFragment())

        }


        testsCard.setOnClickListener {
            pushFragment(PatientUserShowTestsFragment())

        }

        scansCard.setOnClickListener {
            pushFragment(PatientUserShowSansFragment())

        }

        medicalRecordsCard.setOnClickListener {
            pushFragment(PatientUserShowRecordsFragment())

        }


    }

    private fun pushFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack("back")
            .commit()
    }

    private fun getpatientById(patientId: Int) {

        ApiManager.getApis().getPatientByIdResponse(patientId)
            .enqueue(object : Callback<GetPatientByIdResponse> {
                override fun onFailure(
                    call: Call<GetPatientByIdResponse>,
                    t: Throwable
                ) {
                    Toast.makeText(
                        requireContext(),
                        "Throwable" + t.localizedMessage,
                        Toast.LENGTH_LONG
                    ).show()

                }

                override fun onResponse(
                    call: Call<GetPatientByIdResponse>,
                    response: Response<GetPatientByIdResponse>
                ) {
                    patient = response.body()!!
                    firstName.setText(patient.firstName)
                    lastName.setText(patient.lastName)
                }

            })
    }




}