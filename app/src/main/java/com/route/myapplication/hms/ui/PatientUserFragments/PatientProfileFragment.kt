package com.route.myapplication.hms.ui.PatientUserFragments

import android.os.Build
import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.route.myapplication.hms.Constants
import com.route.myapplication.hms.R
import com.route.myapplication.hms.ui.api.ApiManager
import com.route.myapplication.hms.ui.api.Model.GetPatientByIdResponse
import com.route.myapplication.hms.ui.api.Model.UpdatePatientRequest
import com.route.myapplication.hms.ui.api.Model.UpdateUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime


class PatientProfileFragment : Fragment() {

    val patientId = Constants.userID.toInt()

    var patient : GetPatientByIdResponse = GetPatientByIdResponse()

    var genderInput = ""

    lateinit var firstName : EditText
    lateinit var lastName : EditText
    lateinit var userName : EditText
    lateinit var nationalId : EditText
    lateinit var ageInput : EditText
    lateinit var phoneNumber : EditText
    lateinit var email : EditText
    lateinit var bloodType: EditText
    lateinit var address :EditText
    lateinit var gender : Spinner

    lateinit var updateBtn :Button
    lateinit var cancelBtn :Button

    var DeptId : Int? = null
    //lateinit var DeptName :String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_patient_profile, container, false)
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


        updateBtn =requireView().findViewById(R.id.submitbtn_edit)
        cancelBtn =requireView().findViewById(R.id.cancelbtn_edit)


        getpatientById(patientId)

        updateBtn.setOnClickListener {
           val todayDate = LocalDateTime.now().toLocalDate().toString()

            val valid = isEmailValid(email.text.toString())
            if(isEmailValid(email.text.toString())){
                updatePatient(patientId,firstName.text.toString(),lastName.text.toString(),todayDate,ageInput.text.toString().toInt()
                    ,nationalId.text.toString(),null,null, bloodType.text.toString(),phoneNumber.text.toString(),address.text.toString()
                    ,gender.selectedItem.toString(),userName.text.toString(),email.text.toString(),null,"patient",DeptId,
                    null,true)
            }
            else{
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
                    userName.setText(patient.userName)
                   // ageInput.setText(patient.age)
                    ageInput.setText("23")
                    nationalId.setText(patient.nationalId)
                    phoneNumber.setText(patient.phoneNumber)
                    email.setText(patient.mail)
                    bloodType.setText(patient.bloodType)
                    address.setText(patient.address)
                     DeptId = patient.departmentId
                    if(patient.gender == "male" || patient.gender == "MALE" || patient.gender == "Male") {
                        gender.setSelection(0)
                    } else if(patient.gender == "female" || patient.gender == "FEMALE" || patient.gender == "Female"){
                        gender.setSelection(1)
                    }

                }

            })
    }


    private fun updatePatient(id:Int,fName:String,lName:String,date:String,age:Int,nationalId:String,image:String?,imageName:String?,
    bloodType:String,phoneNumber:String,address:String,gender:String,userName:String,mail:String,password:String?,role:String,deptId:Int?,
    deptName:String?,isActive:Boolean) {

        val updateRequest = UpdatePatientRequest(id,fName,lName,date,age,nationalId,image,imageName,bloodType,phoneNumber,address,gender,userName,mail
            ,password,role,deptId,deptName,isActive)

        ApiManager.getApis().updatePatientRequest(updateRequest)
            .enqueue(object : Callback<UpdateUserResponse> {
                override fun onFailure(
                    call: Call<UpdateUserResponse>,
                    t: Throwable
                ) {
                    Toast.makeText(
                        requireContext(),
                        "Throwable" + t.localizedMessage,
                        Toast.LENGTH_LONG
                    ).show()

                }

                override fun onResponse(
                    call: Call<UpdateUserResponse>,
                    response: Response<UpdateUserResponse>
                ) {

                    Toast.makeText(requireContext(), "Information has been updated successfully", Toast.LENGTH_LONG).show()
                    pushFragment(PatientUserDashboardFragment())
                }

            })
    }






}