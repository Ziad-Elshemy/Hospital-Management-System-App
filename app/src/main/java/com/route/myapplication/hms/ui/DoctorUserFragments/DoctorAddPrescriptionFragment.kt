package com.route.myapplication.hms.ui.DoctorUserFragments

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
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationBarView
import com.route.myapplication.hms.R
import com.route.myapplication.hms.ui.api.ApiManager
import com.route.myapplication.hms.ui.api.Model.*
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*


class DoctorAddPrescriptionFragment : Fragment() {

    var patient : GetPatientByIdResponse = GetPatientByIdResponse()
    var indoorPatientRecordId :Int = 0
    var IndoorPatientsByDeptIdList: List<GetIndoorPatientsByDepartmentIdResponseItem> = arrayListOf()



    lateinit var AddPrescriptionBtn: FloatingActionButton
    lateinit var LayoutList: LinearLayout

    lateinit var medicineName: EditText
    lateinit var medicineSterngth: EditText
    lateinit var instructions: EditText

    lateinit var p_name: TextView
    lateinit var p_gender: TextView
    lateinit var p_type: TextView

    lateinit var medicinetype: Spinner
    lateinit var doseSpinner: Spinner
    lateinit var durationSpinner: Spinner

    //    lateinit var EditPrescriptionBtn: Button
    lateinit var SavePrescriptionBtn: Button
    lateinit var RemovePrescriptionBtn: Button

    var counter = 1

    var prescriptionItems: MutableList<prescriptionItemDetails> = arrayListOf()
    lateinit var bottomNavigation: BottomNavigationView

    lateinit var Date_input: EditText
    lateinit var Time_input: EditText
    lateinit var Diagnose : EditText



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_addprescription, container, false)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AddPrescriptionBtn = requireView().findViewById(R.id.AddPrescriptionBtn)
        LayoutList = requireView().findViewById(R.id.layoutList)

        medicineName = requireView().findViewById(R.id.patient_medicineName)
        medicineSterngth = requireView().findViewById(R.id.patient_medicineStrength)
        instructions = requireView().findViewById(R.id.patient_medicineInstructions)

        p_name = requireView().findViewById(R.id.p_fname)

        p_gender = requireView().findViewById(R.id.p_gender)
        p_type = requireView().findViewById(R.id.p_type)

        durationSpinner = requireView().findViewById(R.id.durationSpinner)
        doseSpinner = requireView().findViewById(R.id.doseSpinner)
        medicinetype = requireView().findViewById(R.id.medicinetypeSpinner)

        Date_input = requireView().findViewById(R.id.date_input)
        Time_input = requireView().findViewById(R.id.time_input)
        Diagnose = requireView().findViewById(R.id.diagnoseInput)

        Date_input.inputType = InputType.TYPE_NULL;
        Time_input.inputType = InputType.TYPE_NULL;

        val bundle = requireArguments()
        val patientId: Int = bundle.getInt("patientId")


        Log.e("patientID",patientId.toString())
        getpatientById(patientId)




//        EditPrescriptionBtn = requireView().findViewById(R.id.prescription_editbtn )
        SavePrescriptionBtn = requireView().findViewById(R.id.prescription_submitbtn)
        RemovePrescriptionBtn = requireView().findViewById(R.id.prescription_cancelbtn)

        bottomNavigation = requireView().findViewById(R.id.bottomNavigationView)

        bottomNavigation.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener {
            if (it.itemId == R.id.submit_prescribtion_btn) {
               var editText_is_empty = isEmpty(prescriptionItems)
                if (editText_is_empty)
                {
                    Toast.makeText(requireContext(),"there is an empty input field", Toast.LENGTH_SHORT).show()

                }
//                if (checkSaveButtons(LayoutList) == false)
//                {
//                    Toast.makeText(requireContext(),"please make sure you have clicked on save button of each medicine", Toast.LENGTH_SHORT).show()
//
//                }
                else {
                    val prescription_date = LocalDateTime.now().toString()
                    val d_id = 2
                    val reappointmentDate = Date_input.text.toString()
                    // reappointmentDate.toLocalDate()
                    val local_date =
                        LocalDate.parse(reappointmentDate, DateTimeFormatter.ISO_LOCAL_DATE)
                    val reappointmentTime = Time_input.text
                    val local_time =
                        LocalTime.parse(reappointmentTime, DateTimeFormatter.ISO_LOCAL_TIME)

                    val local_date_time = LocalDateTime.of(local_date, local_time)

                    addPrescription(
                        prescriptionItems,
                        prescription_date,
                        local_date_time.toString(),
                        patientId,
                        d_id,
                        Diagnose.text.toString(),
                        indoorPatientRecordId
                    )
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, DoctorUserInpatientFragment())
                        .commit()
                }

            } else if (it.itemId == R.id.cancel_prescribtion_btn) {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, DoctorUserInpatientFragment())
                    .commit()

            }
            return@OnItemSelectedListener true;
        })
        AddPrescriptionBtn.setOnClickListener {
            counter++
            addView("Medicine" + counter)


        }

