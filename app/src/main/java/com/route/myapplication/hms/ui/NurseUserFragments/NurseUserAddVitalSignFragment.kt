package com.route.myapplication.hms.ui.DoctorUserFragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.route.myapplication.hms.R
import com.route.myapplication.hms.ui.NurseUserFragments.NoteDto
import com.route.myapplication.hms.ui.NurseUserFragments.NurseInpatientAdapter
import com.route.myapplication.hms.ui.NurseUserFragments.NurseInpatientFragment
import com.route.myapplication.hms.ui.api.ApiManager
import com.route.myapplication.hms.ui.api.Model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class NurseUserAddVitalSignFragment : Fragment() {

    var patient : GetPatientByIdResponse = GetPatientByIdResponse()
    var nurse : GetNurseByIdResponse = GetNurseByIdResponse()


    lateinit var date :TextView
   lateinit var patientName : EditText
   lateinit var nurseName : EditText
    lateinit var pressure : EditText
    lateinit var temperature : EditText
    lateinit var pulseRate : EditText
    lateinit var respirationRate : EditText
    lateinit var ecg : EditText

    lateinit var noteTitle : EditText
    lateinit var note : EditText
    lateinit var submitbtn : Button
    lateinit var cancelbtn : Button

    @RequiresApi(Build.VERSION_CODES.O)
    var vitalsDate = LocalDateTime.now().toLocalDate().toString()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_vital_sign, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()

        val bundle = requireArguments()
        val Indoorpatient: GetIndoorPatientsResponseItem = bundle.getSerializable("Indoorpatient") as GetIndoorPatientsResponseItem

        val indoorRecordId = Indoorpatient.indoorPatientId
        patientName.setText(Indoorpatient.firstName+" "+Indoorpatient.lastName)

        val patientId = Indoorpatient.id
        //getpatientById(patientId)
        getnurseById(10)
        //Log.e("Vdate",vitalsDate)

        submitbtn.setOnClickListener {
//            var pulse_rate: Int? =null
//            var temp: Int? =null
//            var respiration_rate: Int? =null
//            var Pressure:String?=null
            var note_title:String?=null
            var Note :String?=null

            if (pulseRate.text.toString().isNotEmpty() && temperature.text.toString().isNotEmpty()
                && respirationRate.text.toString().isNotEmpty() && pressure.text.toString().isNotEmpty()) {
//                    pulse_rate=Integer.parseInt(pulseRate.text.toString())
//
//            if (temperature.text.toString().isNotEmpty())
//                temp=Integer.parseInt(temperature.text.toString())
//            if (respirationRate.text.toString().isNotEmpty())
//                respiration_rate=Integer.parseInt(respirationRate.text.toString())
//
//            if (pressure.text.toString().isNotEmpty())
//                Pressure=pressure.text.toString()


          //  var vitalsDate = LocalDateTime.now().toString()
            var nurse_id=10
           // var indoor_patient_id=2
            //var patient_id=3
            var CreatedDate=LocalDateTime.now().toLocalDate().toString()
            val local_date = LocalDate.parse(CreatedDate, DateTimeFormatter.ISO_LOCAL_DATE)


                if (noteTitle.text.toString().isNotEmpty())
                note_title=noteTitle.text.toString()

            if (note.text.toString().isNotEmpty())
                Note=noteTitle.text.toString()

            var note_dto=NoteDto(note_title,local_date.toString(),Note,nurse_id,null,indoorRecordId)

            addVitals(pressure.text.toString(),Integer.parseInt(pulseRate.text.toString()),Integer.parseInt(temperature.text.toString()),
                null,Integer.parseInt(respirationRate.text.toString()),local_date.toString(),nurse_id,note_dto, patientId!!)
            }
        }

        cancelbtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, NurseInpatientFragment())
                .commit()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun initViews(){
        date =requireView().findViewById(R.id.date_input_vital_sign)
        date.setText(LocalDateTime.now().toLocalDate().toString())
        date.isEnabled=false

        patientName = requireView().findViewById(R.id.name_input_vital_sign)
        patientName.isEnabled=false

        nurseName = requireView().findViewById(R.id.Nursename_input_vital_sign)
        nurseName.isEnabled=false

        pressure = requireView().findViewById(R.id.blood_pressure_input_vital_sign)
        temperature = requireView().findViewById(R.id.temperature_input_vital_sign)
        pulseRate = requireView().findViewById(R.id.pulse_rate_input_vital_sign)
        respirationRate = requireView().findViewById(R.id.respiratory_rate_input_vital_sign)
        ecg=requireView().findViewById(R.id.ecg_input_vital_sign)
        noteTitle = requireView().findViewById(R.id.note_title_input)
        note=requireView().findViewById(R.id.note_input)

        submitbtn = requireView().findViewById(R.id.submitbtn_vital_sign)
        cancelbtn=requireView().findViewById(R.id.cancelbtn_vital_sign)
    }

    private fun addVitals(
        pressure: String, pulseRate: Int, temperature: Int, ecg: String?,
        respirationRate:Int, vitalsDate:String, nurseId:Int, noteDto: NoteDto?, patientId:Int){
        val vitalsRequest = VitalsRequest(pressure,pulseRate,temperature,ecg,respirationRate,
            vitalsDate,nurseId,noteDto,patientId)

        ApiManager.getApis().addVitalsRequest(vitalsRequest).enqueue(object :
            Callback<AddVitalsResponse> {
            override fun onFailure(call: Call<AddVitalsResponse>, t: Throwable) {
                Toast.makeText(requireContext(),t?.message, Toast.LENGTH_SHORT).show()
                Log.d("Error:::", t.message.toString())
            }

            override fun onResponse(
                call: Call<AddVitalsResponse>,
                response: Response<AddVitalsResponse>
            ) {
                Toast.makeText(requireContext(),"Vitals have been added successfully", Toast.LENGTH_LONG).show()
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, NurseInpatientFragment())
                    .commit()
                Log.e("response",response.body().toString())

            }
        })


    }

//    private fun getpatientById(patientId: Int) {
//
//        ApiManager.getApis().getPatientByIdResponse(patientId)
//            .enqueue(object : Callback<GetPatientByIdResponse> {
//                override fun onFailure(
//                    call: Call<GetPatientByIdResponse>,
//                    t: Throwable
//                ) {
//                    Toast.makeText(
//                        requireContext(),
//                        "Throwable" + t.localizedMessage,
//                        Toast.LENGTH_LONG
//                    ).show()
//
//                }
//
//                override fun onResponse(
//                    call: Call<GetPatientByIdResponse>,
//                    response: Response<GetPatientByIdResponse>
//                ) {
//                    patient = response.body()!!
//                    patientName.setText(patient.firstName+" "+patient.lastName)
//                }
//
//            })
//    }
    private fun getnurseById(nurseId: Int) {

        ApiManager.getApis().getNurseByIdResponse(nurseId)
            .enqueue(object : Callback<GetNurseByIdResponse> {
                override fun onFailure(
                    call: Call<GetNurseByIdResponse>,
                    t: Throwable
                ) {
                    Toast.makeText(
                        requireContext(),
                        "Throwable" + t.localizedMessage,
                        Toast.LENGTH_LONG
                    ).show()

                }

                override fun onResponse(
                    call: Call<GetNurseByIdResponse>,
                    response: Response<GetNurseByIdResponse>
                ) {
                    nurse = response.body()!!
                    nurseName.setText(nurse.firstName+" "+nurse.lastName)

                }

            })
    }

}