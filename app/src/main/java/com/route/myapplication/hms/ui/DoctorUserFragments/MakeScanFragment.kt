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


class MakeScanFragment : Fragment() {

    var patient : GetPatientByIdResponse = GetPatientByIdResponse()
    lateinit var patientName : TextView
    var indoorPatientRecordId :Int = 0
    var IndoorPatientsByDeptIdList: List<GetIndoorPatientsByDepartmentIdResponseItem> = arrayListOf()


    var IndoorPatientsByIdList: List<GetIndoorPatientRecordByPatientIdResponseItem> = arrayListOf()

    var scansList: List<GetAllScansResponseItem> = arrayListOf()


    lateinit var scanLinearLayout: LinearLayout
    lateinit var scans: MutableList<String>

    lateinit var submitbtn : Button
    lateinit var cancelbtn : Button


    var atleast_one_item_is_cheacked_flag : Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_make_scan, container, false)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

        getAllScans()

        val bundle = requireArguments()
        val patientId: Int = bundle.getInt("patientId")

        getpatientById(patientId)



        submitbtn.setOnClickListener {

                checkScans(patientId)
//            if(atleast_one_item_is_cheacked_flag)
//            {
//                parentFragmentManager.beginTransaction()
//                    .replace(R.id.fragment_container, DoctorUserInpatientFragment())
//                    .commit()
//
//           }
                       // else
                            if (scans.isEmpty()){
                Toast.makeText(requireContext(),"you have not selected any scan",Toast.LENGTH_LONG).show()

            }

        }

        cancelbtn.setOnClickListener {
            parentFragmentManager.popBackStack()

        }


    }

    private fun getAllScans() {
        ApiManager.getApis().getAllScan()
            .enqueue(object : Callback<ArrayList<GetAllScansResponseItem>> {
                override fun onFailure(
                    call: Call<ArrayList<GetAllScansResponseItem>>,
                    t: Throwable
                ) {
                    Toast.makeText(
                        requireContext(),
                        "Throwable" + t.localizedMessage,
                        Toast.LENGTH_LONG
                    ).show()

                }

                override fun onResponse(
                    call: Call<ArrayList<GetAllScansResponseItem>>,
                    response: Response<ArrayList<GetAllScansResponseItem>>
                ) {
                    scansList = response.body()!!.toList()
                    for(scan in scansList) {
                        var cb = CheckBox(requireContext())
                        cb.setText(scan.scanName)
                        scanLinearLayout.addView(cb)
                    }
                }
            })
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkScans(patientId:Int) {
        scans = arrayListOf()
        scanLinearLayout.forEach {
            if(it is CheckBox) {
                if (it.isChecked) {
                    atleast_one_item_is_cheacked_flag = true
                    scans.add(it.text.toString())
                }
            }
        }

        scans.forEach {

            val current = LocalDateTime.now().toString()
            val scanRequest = ScanRequest(it,current,patientId,1,indoorPatientRecordId)
            ApiManager.getApis().addScanRequest(scanRequest).enqueue(object : Callback<AddScanResponse>{
                override fun onFailure(call: Call<AddScanResponse>, t: Throwable) {
                    Toast.makeText(requireContext(),"Throwable"+t.localizedMessage, Toast.LENGTH_LONG).show()

                }

                override fun onResponse(
                    call: Call<AddScanResponse>,
                    response: Response<AddScanResponse>)
                {
                    Log.e("respone",response.body().toString())
                    Toast.makeText(requireContext(),"your request has been sent successfully",Toast.LENGTH_LONG).show()

                    parentFragmentManager.popBackStack()

                }
            })
        }

    }



    private fun initView() {

        patientName = requireView().findViewById(R.id.patientNameInput)
        scanLinearLayout = requireView().findViewById(R.id.scan_linearLayout)
        scans = arrayListOf()

        submitbtn = requireView().findViewById(R.id.submitbtn)
        cancelbtn = requireView().findViewById(R.id.cancelbtn)

    }

    private fun getIndoorPatientRecordByID(patientId: Int){
        ApiManager.getApis().getIndoorPatientRecordsByPatientId(patientId)
            .enqueue(object : Callback<ArrayList<GetIndoorPatientRecordByPatientIdResponseItem>>{
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
                    IndoorPatientsByIdList = response.body()!!.toList()
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