//        EditPrescriptionBtn.setOnClickListener {
//            EditView(requireView().findViewById(R.id.linear_medicine_card))
//
//        }

        SavePrescriptionBtn.setOnClickListener {
            saveView(requireView().findViewById(R.id.linear_medicine_card))

        }

        RemovePrescriptionBtn.setOnClickListener {
            removeView(requireView().findViewById(R.id.linear_medicine_card))
//            Log.e("removed list",prescriptionItems.toString())


        }


        Date_input.setOnClickListener { showDateDialog(Date_input) }

        Time_input.setOnClickListener { showTimeDialog(Time_input) }




    }

    private fun addView(medicineTitle: String) {

        val AddPrescriptionView: View =
            layoutInflater.inflate(R.layout.doctor_add_new_prescription, null, false)
        val medicine_title_layout: LinearLayout =
            AddPrescriptionView.findViewById(R.id.medicine_card_title)
        medicine_title_layout.forEach {
            if (it is TextView)
                it.setText(medicineTitle)
        }


        val new_medicineName: EditText = AddPrescriptionView.findViewById(R.id.patient_medicineName)
        val new_medicineSterngth: EditText =
            AddPrescriptionView.findViewById(R.id.patient_medicineStrength)
        val new_instructions: EditText =
            AddPrescriptionView.findViewById(R.id.patient_medicineInstructions)
        val new_medicinetype: Spinner = AddPrescriptionView.findViewById(R.id.medicinetypeSpinner)
        val new_doseSpinner: Spinner = AddPrescriptionView.findViewById(R.id.doseSpinner)
        val new_durationSpinner: Spinner = AddPrescriptionView.findViewById(R.id.durationSpinner)

//                val new_EditPrescriptionBtn: Button =
//                    AddPrescriptionView.findViewById(R.id.prescription_editbtn )
//                new_EditPrescriptionBtn.setOnClickListener {
//                        EditView(AddPrescriptionView)
//                }

        val new_SavePrescriptionBtn: Button =
            AddPrescriptionView.findViewById(R.id.prescription_submitbtn)
        new_SavePrescriptionBtn.setOnClickListener {
            saveView(AddPrescriptionView)
        }

        val new_RemovePrescriptionBtn: Button =
            AddPrescriptionView.findViewById(R.id.prescription_cancelbtn)
        new_RemovePrescriptionBtn.setOnClickListener {
            removeView(AddPrescriptionView)
        }


        LayoutList.addView(AddPrescriptionView)
    }


    private fun removeView(view: View) {
        var m_name = view.findViewById<TextView>(R.id.patient_medicineName)
        var m_duration = view.findViewById<Spinner>(R.id.durationSpinner)
        var selected_duration = m_duration.selectedItem
        var m_dose = view.findViewById<Spinner>(R.id.doseSpinner)
        var selected_dose = m_dose.selectedItem

        var m_type = view.findViewById<Spinner>(R.id.medicinetypeSpinner)
        var selected_type = m_type.selectedItem

        var m_concentration = view.findViewById<TextView>(R.id.patient_medicineStrength)
        var m_instructions = view.findViewById<TextView>(R.id.patient_medicineInstructions)

        prescriptionItems.remove(
            prescriptionItemDetails(
                m_name.toString(),
                m_concentration.toString(),
                m_instructions.toString(),
                selected_type.toString(),
                selected_dose.toString(),
                selected_duration.toString()
            )
        )

        LayoutList.removeView(view)



    }

