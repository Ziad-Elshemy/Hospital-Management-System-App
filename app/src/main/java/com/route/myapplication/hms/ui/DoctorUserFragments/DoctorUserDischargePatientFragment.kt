package com.route.myapplication.hms.ui.DoctorUserFragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import com.route.myapplication.hms.R
import com.route.myapplication.hms.ui.api.ApiManager
import com.route.myapplication.hms.ui.api.Model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime

class DoctorUserDischargePatientFragment : Fragment() {

    var patient : GetPatientByIdResponse = GetPatientByIdResponse()

    var patientrecordsList: List<GetIndoorPatientRecordByPatientIdResponseItem> = arrayListOf()

    var IndoorPatientsByDeptIdList: List<GetIndoorPatientsByDepartmentIdResponseItem> = arrayListOf()

    var reportRequest = AddReportRequest(null,null,null,null,null)

    @RequiresApi(Build.VERSION_CODES.O)
    val todayDate = LocalDateTime.now().toString()

    lateinit var patientName : TextView
    lateinit var enterDate : TextView
    lateinit var exitDate : TextView
    lateinit var recommendations : EditText
    lateinit var dischargebtn : Button
    lateinit var cancelbtn : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctor_user_discharge_patient, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        patientName = requireView().findViewById(R.id.patientNameInput)
        enterDate = requireView().findViewById(R.id.enterDateInput)
        exitDate = requireView().findViewById(R.id.exitDateInput)
        recommendations = requireView().findViewById(R.id.recommendationInput)
        dischargebtn = requireView().findViewById(R.id.dischargebtn)
        cancelbtn = requireView().findViewById(R.id.cancelbtn)

        enterDate.setText(todayDate.substring(0,todayDate.indexOf("T"))+"   "+todayDate.substring(todayDate.indexOf("T")+1,19))

       // enterDate.setText(todayDate)

        val bundle = requireArguments()
        val patientId: Int = bundle.getInt("patientId")

        getpatientById(patientId)

        dischargebtn.setOnClickListener {
            Discharge_addReport(reportRequest,patient)

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, DoctorUserInpatientFragment())
                .commit()
        }


        cancelbtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, DoctorUserInpatientFragment())
                .commit()
        }
    }

    private fun Discharge_addReport(reportRequest:AddReportRequest,patient: GetPatientByIdResponse){
        ApiManager.getApis().addPatientReport(reportRequest).enqueue(object :
            Callback<AddPatientReportResponse> {
            override fun onFailure(call: Call<AddPatientReportResponse>, t: Throwable) {
                //Toast.makeText(requireContext(),"Throwable"+t.localizedMessage, Toast.LENGTH_LONG).show()
                Toast.makeText(requireContext(),"there is a problem in the connection ",Toast.LENGTH_LONG).show()

            }

            override fun onResponse(
                call: Call<AddPatientReportResponse>,
                response: Response<AddPatientReportResponse>
            ) {
                Log.e("respone",response.body().toString())
                Toast.makeText(requireContext(),"patient"+patient.firstName+" "+patient.lastName+" has been discharged successfully",Toast.LENGTH_LONG).show()
            }
        })
    }


    private fun getrecords(patient: GetPatientByIdResponse,indoorRecordId: Int) {

        ApiManager.getApis().getIndoorPatientRecordsByPatientId(patient.id!!)
            .enqueue(object : Callback<ArrayList<GetIndoorPatientRecordByPatientIdResponseItem>> {
                override fun onFailure(
                    call: Call<ArrayList<GetIndoorPatientRecordByPatientIdResponseItem>>,
                    t: Throwable
                ) {
                    Toast.makeText(
                        requireContext(),
                        "Throwable" + t.localizedMessage,
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onResponse(
                    call: Call<ArrayList<GetIndoorPatientRecordByPatientIdResponseItem>>,
                    response: Response<ArrayList<GetIndoorPatientRecordByPatientIdResponseItem>>
                ) {
                    patientrecordsList = response.body()!!.toList()
                    patientrecordsList.forEach {
                        if (it.patientRecordId == indoorRecordId)
                            exitDate.setText(it.enterDate!!.substring(0,it.enterDate.indexOf("T"))+"   "+it.enterDate.substring(it.enterDate.indexOf("T")+1,19))
                         reportRequest = AddReportRequest(patient.id,todayDate, recommendations.text.toString(),indoorRecordId,it.enterDate!!)
                    }
                }
            })
    }

    private fun getIdoorPatientsByDeptId(deptId: Int , patient: GetPatientByIdResponse) {

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
                            getrecords(patient, it.indoorPatientId!!)
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
                    patientName.setText(patient.firstName+" "+patient.lastName)
                    getIdoorPatientsByDeptId(patient.departmentId!!,patient)
                }

            })
    }



}