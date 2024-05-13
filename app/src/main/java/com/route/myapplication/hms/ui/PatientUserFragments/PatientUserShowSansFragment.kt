package com.route.myapplication.hms.ui.PatientUserFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.route.myapplication.hms.Constants
import com.route.myapplication.hms.R
import com.route.myapplication.hms.ui.DoctorUserFragments.PdfScanFragment
import com.route.myapplication.hms.ui.DoctorUserFragments.ScansAdapter
import com.route.myapplication.hms.ui.api.ApiManager
import com.route.myapplication.hms.ui.api.Model.GetAllScansResponseItem
import com.route.myapplication.hms.ui.api.Model.GetAllTestsResponseItem
import com.route.myapplication.hms.ui.api.Model.GetPatientTestsByPatientIdResponseItem
import com.route.myapplication.hms.ui.api.Model.GetPatientsScansByPatientIdResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PatientUserShowSansFragment : Fragment() {

    var scansList: List<GetAllScansResponseItem> = arrayListOf()
    var patientscansList: List<GetPatientsScansByPatientIdResponseItem> = arrayListOf()
    var selectedScansList: MutableList<GetPatientsScansByPatientIdResponseItem> = arrayListOf()
    lateinit var searchBtn : Button

    var scansNameList: MutableList<String> = ArrayList()
    lateinit var recyclerView: RecyclerView
    lateinit var scanSpinner: Spinner
    val adapter = PatientScansAdapter(null)

    val patientId = Constants.userID.toInt()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_patient_user_show_sans, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchBtn = requireView().findViewById(R.id.search_btn)
        recyclerView = requireView().findViewById(R.id.scans_recycler)
        recyclerView.adapter = adapter
        scanSpinner=requireView().findViewById(R.id.scanSpinner)

        getScans(patientId)
        getAllScans()


        searchBtn.setOnClickListener {
            val SelectedScanName = scanSpinner.selectedItem.toString()
            getScans(patientId,SelectedScanName)
        }

        adapter.onItemClickListener = object : PatientScansAdapter.OnItemClickListener {
            override fun onItemClick(pos: Int, item: GetPatientsScansByPatientIdResponseItem) {

                val bundle = Bundle()
                bundle.putSerializable("item", item)

                val pdfFragment: Fragment = PdfScanFragment()
                pdfFragment.arguments = bundle

                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, pdfFragment)
                    .addToBackStack("back")
                    .commit()

            }
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
                    for (scan in scansList) {
                        scansNameList.add(scan.scanName.toString())
                    }
                    val Spinneradapter: ArrayAdapter<String> = ArrayAdapter<String>(
                        requireContext(), android.R.layout.simple_spinner_item, scansNameList)
                    Spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    scanSpinner.adapter = Spinneradapter
                }
            })
    }


    private fun getScans(patientId: Int,ScanName: String?=null) {

        ApiManager.getApis().getPatientScansByPatientId(patientId)
            .enqueue(object : Callback<ArrayList<GetPatientsScansByPatientIdResponseItem>> {
                override fun onFailure(
                    call: Call<ArrayList<GetPatientsScansByPatientIdResponseItem>>,
                    t: Throwable
                ) {
                    Toast.makeText(
                        requireContext(),
                        "Throwable" + t.localizedMessage,
                        Toast.LENGTH_LONG
                    ).show()

                }

                override fun onResponse(
                    call: Call<ArrayList<GetPatientsScansByPatientIdResponseItem>>,
                    response: Response<ArrayList<GetPatientsScansByPatientIdResponseItem>>
                ) {
                    patientscansList = response.body()?.toList() ?: listOf()

                    if (ScanName != null){
                        selectedScansList.clear()
                        for (scan in patientscansList){
                            if(ScanName == scan.scanName)
                                selectedScansList.add(scan)
                        }
                        adapter.changeData(selectedScansList)
                    }
                    else
                        adapter.changeData(patientscansList)
                }
            })
    }
}