//    private fun EditView(view: View) {
//        view.findViewById<EditText>(R.id.patient_medicineName).isEnabled = true
//        view.findViewById<EditText>(R.id.patient_medicineStrength).isEnabled = true
//        view.findViewById<EditText>(R.id.patient_medicineInstructions).isEnabled = true
//
//        view.findViewById<Spinner>(R.id.medicinetypeSpinner).isEnabled = true
//        view.findViewById<Spinner>(R.id.durationSpinner).isEnabled = true
//        view.findViewById<Spinner>(R.id.doseSpinner).isEnabled = true
//
//
//    }

    private fun saveView(view: View) {

        var m_name = view.findViewById<TextView>(R.id.patient_medicineName)
        var m_duration = view.findViewById<Spinner>(R.id.durationSpinner)
        var selected_duration = m_duration.selectedItem
        var m_dose = view.findViewById<Spinner>(R.id.doseSpinner)
        var selected_dose = m_dose.selectedItem

        var m_type = view.findViewById<Spinner>(R.id.medicinetypeSpinner)
        var selected_type = m_type.selectedItem

        var m_concentration = view.findViewById<TextView>(R.id.patient_medicineStrength)
        var m_instructions = view.findViewById<TextView>(R.id.patient_medicineInstructions)

        var savebtn = view.findViewById<Button>(R.id.prescription_submitbtn)
        savebtn.isActivated = false

        m_name.isEnabled = false
        m_duration.isEnabled = false
        m_dose.isEnabled = false

        m_type.isEnabled = false
        m_concentration.isEnabled = false
        m_instructions.isEnabled = false


        prescriptionItems.add(
            prescriptionItemDetails(
                m_name.text.toString(),
                m_concentration.text.toString(),
                m_instructions.text.toString(),
                selected_type.toString(),
                selected_dose.toString(),
                selected_duration.toString()
            )
        )


    }

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


    private fun showTimeDialog(time_in: EditText) {
        val calendar = Calendar.getInstance()
        val timeSetListener =
            OnTimeSetListener { view, hourOfDay, minute ->
                calendar[Calendar.HOUR_OF_DAY] = hourOfDay
                calendar[Calendar.MINUTE] = minute
                val simpleDateFormat = SimpleDateFormat("HH:mm:00")
                time_in.setText(simpleDateFormat.format(calendar.time))
            }
        TimePickerDialog(
            requireContext(), timeSetListener,
            calendar[Calendar.HOUR_OF_DAY], calendar[Calendar.MINUTE], false
        ).show()
    }



    private fun addPrescription(
        prescriptionItems: MutableList<prescriptionItemDetails>,
        prescription_Date: String,
        re_appointement_date: String,
        patientId: Int,
        doctorId: Int,
        diagnos: String,
        indoorPatientRecordId: Int?
        ){

        val prescriptionRequest = PrescriptionRequest(prescriptionItems,prescription_Date,re_appointement_date,
            patientId,doctorId,diagnos,indoorPatientRecordId)
        ApiManager.getApis().addPrescriptionRequest(prescriptionRequest).enqueue(object : Callback<AddPrescriptionResponse>{
            override fun onFailure(call: Call<AddPrescriptionResponse>, t: Throwable) {
                Toast.makeText(requireContext(),t?.message, Toast.LENGTH_SHORT).show()
                Log.d("Error:::", t.message.toString())
            }

            override fun onResponse(
                call: Call<AddPrescriptionResponse>,
                response: Response<AddPrescriptionResponse>
            ) {
                Toast.makeText(requireContext(),"Prescription has been added successfully", Toast.LENGTH_LONG).show()
                Log.e("response",response.body().toString())

            }
        })



    }


    private fun isEmpty(prescriptionList:MutableList<prescriptionItemDetails>): Boolean {
        var input_feild_is_empty_flag = true
        prescriptionItems.forEach {
            if (it.medicine_Name.isNotEmpty() && it.medicine_concentration.isNotEmpty() && it.instructions.isNotEmpty() && !Diagnose.text.isNullOrBlank())
                input_feild_is_empty_flag = false
            else
                input_feild_is_empty_flag = true
        }
        return input_feild_is_empty_flag

    }

    private fun getIdoorPatientsByDeptId(deptId: Int) {

        ApiManager.getApis().getIndoorPatientsByDeptId(deptId)
            .enqueue(object : Callback<ArrayList<GetIndoorPatientsByDepartmentIdResponseItem>> {
                override fun onFailure(
                    call: Call<ArrayList<GetIndoorPatientsByDepartmentIdResponseItem>>,
                    t: Throwable
                ) {
                    Toast.makeText(
                        requireContext(),
                        "Throwable" + t.localizedMessage,
                        Toast.LENGTH_LONG
                    ).show()

                }

                override fun onResponse(
                    call: Call<ArrayList<GetIndoorPatientsByDepartmentIdResponseItem>>,
                    response: Response<ArrayList<GetIndoorPatientsByDepartmentIdResponseItem>>
                ) {
                    IndoorPatientsByDeptIdList = response.body()!!.toList()

                    IndoorPatientsByDeptIdList.forEach {
                        if (it.id == patient.id)
                            indoorPatientRecordId = it.indoorPatientId!!
                    }
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
                    p_name.text = patient.firstName+" "+patient.lastName
                    p_gender.text = patient.gender
                    getIdoorPatientsByDeptId(patient.departmentId!!)

                }

            })
    }

//    private fun checkSaveButtons(linearLayoutList : LinearLayout): Boolean {
//        var saved = false
//
//        linearLayoutList.forEach {
//            if (it is Button) {
//                    if (it.isActivated) {
//                        saved = false
//                    } else
//                        saved = true
//                }
//        }
//        return saved
//        }
}
