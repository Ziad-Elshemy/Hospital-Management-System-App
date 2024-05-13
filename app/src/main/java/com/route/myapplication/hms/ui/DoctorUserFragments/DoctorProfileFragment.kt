package com.route.myapplication.hms.ui.DoctorUserFragments

import android.os.Build
import android.os.Bundle
import android.text.InputFilter
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.route.myapplication.hms.Constants
import com.route.myapplication.hms.R
import com.route.myapplication.hms.ui.PatientUserFragments.InputFilterMinMax
import com.route.myapplication.hms.ui.PatientUserFragments.PatientUserDashboardFragment
import com.route.myapplication.hms.ui.api.ApiManager
import com.route.myapplication.hms.ui.api.Model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime

class DoctorProfileFragment : Fragment() {

    var doctor : GetDoctorByIdResponse = GetDoctorByIdResponse()


    lateinit var firstName : EditText
    lateinit var lastName : EditText
    lateinit var userName : EditText
    lateinit var nationalId : EditText
    lateinit var ageInput : EditText
    lateinit var phoneNumber : EditText
    lateinit var email : EditText
    lateinit var bloodType: EditText
    lateinit var address : EditText
    lateinit var gender : Spinner

    lateinit var degree : EditText
    lateinit var specialization : EditText
    lateinit var doctorClinic : Spinner


    lateinit var updateBtn : Button
    lateinit var cancelBtn : Button


    val doctorId = Constants.userID.toInt()

    var DeptId : Int? = null
    lateinit var DeptName :String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctor_profile, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firstName = requireView().findViewById(R.id.fname_input)
        ageInput = requireView().findViewById(R.id.age_input)
        ageInput.filters = arrayOf<InputFilter>(InputFilterMinMax("1", "99"))
        lastName =requireView().findViewById(R.id.lname_input)
        userName = requireView().findViewById(R.id.userName_input)
        nationalId = requireView().findViewById(R.id.nationalId_input)
        phoneNumber = requireView().findViewById(R.id.phoneNumber_input)
        email = requireView().findViewById(R.id.email_input)
        bloodType = requireView().findViewById(R.id.bloodType_input)
        address = requireView().findViewById(R.id.address_input)
        gender = requireView().findViewById(R.id.gender_input)

        degree = requireView().findViewById(R.id.Degree_input)
        specialization = requireView().findViewById(R.id.specialization_input)
        doctorClinic = requireView().findViewById(R.id.doctorClinic_input)

        updateBtn =requireView().findViewById(R.id.submitbtn_edit)
        cancelBtn =requireView().findViewById(R.id.cancelbtn_edit)


        getDoctorById(doctorId)

        updateBtn.setOnClickListener {
            val todayDate = LocalDateTime.now().toLocalDate().toString()
            var doctorClinicBoolean = true
            if(doctorClinic.selectedItem == "Yes")
                doctorClinicBoolean = true
            else if (doctorClinic.selectedItem == "No")
                doctorClinicBoolean = false
            val valid = isEmailValid(email.text.toString())
            if (isEmailValid(email.text.toString())) {
                updateDoctor(
                    doctorId,
                    firstName.text.toString(),
                    lastName.text.toString(),
                    todayDate,
                    ageInput.text.toString().toInt(),
                    nationalId.toString(),
                    null,
                    null,
                    bloodType.text.toString(),
                    phoneNumber.text.toString(),
                    address.text.toString(),
                    gender.selectedItem.toString(),
                    userName.text.toString(),
                    email.text.toString(),
                    null,
                    "doctor",
                    DeptId,
                    DeptName,
                    true,
                    degree.text.toString(),
                    specialization.text.toString(),
                    doctorClinicBoolean
                )
            } else {
                Toast.makeText(requireContext(), "Invalid Email Address", Toast.LENGTH_LONG).show()

            }

        }

        cancelBtn.setOnClickListener {
            pushFragment(PatientUserDashboardFragment())
        }

    }

    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun getDoctorById(doctorID: Int) {

        ApiManager.getApis().getDoctorById(doctorID).enqueue(object :
            Callback<GetDoctorByIdResponse> {
            override fun onFailure(
                call: Call<GetDoctorByIdResponse>,
                t: Throwable
            ) {
                Toast.makeText(
                    requireContext(),
                    "Throwable" + t.localizedMessage,
                    Toast.LENGTH_LONG
                ).show()

            }

            override fun onResponse(
                call: Call<GetDoctorByIdResponse>,
                response: Response<GetDoctorByIdResponse>
            ) {
                doctor = response.body()!!
                firstName.setText(doctor.firstName)
                lastName.setText(doctor.lastName)
                specialization.setText(doctor.docSpecialization)


                userName.setText(doctor.userName)
                ageInput.setText(doctor.age.toString())
                nationalId.setText(doctor.nationalId)
                phoneNumber.setText(doctor.phoneNumber)
                email.setText(doctor.mail)
                bloodType.setText(doctor.bloodType)
                address.setText(doctor.address)
                degree.setText(doctor.docDegree)
                specialization.setText(doctor.docSpecialization)
                DeptId = doctor.departmentId
                DeptName = doctor.departmentName.toString()
                if(doctor.gender == "male" || doctor.gender == "MALE" || doctor.gender == "Male") {
                    gender.setSelection(0)
                } else if(doctor.gender == "female" || doctor.gender == "FEMALE" || doctor.gender == "Female"){
                    gender.setSelection(1)
                }

                if(doctor.clinicalDoctor == true) {
                    doctorClinic.setSelection(0)
                } else if(doctor.clinicalDoctor == false){
                    doctorClinic.setSelection(1)
                }
            }

        })
    }

    private fun pushFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack("back")
            .commit()
    }

    private fun updateDoctor(id:Int,fName:String,lName:String,date:String,age:Int,nationalId:String,image:String?,imageName:String?,
                              bloodType:String,phoneNumber:String,address:String,gender:String,userName:String,mail:String,password:String?,role:String,deptId:Int?,
                              deptName:String?,isActive:Boolean,Degree:String,Specialization:String,doctorClinic:Boolean) {

        val updateRequest = UpdateDoctorRequest(id,fName,lName,date,age,nationalId,image,imageName,bloodType,phoneNumber,address,gender,userName,mail
            ,password,role,deptId,deptName,isActive,Degree,Specialization,doctorClinic)

        ApiManager.getApis().updateDoctorRequest(updateRequest)
            .enqueue(object : Callback<UpdateDoctorResponse> {
                override fun onFailure(
                    call: Call<UpdateDoctorResponse>,
                    t: Throwable
                ) {
                    Toast.makeText(
                        requireContext(),
                        "Throwable" + t.localizedMessage,
                        Toast.LENGTH_LONG
                    ).show()

                }

                override fun onResponse(
                    call: Call<UpdateDoctorResponse>,
                    response: Response<UpdateDoctorResponse>
                ) {

                    Toast.makeText(requireContext(), "Information has been updated successfully", Toast.LENGTH_LONG).show()
                    pushFragment(DoctorUserDashboardFragment())
                }

            })
    }




}