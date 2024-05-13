package com.route.myapplication.hms.ui.HomeFragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.route.myapplication.hms.R
import com.route.myapplication.hms.ui.DoctorUserFragments.TestsAdapter
import com.route.myapplication.hms.ui.api.ApiManager
import com.route.myapplication.hms.ui.api.Model.GetAllDeptsResponseItem
import com.route.myapplication.hms.ui.api.Model.GetAllDoctorsResponseItem
import com.route.myapplication.hms.ui.api.Model.GetPatientTestsByPatientIdResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DoctorFragment : Fragment() {


    var doctorsList: List<GetAllDoctorsResponseItem> = arrayListOf()
    var deptList :List<GetAllDeptsResponseItem> = arrayListOf()

    var selectedDoctorList: MutableList<GetAllDoctorsResponseItem> = arrayListOf()

    var deptsNameList: MutableList<String> = ArrayList()
    lateinit var recyclerView: RecyclerView
    lateinit var deptSpinner: Spinner
    val adapter = DoctorsAdapter(null)
    lateinit var searchBtn : Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctor, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = requireView().findViewById(R.id.doctors_recycler)
        recyclerView.adapter = adapter
        deptSpinner=requireView().findViewById(R.id.deptSpinner)
        searchBtn = requireView().findViewById(R.id.search_btn)
        getAllDoctors()
        getAllDepts()

        searchBtn.setOnClickListener {
            val SelectedDeptName = deptSpinner.selectedItem.toString()
            getAllDoctors(SelectedDeptName)
        }

    }

    private fun getAllDoctors(deptName: String?=null) {

        ApiManager.getApis().getAllDoctors()
            .enqueue(object : Callback<ArrayList<GetAllDoctorsResponseItem>> {
                override fun onFailure(
                    call: Call<ArrayList<GetAllDoctorsResponseItem>>,
                    t: Throwable
                ) {
                    Toast.makeText(
                        requireContext(),
                        "Throwable" + t.localizedMessage,
                        Toast.LENGTH_LONG
                    ).show()

                }

                override fun onResponse(
                    call: Call<ArrayList<GetAllDoctorsResponseItem>>,
                    response: Response<ArrayList<GetAllDoctorsResponseItem>>
                ) {
                    doctorsList = response.body()!!.toList()
                   // adapter.changeData(doctorsList)

                    if (deptName != null){
                        selectedDoctorList.clear()
                        for (doctor in doctorsList){
                            if(deptName == doctor.departmentName)
                                selectedDoctorList.add(doctor)
                        }
                        Log.e("selectedtestsList",selectedDoctorList.toString())
                        adapter.changeData(selectedDoctorList)
                    }
                    else
                        adapter.changeData(doctorsList)

                }

            })
    }

    private fun getAllDepts() {

        ApiManager.getApis().getAllDepts()
            .enqueue(object : Callback<ArrayList<GetAllDeptsResponseItem>> {
                override fun onFailure(
                    call: Call<ArrayList<GetAllDeptsResponseItem>>,
                    t: Throwable
                ) {
                    Toast.makeText(
                        requireContext(),
                        "Throwable" + t.localizedMessage,
                        Toast.LENGTH_LONG
                    ).show()

                }

                override fun onResponse(
                    call: Call<ArrayList<GetAllDeptsResponseItem>>,
                    response: Response<ArrayList<GetAllDeptsResponseItem>>
                ) {
                    deptList = response.body()!!.toList()
                    for (dept in deptList) {
                        deptsNameList.add(dept.departmentName.toString())
                    }
                    val Spinneradapter: ArrayAdapter<String> = ArrayAdapter<String>(
                        requireContext(), android.R.layout.simple_spinner_item, deptsNameList)
                    Spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    deptSpinner.adapter = Spinneradapter
                }
            })
    }




}