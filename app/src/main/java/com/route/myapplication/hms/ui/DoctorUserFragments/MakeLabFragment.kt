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
import androidx.core.view.forEach
import com.route.myapplication.hms.R
import com.route.myapplication.hms.ui.api.ApiManager
import com.route.myapplication.hms.ui.api.Model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime


class MakeLabFragment : Fragment() {

    var patient : GetPatientByIdResponse = GetPatientByIdResponse()
    lateinit var patientName : TextView
    var indoorPatientRecordId :Int = 0
    var IndoorPatientsByDeptIdList: List<GetIndoorPatientsByDepartmentIdResponseItem> = arrayListOf()

    var testsList: List<GetAllTestsResponseItem> = arrayListOf()

    lateinit var testLinearLayout: LinearLayout
    lateinit var tests: MutableList<String>

    lateinit var submitbtn : Button
    lateinit var cancelbtn : Button

    var atleast_one_item_is_cheacked_flag : Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_make_lab, container, false)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

        getAllTests()

        val bundle = requireArguments()
        val patientId: Int = bundle.getInt("patientId")

        getpatientById(patientId)

        submitbtn.setOnClickListener {

            checkTests(patientId)

//            if(atleast_one_item_is_cheacked_flag)
//            {
//                parentFragmentManager.beginTransaction()
//                    .replace(R.id.fragment_container, DoctorUserInpatientFragment())
//                    .commit()
//
//            }
         //   else
                if (tests.isEmpty()){
                Toast.makeText(requireContext(),"you have not selected any lab test", Toast.LENGTH_LONG).show()

            }

        }

        cancelbtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkTests(patientId:Int) {
        tests = arrayListOf()
        testLinearLayout.forEach {
            if(it is CheckBox) {
                if (it.isChecked) {
                    atleast_one_item_is_cheacked_flag = true
                    tests.add(it.text.toString())
                }
            }
        }

        tests.forEach {
            val current = LocalDateTime.now().toString()

            val testRequest = TestRequest(it,current,patientId,1, indoorPatientRecordId)
            Log.e("respone",testRequest.toString())
            ApiManager.getApis().addTestRequest(testRequest).enqueue(object :
                Callback<AddTestResponse> {
                override fun onFailure(call: Call<AddTestResponse>, t: Throwable) {
                    //Toast.makeText(requireContext(),"Throwable"+t.localizedMessage, Toast.LENGTH_LONG).show()
                    Toast.makeText(requireContext(),"there is a problem in the connection ",Toast.LENGTH_LONG).show()

                }

                override fun onResponse(
                    call: Call<AddTestResponse>,
                    response: Response<AddTestResponse>
                ) {
                    Log.e("respone",response.body().toString())
                    Toast.makeText(requireContext(),"your request has been sent successfully",Toast.LENGTH_LONG).show()

                    parentFragmentManager.popBackStack()


                }
            })
        }

    }

    private fun initView() {
        patientName = requireView().findViewById(R.id.patientNameInput)

        testLinearLayout = requireView().findViewById(R.id.lab_linearLayout)
        tests = arrayListOf()

        submitbtn = requireView().findViewById(R.id.submitbtn)
        cancelbtn = requireView().findViewById(R.id.cancelbtn)

    }


    private fun getAllTests() {

        ApiManager.getApis().getAllTest()
            .enqueue(object : Callback<ArrayList<GetAllTestsResponseItem>> {
                override fun onFailure(
                    call: Call<ArrayList<GetAllTestsResponseItem>>,
                    t: Throwable
                ) {
                    Toast.makeText(
                        requireContext(),
                        "Throwable" + t.localizedMessage,
                        Toast.LENGTH_LONG
                    ).show()

                }

                override fun onResponse(
                    call: Call<ArrayList<GetAllTestsResponseItem>>,
                    response: Response<ArrayList<GetAllTestsResponseItem>>
                ) {
                    testsList = response.body()!!.toList()
                    for(test in testsList) {
                        var cb = CheckBox(requireContext())
                        cb.setText(test.name)
                        testLinearLayout.addView(cb)
                    }
                }
            })
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
                    patientName.setText(patient.firstName+" "+patient.lastName)
                    Log.e("deptId",patient.departmentId.toString())
                    getIdoorPatientsByDeptId(patient.departmentId!!)
                }

            })
    }



}