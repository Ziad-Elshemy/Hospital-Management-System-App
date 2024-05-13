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
import com.route.myapplication.hms.ui.api.Model.GetIndoorPatientRecordByPatientIdResponseItem
import com.route.myapplication.hms.ui.api.Model.GetPatientByIdResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PatientReportFragment : Fragment() {

    var patient : GetPatientByIdResponse = GetPatientByIdResponse()
    lateinit var patientName: TextView
    var patientreportsListResponse: List<GetIndoorPatientRecordByPatientIdResponseItem> = arrayListOf()
    var patientreportsList: MutableList<GetIndoorPatientRecordByPatientIdResponseItem> = arrayListOf()

    var reportsDateList: MutableList<String> = arrayListOf()
    var selectedReportList: MutableList<GetIndoorPatientRecordByPatientIdResponseItem> = arrayListOf()

    lateinit var recyclerView: RecyclerView
    lateinit var reportSpinner: Spinner
    //val adapter = ReportAdapter(null)

    var spinnerListAddedFlag=false
    lateinit var searchbtn : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_patient_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        patientName = requireView().findViewById(R.id.name_input)
        recyclerView = requireView().findViewById(R.id.reports_recycler)
        //recyclerView.adapter = adapter
        reportSpinner = requireView().findViewById(R.id.reportSpinner)
        searchbtn = requireView().findViewById(R.id.searchtbtn)

        val bundle = requireArguments()
        val patientId: Int = bundle.getInt("patientId")

        getpatientById(patientId)
        //getReports(patientId)

//        adapter.onItemClickListener = object : ReportAdapter.OnItemClickListener {
//            override fun onItemClick(pos: Int, item: GetIndoorPatientRecordByPatientIdResponseItem) {
//
//                val patientReportRequest = PatientReportRequest(patientId,item.discahrgeDate.toString())
//
//                val bundle = Bundle()
//                bundle.putSerializable("requestReport", patientReportRequest)
//
//                val reportpdfFragment: Fragment = PdfReportFragment()
//                reportpdfFragment.arguments = bundle
//
//                parentFragmentManager.beginTransaction()
//                    .replace(R.id.fragment_container, reportpdfFragment)
//                    .addToBackStack("back")
//                    .commit()
//
//            }
//        }

        searchbtn.setOnClickListener {
            var selectedSpinnerIndex = reportSpinner.selectedItemPosition
            var selectedReportDate = reportSpinner.selectedItem.toString()
           // getReports(patientId, selectedSpinnerIndex)
        }

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


//    private fun getReports(patientId: Int,selectedSpinnerIndex:Int?=null) {
//
//        ApiManager.getApis().getIndoorPatientRecordsByPatientId(patientId)
//            .enqueue(object : Callback<ArrayList<GetIndoorPatientRecordByPatientIdResponseItem>> {
//                override fun onFailure(
//                    call: Call<ArrayList<GetIndoorPatientRecordByPatientIdResponseItem>>,
//                    t: Throwable
//                ) {
//                    Toast.makeText(
//                        requireContext(),
//                        "Throwable" + t.localizedMessage,
//                        Toast.LENGTH_LONG
//                    ).show()
//
//                }
//
//                override fun onResponse(
//
//                    call: Call<ArrayList<GetIndoorPatientRecordByPatientIdResponseItem>>,
//                    response: Response<ArrayList<GetIndoorPatientRecordByPatientIdResponseItem>>
//                ) {
//                    patientreportsListResponse = response.body()!!.toList()
//                    Log.e("reportslist",patientreportsList.toString())
//                    patientreportsList.clear()
//
//                    patientreportsListResponse.forEach {
//                        if (it.discahrgeDate!=null)
//                            patientreportsList.add(it)
//                        Log.e("reportslist",patientreportsList.toString())
//                    }
//
//
//                    reportsDateList.clear()
//                    patientreportsList.forEach {
//                        val enterDate = it.enterDate!!.substring(0, it.enterDate.indexOf("T"))
//                        val endDate = it.discahrgeDate!!.substring(0, it.discahrgeDate.indexOf("T"))
//                        reportsDateList.add(enterDate + " - " + endDate)
//                         }
//                        val Spinneradapter: ArrayAdapter<String> = ArrayAdapter<String>(
//                            requireContext(), android.R.layout.simple_spinner_item, reportsDateList
//                        )
//                        Spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//                        reportSpinner.adapter = Spinneradapter
//
//
//                    if (selectedSpinnerIndex == null)
//                        adapter.changeData(patientreportsList)
//
//                    else {
//                            selectedReportList.clear()
//
//                        selectedReportList.add(patientreportsList[selectedSpinnerIndex])
//                        Log.e("selectedlist", selectedReportList.toString())
//                        adapter.changeData(selectedReportList)
//                    }
//                }
//            })
//    }


}