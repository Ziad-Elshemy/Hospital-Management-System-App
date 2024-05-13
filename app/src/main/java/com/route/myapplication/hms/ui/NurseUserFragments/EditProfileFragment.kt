package com.route.myapplication.hms.ui.NurseUserFragments

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.InputFilter
import android.util.Log
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
import com.route.myapplication.hms.ui.UserNurseActivity
import com.route.myapplication.hms.ui.api.ApiManager
import com.route.myapplication.hms.ui.api.Model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime

class EditProfileFragment : Fragment() {

    var nurse : GetNurseByIdResponse = GetNurseByIdResponse()

    var genderInput = ""

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
    //lateinit var note : EditText

    lateinit var updateBtn : Button
    lateinit var cancelBtn : Button

     var nurseDeptId : Int? = null
    lateinit var nurseDeptName :String

    val nurseId = Constants.userID.toInt()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firstName = requireView().findViewById(R.id.fname_input)
        ageInput = requireView().findViewById(R.id.age_input)
        ageInput.filters = arrayOf<InputFilter>(InputFilterMinMax("1", "99"))
        lastName = requireView().findViewById(R.id.lname_input)
        userName = requireView().findViewById(R.id.userName_input)
        nationalId = requireView().findViewById(R.id.nationalId_input)
        phoneNumber = requireView().findViewById(R.id.phoneNumber_input)
        email = requireView().findViewById(R.id.email_input)
        bloodType = requireView().findViewById(R.id.bloodType_input)
        address = requireView().findViewById(R.id.address_input)
        gender = requireView().findViewById(R.id.gender_input)
        degree = requireView().findViewById(R.id.Degree_input)
        specialization = requireView().findViewById(R.id.specialization_input)
        //note = requireView().findViewById(R.id.note_input)


        updateBtn = requireView().findViewById(R.id.submitbtn_edit)
        cancelBtn = requireView().findViewById(R.id.cancelbtn_edit)


        getnurseById(nurseId)
        updateBtn.setOnClickListener {
            val todayDate = LocalDateTime.now().toLocalDate().toString()

            val valid = isEmailValid(email.text.toString())
            if (isEmailValid(email.text.toString())) {
                updateNurse(
                    nurseId,
                    firstName.text.toString(),
                    lastName.text.toString(),
                    todayDate,
                    ageInput.text.toString().toInt(),
                    nationalId.text.toString(),
                    null,
                    null,
                    bloodType.text.toString(),
                    phoneNumber.text.toString(),
                    address.text.toString(),
                    gender.selectedItem.toString(),
                    userName.text.toString(),
                    email.text.toString(),
                    null,
                    "Nurse",
                    nurseDeptId,
                    nurseDeptName,
                    true,
                    null,
                    degree.text.toString(),
                    specialization.text.toString(),
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

        private fun pushFragment(fragment: Fragment) {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack("back")
                .commit()
        }

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
                        firstName.setText(nurse.firstName)
                        lastName.setText(nurse.lastName)
                        userName.setText(nurse.userName)
                        ageInput.setText(nurse.age.toString())
                        nationalId.setText(nurse.nationalId)
                        phoneNumber.setText(nurse.phoneNumber)
                        email.setText(nurse.mail)
                        bloodType.setText(nurse.bloodType)
                        address.setText(nurse.address)
                        nurseDeptId = nurse.departmentId
                        nurseDeptName = nurse.departmentName.toString()
                        degree.setText(nurse.nurseDegree)
                        specialization.setText(nurse.nurseSpecialization)
                        if(nurse.gender == "male" || nurse.gender == "MALE" || nurse.gender == "Male") {
                            gender.setSelection(0)
                        } else if(nurse.gender == "female" || nurse.gender == "FEMALE" || nurse.gender == "Female"){
                            gender.setSelection(1)
                        }

                    }

                })
        }


        private fun updateNurse(id:Int,fName:String,lName:String,date:String,age:Int,nationalId:String,image:String?,imageName:String?,
                                  bloodType:String,phoneNumber:String,address:String,gender:String,userName:String,mail:String,password:String?,role:String,deptId:Int?,
                                  deptName:String?,isActive:Boolean,nurseNotes:String?,nurseDegree:String,nurseSpecialization:String) {

            val updateRequest = UpdateNurseRequest(id,fName,lName,date,age,nationalId,image,imageName,bloodType,phoneNumber,address,gender,userName,mail
                ,password,role,deptId,deptName,isActive, nurseNotes,nurseDegree,nurseSpecialization)

            ApiManager.getApis().updateNurseRequest(updateRequest)
                .enqueue(object : Callback<UpdateNurseResponse> {
                    override fun onFailure(
                        call: Call<UpdateNurseResponse>,
                        t: Throwable
                    ) {
                        Toast.makeText(
                            requireContext(),
                            "Throwable" + t.localizedMessage,
                            Toast.LENGTH_LONG
                        ).show()

                    }

                    override fun onResponse(
                        call: Call<UpdateNurseResponse>,
                        response: Response<UpdateNurseResponse>
                    ) {

                        Toast.makeText(requireContext(), "Information has been updated successfully", Toast.LENGTH_LONG).show()
                       // pushFragment(NurseUserProfileFragment())
                        activity?.let{
                            val intent = Intent (it, UserNurseActivity::class.java)
                            it.startActivity(intent)
                        }
                    }

                })
        }

}