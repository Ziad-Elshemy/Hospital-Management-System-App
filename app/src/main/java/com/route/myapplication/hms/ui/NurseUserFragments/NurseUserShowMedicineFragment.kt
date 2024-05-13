package com.route.myapplication.hms.ui.DoctorUserFragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.annotations.SerializedName
import com.route.myapplication.hms.R
import com.route.myapplication.hms.ui.NurseUserFragments.ShowMedicineAdapter
import com.route.myapplication.hms.ui.api.ApiManager
import com.route.myapplication.hms.ui.api.Model.*
import com.route.myapplication.hms.ui.ui.DoctorsDetails
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NurseUserShowMedicineFragment : Fragment() {

//    lateinit var notificationNurseBtn : Button
//    lateinit var scheduleNotificationNurseBtn : Button
//    val CHANNEL_ID = "channelId"
//    val CHANNEL_NAME = "channelName"
//    val NOTIFICATION_ID = 0

    lateinit var doctor_name : EditText
    lateinit var patient_name : EditText



    var medicinesList: List<PrescriptionListItem?>? = arrayListOf()

    lateinit var recyclerView: RecyclerView
    val adapter = ShowMedicineAdapter(null)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nurse_user_show_medicine, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        notificationNurseBtn = requireView().findViewById(R.id.nurse_btn_notification)
//        scheduleNotificationNurseBtn = requireView().findViewById(R.id.nurse_btn_schedule_notification)
//        createNotificationChannel()
//
//        val intent = Intent(requireContext(),UserNurseActivity::class.java)
//        val pendingIntent = TaskStackBuilder.create(requireContext()).run {
//            addNextIntentWithParentStack(intent)
//            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        doctor_name = requireView().findViewById(R.id.doctor_name)
        patient_name = requireView().findViewById(R.id.patient_name)


        recyclerView = requireView().findViewById(R.id.Nurse_medicine_recyclerView)
        recyclerView.adapter = adapter

        getLastPrescription(7)



        }

    private fun getLastPrescription(indoorRecordId: Int) {

        ApiManager.getApis().getLastPrescription(indoorRecordId)
            .enqueue(object : Callback<GetLastPrescriptionResponse> {
                override fun onFailure(
                    call: Call<GetLastPrescriptionResponse>, t: Throwable
                ) {
                    Toast.makeText(requireContext(), "Throwable" + t.localizedMessage, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<GetLastPrescriptionResponse>,
                    response: Response<GetLastPrescriptionResponse>
                ) {
                    doctor_name.setText(response.body()?.result?.doctorFullName)
                    getpatientById(response.body()?.result?.prescription?.patientId!!)
                    medicinesList = response.body()?.result?.prescription?.prescriptionItems
                    adapter.changeData(medicinesList)
                }
            })
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
                    patient_name.setText(response.body()?.firstName+" "+response.body()?.lastName)

                }
            })

    }


//        val notification = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
//            .setContentTitle("Awesome Notification")
//            .setContentText("This is the content text")
//            .setSmallIcon(R.drawable.ic_star)
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setContentIntent(pendingIntent)
//            .build()
//
//        val notificationManager = NotificationManagerCompat.from(requireContext())
//
//        notificationNurseBtn.setOnClickListener {
//            notificationManager.notify(NOTIFICATION_ID, notification)
//        }
//        scheduleNotificationNurseBtn.setOnClickListener{
//            getParentFragmentManager().beginTransaction()
//                .replace(R.id.fragment_container, ScheduleNotificationFragment())
//                .addToBackStack("")
//                .commit()
//        }

 //   }

//    fun createNotificationChannel(){
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            val channel = NotificationChannel(CHANNEL_ID,CHANNEL_NAME,
//                NotificationManager.IMPORTANCE_DEFAULT).apply {
//                lightColor = Color.GREEN
//                enableLights(true)
//            }
//            val manger = getActivity()?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            manger.createNotificationChannel(channel)
//        }
  //  }


    }