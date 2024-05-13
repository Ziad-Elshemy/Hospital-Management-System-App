package com.route.myapplication.hms.ui.NurseUserFragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.route.myapplication.hms.R
import com.route.myapplication.hms.ui.api.ApiManager
import com.route.myapplication.hms.ui.api.Model.*
import com.route.myapplication.hms.ui.ui.NurseVitalSignsDetails
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime


class NurseUserReservePatientFragment : Fragment() {

    lateinit var patientsSpinner: Spinner
    lateinit var deptsSpinner: Spinner
    lateinit var doctorsSpinner : Spinner
    lateinit var roomsSpinner: Spinner
    lateinit var bedsSpinner : Spinner
    lateinit var causeOfAdmission : EditText
    lateinit var medicalHistory : EditText

    lateinit var submitBtn : Button
    lateinit var cancelBtn : Button

    var patientsList: List<GetAllPatiensResponseItem> = ArrayList()
    var patientsNameList: MutableList<String> = ArrayList()

    var deptsList: List<GetAllDeptsResponseItem> = ArrayList()
    var deptsNameList: MutableList<String> = ArrayList()

    var empsList: List<GetAllEmpsResponseItem> = ArrayList()
    var doctorsList: List<DoctorDtosItem?>? = ArrayList()
    var doctorsNameList: MutableList<String> = ArrayList()

    var roomsList: List<GetFreeRoomsResponseItem> = ArrayList()
    var roomsNumList: MutableList<Int> = ArrayList()

    var bedsList: List<GetFreeBedsResponseItem> = ArrayList()
    var bedsNumList: MutableList<Int> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(
            R.layout.fragment_nurse_user_reserve_patient,
            container,
            false
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        patientsSpinner = requireView().findViewById(R.id.patientsSpinner)
        deptsSpinner = requireView().findViewById(R.id.DepartmentsSpinner)
        doctorsSpinner = requireView().findViewById(R.id.DoctorsSpinner)
        roomsSpinner = requireView().findViewById(R.id.RoomsSpinner)
        bedsSpinner = requireView().findViewById(R.id.BedsSpinner)

        causeOfAdmission = requireView().findViewById(R.id.cause_of_Addmission_input)
        medicalHistory = requireView().findViewById(R.id.medical_history_input)

        submitBtn = requireView().findViewById(R.id.submitbtn_reservation)
        cancelBtn = requireView().findViewById(R.id.cancelbtn_reservation)

        getAllPatients()
        getAllDepts()

        var selectedDeptName : String
        deptsSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedDeptName = deptsNameList[p2]
                getAllDoctors(selectedDeptName)         }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        //getAllDoctors()
        getAllRooms()
        var selectedRoomId : Int
        // getAllBeds()
        roomsSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedRoomId = roomsList[p2].id!!
                getAllBeds(selectedRoomId)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        submitBtn.setOnClickListener {
            var patient_id = getPatientID()
            var doctor_id = getDoctorID()
            var dept_id = getDeptID()
            var room_id =roomsList[roomsSpinner.selectedItemPosition].id!!
                //roomsIdList[Integer.parseInt(roomsSpinner.selectedItemId.toString())]
                //Integer.parseInt(roomsSpinner.selectedItemId.toString())
            var bed_id = bedsList[bedsSpinner.selectedItemPosition].id!!
                //Integer.parseInt(bedsSpinner.selectedItemId.toString())
            var todaysDate = LocalDateTime.now().toLocalDate().toString()
            reservePatient(causeOfAdmission.text.toString(),
                room_id,bed_id,dept_id,medicalHistory.text.toString(),doctor_id,patient_id,todaysDate)
        }

