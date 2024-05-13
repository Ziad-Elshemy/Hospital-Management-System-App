package com.route.myapplication.hms.ui.DoctorUserFragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.route.myapplication.hms.R
import com.route.myapplication.hms.ui.api.ApiManager
import com.route.myapplication.hms.ui.api.Model.GetAllTestsResponseItem
import com.route.myapplication.hms.ui.api.Model.GetPatientByIdResponse
import com.route.myapplication.hms.ui.api.Model.GetPatientTestsByPatientIdResponseItem
import com.route.myapplication.hms.ui.api.Model.GetPatientsScansByPatientIdResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetLabAppointmentFragment : Fragment() {

    var patient : GetPatientByIdResponse = GetPatientByIdResponse()
    lateinit var patientName:TextView
    var testsList: List<GetAllTestsResponseItem> = arrayListOf()
    var patienttestsList: List<GetPatientTestsByPatientIdResponseItem> = arrayListOf()
    var selectedtestsList: MutableList<GetPatientTestsByPatientIdResponseItem> = arrayListOf()

    lateinit var searchBtn : Button

    var testsNameList: MutableList<String> = ArrayList()
    lateinit var recyclerView: RecyclerView
    lateinit var testSpinner: Spinner
    val adapter = TestsAdapter(null)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_get_lab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        patientName = requireView().findViewById(R.id.name_input)
        recyclerView = requireView().findViewById(R.id.tests_recycler)
        recyclerView.adapter = adapter
        testSpinner=requireView().findViewById(R.id.testSpinner)
        searchBtn = requireView().findViewById(R.id.search_btn)


        val bundle = requireArguments()
        val patientId: Int = bundle.getInt("patientId")

        getpatientById(patientId)
        getTests(patientId)
        getAllTests()

        searchBtn.setOnClickListener {
            val SelectedTestName = testSpinner.selectedItem.toString()
            getTests(patientId,SelectedTestName)
        }
//        testSpinner.onItemSelectedListener = object :
//            AdapterView.OnItemSelectedListener{
//            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                val SelectedTestName: String = p0?.getItemAtPosition(p2).toString()
//                getTests(patientId,SelectedTestName.toString())
//            }
//
//            override fun onNothingSelected(p0: AdapterView<*>?) {
//                TODO("Not yet implemented")
//            }
//        }


        adapter.onItemClickListener = object : TestsAdapter.OnItemClickListener {
            override fun onItemClick(pos: Int, item: GetPatientTestsByPatientIdResponseItem) {

                val bundle = Bundle()
                bundle.putSerializable("item",item)

                val testFragment : Fragment = PdfLabTestFragment()
                testFragment.arguments  = bundle

                pushFragment(testFragment)

//                parentFragmentManager.beginTransaction()
//                    .replace(R.id.fragment_container, testFragment)
//                    .addToBackStack("back")
//                    .commit()

            }
        }


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
                    for (test in testsList) {
                        testsNameList.add(test.name.toString())
                    }
                    val Spinneradapter: ArrayAdapter<String> = ArrayAdapter<String>(
                        requireContext(), android.R.layout.simple_spinner_item, testsNameList)
                    Spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    testSpinner.adapter = Spinneradapter



//                    testSpinner.onItemSelectedListener = object :
//                        AdapterView.OnItemSelectedListener{
//                        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                            val SelectedTestName: String = p0?.getItemAtPosition(p2).toString()
//                            getTests(patientId,SelectedTestName.toString())
//                        }
//
//                        override fun onNothingSelected(p0: AdapterView<*>?) {
//                            TODO("Not yet implemented")
//                        }
//                    }

                }
            })
    }


    private fun getTests(patientId: Int,TestName: String?=null) {

        ApiManager.getApis().getPatientTestsByPatientId(patientId)
            .enqueue(object : Callback<ArrayList<GetPatientTestsByPatientIdResponseItem>> {
                override fun onFailure(
                    call: Call<ArrayList<GetPatientTestsByPatientIdResponseItem>>,
                    t: Throwable
                ) {
                    Toast.makeText(
                        requireContext(),
                        "Throwable" + t.localizedMessage,
                        Toast.LENGTH_LONG
                    ).show()

                }

                override fun onResponse(
                    call: Call<ArrayList<GetPatientTestsByPatientIdResponseItem>>,
                    response: Response<ArrayList<GetPatientTestsByPatientIdResponseItem>>
                ) {
                    patienttestsList = response.body()!!.toList()
                    Log.e("patienttestsList",patienttestsList.toString())

                    if (TestName != null){
                        selectedtestsList.clear()
                        for (test in patienttestsList){
                            if(TestName == test.testName)
                                selectedtestsList.add(test)
                        }
                        Log.e("selectedtestsList",selectedtestsList.toString())
                        adapter.changeData(selectedtestsList)
                    }
                    else
                        adapter.changeData(patienttestsList)
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

    private fun pushFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack("")
            .commit()
    }


}