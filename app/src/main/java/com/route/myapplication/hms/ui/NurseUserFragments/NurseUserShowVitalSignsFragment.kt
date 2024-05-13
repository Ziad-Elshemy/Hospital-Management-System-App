package com.route.myapplication.hms.ui.NurseUserFragments

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.route.myapplication.hms.R
import com.route.myapplication.hms.ui.api.ApiManager
import com.route.myapplication.hms.ui.api.Model.GetDoctorAppointmentResponseItem
import com.route.myapplication.hms.ui.api.Model.GetPatientByIdResponse
import com.route.myapplication.hms.ui.api.Model.GetVitalsResponse
import com.route.myapplication.hms.ui.api.Model.getVitalsResponseItem
import com.route.myapplication.hms.ui.ui.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class NurseUserShowVitalSignsFragment : Fragment() {

    var patient : GetPatientByIdResponse = GetPatientByIdResponse()

    var vitalsList: List<getVitalsResponseItem> = arrayListOf()
    var vitalSigns: MutableList<NurseVitalSignsDetails> = arrayListOf()

    lateinit var patientName : TextView

    lateinit var date_in: EditText
    lateinit var end_date_in: EditText
    
    lateinit var getVitals_btn : Button
    lateinit var no_items_tv : TextView

    lateinit var progressBar : ProgressBar
    lateinit var linearLayout : LinearLayout

    lateinit var recyclerView: RecyclerView
    lateinit var adapter : NurseVitalSignsAdapter
    lateinit var items: MutableList<NurseVitalSignsDetails>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vital_signs_nurse, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()

        val bundle = requireArguments()
        val patientId: Int = bundle.getInt("patientId")

        getpatientById(patientId)

        date_in.setOnClickListener {
            showDateDialog(date_in) }

        end_date_in.setOnClickListener {
            showDateDialog(end_date_in) }


        getVitals_btn.setOnClickListener {
            if (date_in.text.toString().isNotEmpty() && end_date_in.text.toString().isNotEmpty())
            {
                val local_in_date =LocalDate.parse(date_in.text.toString(), DateTimeFormatter.ISO_LOCAL_DATE)
                val local_end_date =LocalDate.parse(end_date_in.text.toString(), DateTimeFormatter.ISO_LOCAL_DATE)
//                val zero_local_time = LocalTime.parse("0", DateTimeFormatter.ISO_LOCAL_TIME)

//                val zero_local_time = LocalTime.of(0,0,0)
//                val local_Startdate_time = LocalDateTime.of(local_in_date, zero_local_time)
//                val local_Enddate_time = LocalDateTime.of(local_end_date, zero_local_time)

                getVitals(patientId,local_in_date.toString(),local_end_date.toString())
            }
            else{
                 Toast.makeText(requireContext(),"please make sure you entered both start and end date",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun initViews(){
        progressBar = requireView().findViewById(R.id.progress_bar)
        linearLayout = requireView().findViewById(R.id.layout_vitals_titles)

        patientName = requireView().findViewById(R.id.patient_name)

        getVitals_btn = requireView().findViewById(R.id.getVitals_btn)
        no_items_tv = requireView().findViewById(R.id.no_items_tv)

        date_in=requireView().findViewById(R.id.date_input)
        end_date_in=requireView().findViewById(R.id.end_date_input)

        date_in.inputType = InputType.TYPE_NULL;
        end_date_in.inputType = InputType.TYPE_NULL;

        recyclerView =requireView().findViewById(R.id.NurseInpatientVitalSigns_recyclerView)
        adapter = NurseVitalSignsAdapter(vitalSigns)
        recyclerView.adapter = adapter

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showDateDialog(date_in: EditText) {
        val calendar = Calendar.getInstance()
        val dateSetListener =
            OnDateSetListener { view, year, month, dayOfMonth ->
                calendar[Calendar.YEAR] = year
                calendar[Calendar.MONTH] = month
                calendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
                date_in.setText(simpleDateFormat.format(calendar.time))
            }
        DatePickerDialog(
            requireContext(), dateSetListener,
            calendar[Calendar.YEAR], calendar[Calendar.MONTH], calendar[Calendar.DAY_OF_MONTH]
        ).show()
    }

        private fun getVitals(id:Int,startDate:String,endDate:String){

            ApiManager.getApis().getVitals(id,startDate,endDate).enqueue(object : Callback<ArrayList<getVitalsResponseItem>>{
            override fun onFailure(call: Call<ArrayList<getVitalsResponseItem>>, t: Throwable) {
                progressBar.isVisible = false
                Toast.makeText(requireContext(),t?.message, Toast.LENGTH_SHORT).show()
                Log.d("Error:::", t.message.toString())
            }

            override fun onResponse(
                call: Call<ArrayList<getVitalsResponseItem>>,
                response: Response<ArrayList<getVitalsResponseItem>>
            ) {
                progressBar.isVisible=false
                no_items_tv.isVisible=false
                linearLayout.isVisible=true
                recyclerView.isVisible=true

                vitalsList = response.body()!!.toList()
                for (vitals in vitalsList) {
                   var date = vitals.vitalsDate?.substring(0,vitals.vitalsDate.indexOf('T'))
//                    var time = vitals.vitalsDate?.substring(11, endIndex = 18)

                    vitalSigns.add(NurseVitalSignsDetails(date.toString(),
                        vitals.ecg as Int?,vitals.pressure,vitals.pulseRate,
                    vitals.temperature,vitals.respirationRate,vitals.nurseName,vitals.note?.body))
                }

                //Log.e("response",response.body().toString())
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
                    patient = response.body()!!
                    patientName.setText(patient.firstName+" "+patient.lastName)
                }

            })
    }



//    private fun AddingItems() : MutableList<NurseVitalSignsDetails>{
//        val items: MutableList<NurseVitalSignsDetails> = arrayListOf()
//        for (i in 0..9) {
//            items.add(
//                NurseVitalSignsDetails("10/10/2020","10pm",R.drawable.ic_edit,28,30,37,60,"amira","comment"))
//        }
//        return items
//    }

}