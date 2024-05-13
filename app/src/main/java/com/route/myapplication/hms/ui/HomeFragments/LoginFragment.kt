package com.route.myapplication.hms.ui.HomeFragments

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.auth0.android.jwt.JWT
import com.route.myapplication.hms.Constants
import com.route.myapplication.hms.R
import com.route.myapplication.hms.ui.DashboardActivity
import com.route.myapplication.hms.ui.UserDoctorActivity
import com.route.myapplication.hms.ui.UserNurseActivity
import com.route.myapplication.hms.ui.UserPatientActivity
import com.route.myapplication.hms.ui.api.ApiManager
import com.route.myapplication.hms.ui.api.Model.LoginRequest
import com.route.myapplication.hms.ui.api.Model.LoginResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class LoginFragment : Fragment() {

    lateinit var loginbtn: Button
    lateinit var id_input: EditText
    lateinit var pass_input: EditText

    lateinit var userToken: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginbtn = requireView().findViewById(R.id.loginbtn)


        id_input = requireView().findViewById(R.id.idInput)
        pass_input = requireView().findViewById(R.id.passInput)


        loginbtn.setOnClickListener {

            if(id_input.text.toString().trim().length > 0){
                userLogin()
            }else{
                Toast.makeText(requireContext(),"Username / Password Required",Toast.LENGTH_LONG).show()
            }

//            if (id_input.text.toString() == "admin" && pass_input.text.toString() == "123") {
//                val intent_admin = Intent(context, DashboardActivity::class.java)
//                startActivity(intent_admin)

//            } else if (id_input.text.toString() == "doctor" && pass_input.text.toString() == "123") {
//                val intent_doctor = Intent(context, UserDoctorActivity::class.java)
//                startActivity(intent_doctor)
//            }
//            else if (id_input.text.toString() == "nurse" && pass_input.text.toString() == "123") {
//                val intent_nurse = Intent(context, UserNurseActivity::class.java)
//                startActivity(intent_nurse)
//            }
        }

    }

    private fun userLogin() {
        val loginRequest = LoginRequest(id_input.text.toString(),pass_input.text.toString())

        ApiManager.getApis().userLogin(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(requireContext(),"Throwable"+t.localizedMessage, Toast.LENGTH_LONG).show()

            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

                if (response.body()?.status.toString()=="successful"){
                userToken = response.body()?.token.toString()
                val mDecode = decodeToken(userToken)
                val id = JSONObject(mDecode).getString("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/nameidentifier")
                Constants.userID = id

                Log.e("------------------>", mDecode.toString());

                if(response.body()?.status.toString()=="successful"&&response.body()?.role.toString()=="Doctor"){

                    userToken = response.body()?.token.toString()
                    Constants.token=userToken
                    val intent_doctor = Intent(context, UserDoctorActivity::class.java)
                    intent_doctor.putExtra("EXTRA_USER_TOKEN", userToken)
                    startActivity(intent_doctor)
                }
                else if(response.body()?.status.toString()=="successful"&&response.body()?.role.toString()=="Admin") {
                    response
                    userToken = response.body()?.token.toString()
//                    val jwt = JWT(userToken)
//                    val claim = jwt.getClaim("")


                    Constants.token=userToken
                    val intent_admin = Intent(context, DashboardActivity::class.java)
                    intent_admin.putExtra("EXTRA_USER_TOKEN", userToken)
                    startActivity(intent_admin)
                }
                else if(response.body()?.status.toString()=="successful"&&response.body()?.role.toString()=="Nurse") {
                    response
                    userToken = response.body()?.token.toString()
                    Constants.token=userToken
                    val intent_nurse = Intent(context, UserNurseActivity::class.java)
                    intent_nurse.putExtra("EXTRA_USER_TOKEN", userToken)
                    startActivity(intent_nurse)
                }
                else if(response.body()?.status.toString()=="successful"&&response.body()?.role.toString()=="Patient") {
                    response
                    userToken = response.body()?.token.toString()
                    Constants.token=userToken
                    val intent_patient = Intent(context, UserPatientActivity::class.java)
                    intent_patient.putExtra("EXTRA_USER_TOKEN", userToken)
                    startActivity(intent_patient)
                }}
                else{
                    Toast.makeText(requireContext(),""+response.body()?.status, Toast.LENGTH_LONG).show()
                }
            }
        } )
    }

    private fun decodeToken(jwt: String): String {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return "Requires SDK 26"
        val parts = jwt.split(".")
        return try {
            val charset = charset("UTF-8")
            val header = String(Base64.getUrlDecoder().decode(parts[0].toByteArray(charset)), charset)
            val payload = String(Base64.getUrlDecoder().decode(parts[1].toByteArray(charset)), charset)
            "$header"
            "$payload"
        } catch (e: Exception) {
            "Error parsing JWT: $e"
        }
    }


}