            cancelBtn.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, NurseInpatientFragment())
                    .commit()
            }
    }

    private fun getPatientID(): Int {
        val itemSelectedIndex = patientsSpinner.selectedItemPosition
        val patientId = patientsList[itemSelectedIndex].id

        return patientId!!
    }

    private fun getDoctorID(): Int {
//        val fullName = doctorsSpinner.selectedItem.toString()
//        val firstName = fullName.substring(0,fullName.indexOf(" "))
//        val lastName = fullName.substring(fullName.indexOf(" ")+1)
//
//        for (doctor in doctorsList!!){
//            if (doctor?.firstName == firstName && doctor.lastName == lastName)
//                return doctor.id!!
//
//        }
//        return -1
        val itemSelectedIndex = doctorsSpinner.selectedItemPosition
        val doctorId = doctorsList!![itemSelectedIndex]?.id

        return doctorId!!

    }

    private fun getDeptID(): Int {
//        val deptName = deptsSpinner.selectedItem.toString()
//        for (dept in deptsList){
//            if (dept.departmentName == deptName)
//                return dept.departmentId!!
//
//        }
//       return -1
        val itemSelectedIndex = deptsSpinner.selectedItemPosition
        val deptId = deptsList[itemSelectedIndex].departmentId

        return deptId!!

    }

    private fun getAllPatients(){

        ApiManager.getApis().getAllPatients().enqueue(object :Callback<ArrayList<GetAllPatiensResponseItem>>{
            override fun onFailure(call: Call<ArrayList<GetAllPatiensResponseItem>>, t: Throwable) {
                Toast.makeText(requireContext(),t?.message, Toast.LENGTH_SHORT).show()
                Log.d("Error:::", t.message.toString())
            }

            override fun onResponse(
                call: Call<ArrayList<GetAllPatiensResponseItem>>,
                response: Response<ArrayList<GetAllPatiensResponseItem>>
            ) {
                patientsList = response.body()!!.toList()
                for (patient in patientsList) {
                    patientsNameList.add(patient.firstName.toString()+" "+patient.lastName.toString())
                }
                val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                    requireContext(), android.R.layout.simple_spinner_item, patientsNameList)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                patientsSpinner.adapter = adapter
                Log.e("response",patientsNameList.toString())

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
                deptsSpinner.adapter = adapter
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
                doctorsSpinner.adapter = adapter
                Log.e("response",doctorsNameList.toString())

            }
        })
    }

    private fun getAllRooms(){

        ApiManager.getApis().getFreeRooms().enqueue(object :Callback<ArrayList<GetFreeRoomsResponseItem>>{
            override fun onFailure(call: Call<ArrayList<GetFreeRoomsResponseItem>>, t: Throwable) {
                Toast.makeText(requireContext(),t?.message, Toast.LENGTH_SHORT).show()
                Log.d("Error:::", t.message.toString())
            }

            override fun onResponse(
                call: Call<ArrayList<GetFreeRoomsResponseItem>>,
                response: Response<ArrayList<GetFreeRoomsResponseItem>>
            ) {
                roomsList = response.body()!!.toList()
                for (room in roomsList) {
                    roomsNumList.add(room.roomNumber!!)
                }
                val adapter: ArrayAdapter<Int> = ArrayAdapter<Int>(
                    requireContext(), android.R.layout.simple_spinner_item, roomsNumList)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                roomsSpinner.adapter = adapter
                Log.e("response",roomsNumList.toString())

            }
        })
    }

    private fun getAllBeds( roomID:Int){
        ApiManager.getApis().getFreeBeds(roomID).enqueue(object :Callback<ArrayList<GetFreeBedsResponseItem>>{
            override fun onFailure(call: Call<ArrayList<GetFreeBedsResponseItem>>, t: Throwable) {
                Toast.makeText(requireContext(),t?.message, Toast.LENGTH_SHORT).show()
                Log.d("Error:::", t.message.toString())
            }

            override fun onResponse(
                call: Call<ArrayList<GetFreeBedsResponseItem>>,
                response: Response<ArrayList<GetFreeBedsResponseItem>>
            ) {
                bedsList = response.body()!!.toList()
                for (bed in bedsList) {
                    bedsNumList.add(bed.number!!)
                }
                val adapter: ArrayAdapter<Int> = ArrayAdapter<Int>(
                    requireContext(), android.R.layout.simple_spinner_item, bedsNumList)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                bedsSpinner.adapter = adapter
                Log.e("response",bedsNumList.toString())

            }
        })
    }

    private fun reservePatient(causeOfAdmission:String,room_id: Int,bed_id: Int,dept_id: Int,medicalHistory:String,doctor_id: Int,patient_id: Int,todaysDate:String) {
        val reserveRequest = ReservePatientRequest(causeOfAdmission,room_id,bed_id,dept_id,medicalHistory,doctor_id,patient_id, todaysDate)

        ApiManager.getApis().reservePatient(reserveRequest).enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(requireContext(),t?.message, Toast.LENGTH_SHORT).show()
                Log.d("Error:::", t.message.toString())
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                Toast.makeText(requireContext(),response.body(), Toast.LENGTH_LONG).show()
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, NurseInpatientFragment())
                    .commit()
                Log.e("response",response.body().toString())

            }
        })

    }


}