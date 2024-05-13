package com.route.myapplication.hms.ui.DoctorUserFragments

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.route.myapplication.hms.*
import com.route.myapplication.hms.ui.api.ApiManager
import com.route.myapplication.hms.ui.api.Model.GetDoctorAppointmentResponseItem
import com.route.myapplication.hms.ui.ui.AppointmentsDetails
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.Month
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class DoctorUserAppointmentsFragment : Fragment() {

    var AppointmentsList: List<GetDoctorAppointmentResponseItem> = arrayListOf()
    lateinit var recyclerView: RecyclerView
    lateinit var adapter : DoctorAppointmentAdapter

    var appointmentsTimeSlot : String = "04:20"
    var appointmentsPatientname : String = "String"

    var Appointments: MutableList<AppointmentsDetails> = arrayListOf()
    lateinit var timeConvension:String

    val doctorId = Constants.userID.toInt()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctor_user_appointments, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createNotificationChannel()

        recyclerView =requireView().findViewById(R.id.consultations_recycler)
        adapter = DoctorAppointmentAdapter(Appointments)
        recyclerView.adapter = adapter
       // val doctorId =1
        val todayDate = LocalDateTime.now().toLocalDate().toString()
//        val todayDate = LocalDate.of(2022,Month.JULY,30).toString()
        getAppointments(doctorId,todayDate)

        Log.e("timee",appointmentsTimeSlot.substring(4,5))


        adapter.onLabImgClickListener = object : DoctorAppointmentAdapter.OnImageClickListener{
            override fun onImageClick(pos: Int, item: AppointmentsDetails) {
                val bundle1 = Bundle()
                bundle1.putInt("patientId", item.patientID)

                val dialog : DialogFragment = lab_dialog_appoinments_fragment()
                dialog.arguments  = bundle1

                dialog.show(parentFragmentManager,"custom dialog")

            }
        }

        adapter.onScanImgClickListener = object : DoctorAppointmentAdapter.OnImageClickListener{
            override fun onImageClick(pos: Int, item: AppointmentsDetails) {
                val bundle2 = Bundle()
                bundle2.putInt("patientId", item.patientID)

                val dialog : DialogFragment = scan_dialog_appointment_fragment()
                dialog.arguments  = bundle2

                dialog.show(parentFragmentManager,"custom dialog")

            }
        }

        adapter.onRecordImgClickListener = object : DoctorAppointmentAdapter.OnImageClickListener{
            override fun onImageClick(pos: Int, item: AppointmentsDetails) {
                val bundle = Bundle()
                bundle.putInt("patientId", item.patientID)

                val recordFragment : Fragment = DoctorUserPatientIndoorRecordFragment()
                recordFragment.arguments  = bundle
                pushFragment(recordFragment)            }
        }

        adapter.onPrescriptionImgClickListener = object : DoctorAppointmentAdapter.OnImageClickListener{
            override fun onImageClick(pos: Int, item: AppointmentsDetails) {
                val bundle = Bundle()
                bundle.putInt("patientId", item.patientID)

                val dialog : DialogFragment = prescription_dialog_appointment_fragment()
                dialog.arguments  = bundle

                dialog.show(parentFragmentManager,"custom dialog")
            }
        }

}

    @RequiresApi(Build.VERSION_CODES.M)
    private fun scheduleNotification() {
        val intent = Intent(requireContext(), NotificationDoctor::class.java)
        val title = "Hi Doctor"
        val message = "It's for you about your patient $appointmentsPatientname"
        intent.putExtra(scheduleDoctorTitleExtra,title)
        intent.putExtra(scheduleDoctorMessageExtra,message)

        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            scheduleDoctorNotificationId,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getActivity()?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getTime()
        Log.e("alarmTimeD",time.toString())
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )


