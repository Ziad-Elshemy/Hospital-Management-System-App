package com.route.myapplication.hms.ui.DoctorUserFragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.route.myapplication.hms.R
import com.route.myapplication.hms.ui.api.ApiManager
import com.route.myapplication.hms.ui.api.Model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TestsRecordIndoorPatientFragment : Fragment() {
    var patient : GetPatientByIdResponse = GetPatientByIdResponse()
    lateinit var patientName: TextView

    var patientTest = GetPatientTestByTestIdResponse()

    var patientTestsList: MutableList<GetPatientTestByTestIdResponse> = arrayListOf()

    var patientTestrecordsList: List<PatientTestsItem?> = arrayListOf()

    lateinit var recyclerView: RecyclerView
    val adapter = TestsIndoorRecordAdapter(null)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tests_record_indoor_patient, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        patientName = requireView().findViewById(R.id.name_input)
        recyclerView = requireView().findViewById(R.id.tests_recycler)
        recyclerView.adapter = adapter

        val bundle = requireArguments()
        val patientRecord: GetIndoorPatientRecordByPatientIdResponseItem = bundle!!.getSerializable("patientRecord") as GetIndoorPatientRecordByPatientIdResponseItem

        patientTestrecordsList = patientRecord.patientTests?.toList() ?: listOf()
        patientTestsList.clear()
        patientTestrecordsList.forEach {
            getpatientTestById(it?.patientTestId!!)
            getpatientById(it.patientId!!)
        }
            adapter.onItemClickListener = object : TestsIndoorRecordAdapter.OnItemClickListener {
                override fun onItemClick(pos: Int, item: GetPatientTestByTestIdResponse) {

                    val bundle = Bundle()
                    bundle.putSerializable("item",item)

                    val testRecordFragment : Fragment = PdfLabTestRecordFragment()
                    testRecordFragment.arguments  = bundle

                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, testRecordFragment)
                        .addToBackStack("back")
                        .commit()

                }

        }



//        adapter.onItemClickListener = object : TestsIndoorRecordAdapter.OnItemClickListener {
//            override fun onItemClick(pos: Int, item: GetPatientTestByTestIdResponse) {
//
//                val bundle = Bundle()
//                bundle.putSerializable("item",item)
//
//                val testFragment : Fragment = PdfLabTestFragment()
//                testFragment.arguments  = bundle
//
//                parentFragmentManager.beginTransaction()
//                    .replace(R.id.fragment_container, testFragment)
//                    .addToBackStack("back")
//                    .commit()
//
//            }
//        }


    }

    private fun getpatientTestById(testId: Int) {

        ApiManager.getApis().getPatientTestByTestId(testId)
            .enqueue(object : Callback<GetPatientTestByTestIdResponse> {
                override fun onFailure(
                    call: Call<GetPatientTestByTestIdResponse>,
                    t: Throwable
                ) {
                    Toast.makeText(
                        requireContext(),
                        "Throwable" + t.localizedMessage,
                        Toast.LENGTH_LONG
                    ).show()

                }

                override fun onResponse(
                    call: Call<GetPatientTestByTestIdResponse>,
                    response: Response<GetPatientTestByTestIdResponse>
                ) {
                    patientTest = response.body()!!
                    patientTestsList.add(patientTest)

                    adapter.changeData(patientTestsList)

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
                }

            })
    }



}