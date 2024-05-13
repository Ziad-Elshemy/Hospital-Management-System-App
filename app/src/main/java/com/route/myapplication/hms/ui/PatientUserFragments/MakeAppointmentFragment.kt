package com.route.myapplication.hms.ui.PatientUserFragments

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.route.myapplication.hms.Constants
import com.route.myapplication.hms.R
import com.route.myapplication.hms.ui.api.ApiManager
import com.route.myapplication.hms.ui.api.Model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class MakeAppointmentFragment : Fragment() {

    lateinit var date : EditText
    lateinit var nameInput : EditText
    lateinit var dept_Spinner : Spinner
    lateinit var doctor_spinner :Spinner
    lateinit var slots_spinner :Spinner
    lateinit var submitbtn : Button
    lateinit var cancelbtn : Button
    lateinit var complain_input : EditText
     var  dayOfWeek :Int?=null
    var timeSlotDto = TimeSlotDto(null, null, null, null, false, null)

    var slotsList: List<WorkSchedulesItem?>? = ArrayList()
    var timeSlotsList: MutableList<String> = ArrayList()
    var editedTimeSlotsList : MutableList<String> = ArrayList()



    var deptsList: List<GetAllDeptsResponseItem> = ArrayList()
    var deptsNameList: MutableList<String> = ArrayList()

    var empsList: List<GetAllEmpsResponseItem> = ArrayList()
    var doctorsList: List<DoctorDtosItem?>? = ArrayList()
    var doctorsNameList: MutableList<String> = ArrayList()


    var patient : GetPatientByIdResponse = GetPatientByIdResponse()

    val patientId = Constants.userID.toInt()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_make_appointment2, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        complain_input = requireView().findViewById(R.id.complain_input)
        submitbtn = requireView().findViewById(R.id.submitbtn)
        cancelbtn = requireView().findViewById(R.id.cancelbtn)
        date = requireView().findViewById(R.id.date_input)
        date.inputType = InputType.TYPE_NULL;
        dept_Spinner = requireView().findViewById(R.id.dept_Spinner)
        doctor_spinner = requireView().findViewById(R.id.doctor_spinner)
        slots_spinner = requireView().findViewById(R.id.slots_spinner)

        var current = LocalDateTime.now().toLocalDate()
        date.setText(current.toString())
        var dayInString = current.dayOfWeek.toString()
        if (dayInString == "SUNDAY")
            dayOfWeek = 0
        else if (dayInString == "Monday")
            dayOfWeek = 1
        else if (dayInString == "TUESDAY")
            dayOfWeek = 2
        else if (dayInString == "WEDNESDAY")
            dayOfWeek = 3
        else if (dayInString == "THURSDAY")
            dayOfWeek = 4
        else if (dayInString == "FRIDAY")
            dayOfWeek = 5
        else if (dayInString == "SATURDAY")
            dayOfWeek = 6

        nameInput = requireView().findViewById(R.id.name_input)
        nameInput.isEnabled = false


        getpatientById(patientId)
        getAllDepts()

        var selectedDeptName: String

        dept_Spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedDeptName = deptsNameList[p2]
                getAllDoctors(selectedDeptName)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        date.setOnClickListener {
            showDateDialog(date)
        }


        doctor_spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val doctorId = getDoctorID()
                getFreeSlots(doctorId, dayOfWeek!!)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        submitbtn.setOnClickListener {
            if (complain_input.text.isNotEmpty()) {
                var selectedTime = timeSlotsList[slots_spinner.selectedItemPosition]
                for (slot in slotsList!!) {
                    if (slot?.dayOfWork == dayOfWeek) {
                        slot?.slots?.forEach {
                            if (it?.slotTime == selectedTime)
                                timeSlotDto = TimeSlotDto(
                                    it?.slotId!!,
                                    it.slotNumber!!,
                                    dayOfWeek!!,
                                    it.slotTime!!,
                                    false,
                                    getDoctorID()
                                )
                        }
                    }
                }

                var appointmentDto = AppointmentDto(date.text.toString(), "consultaion",complain_input.text.toString(),patientId,getDoctorID())

                var appointmentRequest = AddAppointmentRequest(timeSlotDto,appointmentDto)
                makeAppointment(appointmentRequest)

            }
            else
                Toast.makeText(requireContext(),"please enter your complain ", Toast.LENGTH_LONG).show()


        }

        cancelbtn.setOnClickListener {
            pushFragment(PatientUserDashboardFragment())

        }
    }

    private fun showDateDialog(date_in: EditText) {
        val calendar = Calendar.getInstance()
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                calendar[Calendar.YEAR] = year
                calendar[Calendar.MONTH] = month
                calendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                 dayOfWeek = calendar.time.day
                Log.e("day",(dayOfWeek!! -1).toString())
                    //.toString())
                val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
                date_in.setText(simpleDateFormat.format(calendar.time))
                getFreeSlots(getDoctorID(), dayOfWeek!!)
            }
        DatePickerDialog(
            requireContext(),dateSetListener,
            calendar[Calendar.YEAR], calendar[Calendar.MONTH], calendar[Calendar.DAY_OF_MONTH]
        ).show()
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
                    nameInput.setText(patient.firstName+" "+patient.lastName)
                }

            })
    }


    private fun getAllDepts(){

        ApiManager.getApis().getAllDepts().enqueue(object :Callback<ArrayList<GetAllDeptsResponseItem>>{
            override fun onFailure(call: Call<ArrayList<GetAllDeptsResponseItem>>, t: Throwable) {
                Toast.makeText(requireContext(),t?.message, Toast.LENGTH_SHORT).show()
                Log.d("Error:::", t.message.toString())
            }

            override fun onResponse(
                call: Call<ArrayList<GetAllDeptsResponseItem>>,
                response: Response<ArrayList<GetAllDeptsResponseItem>>
            ) {
                deptsList = response.body()!!.toList()
                for (dept in deptsList) {
                    deptsNameList.add(dept.departmentName.toString())
                }
                val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                    requireContext(), android.R.layout.simple_spinner_item, deptsNameList)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                dept_Spinner.adapter = adapter
                Log.e("response",deptsNameList.toString())
            }
        })
    }

    private fun getAllDoctors(deptName:String){

        ApiManager.getApis().getAllEmps().enqueue(object :Callback<ArrayList<GetAllEmpsResponseItem>>{
            override fun onFailure(call: Call<ArrayList<GetAllEmpsResponseItem>>, t: Throwable) {
                Toast.makeText(requireContext(),t?.message, Toast.LENGTH_SHORT).show()
                Log.d("Error:::", t.message.toString())
            }

            override fun onResponse(
                call: Call<ArrayList<GetAllEmpsResponseItem>>,
                response: Response<ArrayList<GetAllEmpsResponseItem>>
            ) {
                doctorsNameList.clear()
                empsList = response.body()!!.toList()
                for (emp in empsList) {
                    if (emp.departmentName == deptName)
                    {
                        doctorsList = emp.doctorDtos!!.toList()
                        for (doctor in doctorsList!!){
                            doctorsNameList.add(doctor?.firstName!! + " " + doctor?.lastName!!)

                        }
                    }

                }
                val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                    requireContext(), android.R.layout.simple_spinner_item, doctorsNameList)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                doctor_spinner.adapter = adapter
                Log.e("response",doctorsNameList.toString())

            }
        })
    }


    private fun getFreeSlots(doctorId:Int,day:Int){

        ApiManager.getApis().getFreeSlotsByDocId(doctorId).enqueue(object :Callback<GetFreeTimeSlotsByDocIdResponse>{
            override fun onFailure(call: Call<GetFreeTimeSlotsByDocIdResponse>, t: Throwable) {
                Toast.makeText(requireContext(),t?.message, Toast.LENGTH_SHORT).show()
                Log.d("Error:::", t.message.toString())
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(
                call: Call<GetFreeTimeSlotsByDocIdResponse>,
                response: Response<GetFreeTimeSlotsByDocIdResponse>
            ) {
                val getFreeSlotsResponse : GetFreeTimeSlotsByDocIdResponse = response.body()!!
                slotsList = getFreeSlotsResponse.workSchedules
                editedTimeSlotsList.clear()
                for(slot in slotsList!!) {
                    if (slot?.dayOfWork == day)
                    {
                        slot.slots?.forEach {
                            val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
                            var dateTime = LocalTime.parse(it?.slotTime, formatter)
                            if (dateTime.hour > 12)
                            {
                                dateTime=dateTime.minusHours(12)
                                editedTimeSlotsList.add(dateTime.toString()+" "+"PM")

                            }
                            else if (dateTime.hour == 12)
                            {
                                editedTimeSlotsList.add(dateTime.toString()+" "+"PM")

                            }
                            else
                                editedTimeSlotsList.add(dateTime.toString()+" "+"AM")
                            timeSlotsList.add(it?.slotTime.toString())
                        }
                    }
                }
                val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                    requireContext(), android.R.layout.simple_spinner_item, editedTimeSlotsList)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                slots_spinner.adapter = adapter
                Log.e("response",editedTimeSlotsList.toString())
            }
        })
    }

    private fun getDoctorID(): Int {
        val itemSelectedIndex = doctor_spinner.selectedItemPosition
        val doctorId = doctorsList!![itemSelectedIndex]?.id
        return doctorId!!

    }


    private fun makeAppointment(addAppointmentRequest: AddAppointmentRequest){

        ApiManager.getApis().addAppointment(addAppointmentRequest).enqueue(object :Callback<AddAppointmentResponse>{
            override fun onFailure(call: Call<AddAppointmentResponse>, t: Throwable) {
                Toast.makeText(requireContext(),t?.message, Toast.LENGTH_SHORT).show()
                Log.d("Error:::", t.message.toString())
            }

            override fun onResponse(
                call: Call<AddAppointmentResponse>,
                response: Response<AddAppointmentResponse>
            ) {
                Toast.makeText(requireContext(),"you have booked appointment successfully", Toast.LENGTH_LONG).show()
                pushFragment(PatientUserDashboardFragment())

            }
        })
    }

    private fun pushFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }


}