//        showAlert(time,title,message)
    }

    private fun showAlert(time: Long, title: String, message: String) {
        val date = Date(time)
        val dateFormat = android.text.format.DateFormat.getLongDateFormat(requireContext())
        val timeFormat = android.text.format.DateFormat.getTimeFormat(requireContext())

        AlertDialog.Builder(requireContext())
            .setTitle("Notification Scheduled")
            .setMessage(
//                "Title: " + "Notification Created" +
                "Title: " + title +

//                        "\nMessage: " + "It's a notification for your patient" +
                        "\nMessage: " + message +
                        "\nAt: " + dateFormat.format(date) + " " + timeFormat.format(date)
//                        ""
            )
            .setPositiveButton("Okay"){_,_,->}
            .show()
    }

    private fun getTime(): Long {
        var hour : Int
        var minute : Int
        Log.e("timee1",appointmentsTimeSlot.substring(0,1).toInt().toString())
        if (appointmentsTimeSlot.substring(0,1).toInt()==0) {
            hour = appointmentsTimeSlot.substring(1, 2).toInt()
            Log.e("time_hour",hour.toString())
        }else {
            hour = appointmentsTimeSlot.substring(0, 2).toInt()
            Log.e("time_hour", hour.toString())
        }
        if (appointmentsTimeSlot.substring(3,4).toInt()==0){
            minute = appointmentsTimeSlot.substring(4,5).toInt()
            Log.e("time_minute", minute.toString())
        }else {
            minute = appointmentsTimeSlot.substring(3, 5).toInt()
            Log.e("time_minute", minute.toString())
        }



        val day = 30
        val month = 6
        val year = 2022

        val calender = Calendar.getInstance()
        calender.set(year,month,day,hour,minute)
        return calender.timeInMillis
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val name = "notification name"
        val desc = "a description of the notification"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(scheduleDoctorChannelId,name,importance)
        channel.description = desc
        val notificationManager = getActivity()?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun pushFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack("back")
            .commit()
    }

    private fun getAppointments(DoctorId: Int,TodayDate : String) {

        ApiManager.getApis().getDoctorAppointments(DoctorId, TodayDate ).enqueue(object:Callback<ArrayList<GetDoctorAppointmentResponseItem>>{
                override fun onFailure(
                    call: Call<ArrayList<GetDoctorAppointmentResponseItem>>,
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
                    call: Call<ArrayList<GetDoctorAppointmentResponseItem>>,
                    response: Response<ArrayList<GetDoctorAppointmentResponseItem>>
                ) {
                    val title = "Hi Doctor"
                    val message = "Some Notifications are Created"
                    val todayDateNow = LocalDateTime.now().toLocalTime().toString()
                    Log.e("timeNow",todayDateNow)
                    for (item in response.body()!!) {
//                        appointmentsTimeSlot = response.body()?.get(4)?.slotTime.toString()
                        appointmentsTimeSlot = item.slotTime.toString()
//                        appointmentsPatientname = response.body()?.get(4)?.patientName.toString()
                        appointmentsPatientname = item.patientName.toString()

//                    Log.e("timeApi",appointmentsTimeSlot)
                        if ((todayDateNow.substring(0,2).toInt())*60+(todayDateNow.substring(3,5).toInt())<
                            (appointmentsTimeSlot.substring(0, 2).toInt())*60+( appointmentsTimeSlot.substring(3, 5).toInt())){
                            scheduleNotification()
                            Log.e("Notification at ",appointmentsTimeSlot)

                        }
                    }
                    val time = getTime()
//                    showAlert(time,title,message)

                    AppointmentsList = response.body()!!.toList()

                    Appointments.clear()
                    for (appointment in AppointmentsList) {
                        var local_time = LocalTime.parse(appointment.slotTime, DateTimeFormatter.ISO_LOCAL_TIME)
                        var appointmentTime = ""

                        if(local_time.hour.toString()=="00"){
                            timeConvension="AM"
                            local_time=local_time.plusHours(12)
                            appointmentTime = local_time.toString().substring(0,5)
                        }
                        else if(local_time.hour.toString()=="12"){
                            timeConvension="PM"
                            appointmentTime = local_time.toString().substring(0,5)
                        }

                        else if (local_time.hour>12)
                        {
                            timeConvension="PM"
                            local_time = local_time.minusHours(12)
                            appointmentTime = local_time.toString().substring(0,5)
                        }

                        else {
                            timeConvension = "AM"
                            appointmentTime = local_time.toString().substring(0,5)
                        }

                        Appointments.add(AppointmentsDetails(appointment.patientId!!,appointment.patientName.toString(),
                            appointment.gender.toString(),appointment.age!!,timeConvension,appointmentTime))
                    }
                    Log.e("AppointmentList",Appointments.toString())
                    adapter.changeData(Appointments)
                }
        })

    }

    }