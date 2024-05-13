package com.route.myapplication.hms.ui.DoctorUserFragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.route.myapplication.hms.R
import com.route.myapplication.hms.ui.api.ApiManager
import com.route.myapplication.hms.ui.api.Model.GetIndoorPatientRecordByPatientIdResponseItem
import com.route.myapplication.hms.ui.api.Model.GetIndoorPatientsResponseItem
import com.route.myapplication.hms.ui.api.Model.GetPatientByIdResponse
import com.route.myapplication.hms.ui.ui.DoctorInpatientAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DoctorUserPatientIndoorRecordFragment : Fragment() {

    var patient : GetPatientByIdResponse = GetPatientByIdResponse()
    lateinit var patientName: TextView
    var patientrecordsList: List<GetIndoorPatientRecordByPatientIdResponseItem> = arrayListOf()

    var recordsDateList: MutableList<String> = arrayListOf()
    var selectedrecordList: MutableList<GetIndoorPatientRecordByPatientIdResponseItem> = arrayListOf()

    lateinit var recyclerView: RecyclerView
    lateinit var recordSpinner: Spinner
    val adapter = DoctorInpatientRecordAdapter(null)
    lateinit var searchbtn : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(
            R.layout.fragment_doctor_user_patient_indoor_record,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        patientName = requireView().findViewById(R.id.name_input)
        recyclerView = requireView().findViewById(R.id.DoctorinpatientRecord_recyclerView)
        recyclerView.adapter = adapter
        recordSpinner = requireView().findViewById(R.id.recordsSpinner)
        searchbtn = requireView().findViewById(R.id.search_btn)
        val bundle = requireArguments()
        val patientId: Int = bundle.getInt("patientId")

        getpatientById(patientId)
        getrecords(patientId)

        searchbtn.setOnClickListener {
            var selectedSpinnerIndex = recordSpinner.selectedItemPosition
            var selectedrecordDate = recordSpinner.selectedItem.toString()
            getrecords(patientId, selectedSpinnerIndex)
        }

        adapter.onLabImgClickListener = object : DoctorInpatientRecordAdapter.OnImageClickListener {
            override fun onImageClick(
                pos: Int,
                item: GetIndoorPatientRecordByPatientIdResponseItem
            ) {

                val bundle = Bundle()
                bundle.putSerializable("patientRecord", item)


                val testrecordsFragment: Fragment = TestsRecordIndoorPatientFragment()
                testrecordsFragment.arguments = bundle
                pushFragment(testrecordsFragment)

            }
        }

            adapter.onScanImgClickListener = object : DoctorInpatientRecordAdapter.OnImageClickListener{
                override fun onImageClick(pos: Int, item: GetIndoorPatientRecordByPatientIdResponseItem) {

                    val bundle = Bundle()
                    bundle.putSerializable("patientRecord",item)


                    val scanrecordsFragment : Fragment = ScansRecordIndoorPatientFragment()
                    scanrecordsFragment.arguments  = bundle
                    pushFragment(scanrecordsFragment)

                }
        }

        adapter.onReportImgClickListener = object : DoctorInpatientRecordAdapter.OnImageClickListener{
            override fun onImageClick(pos: Int, item: GetIndoorPatientRecordByPatientIdResponseItem) {

                val patientReportRequest = PatientReportRequest(patientId,item.discahrgeDate.toString())
                val bundle = Bundle()

                bundle.putSerializable("requestReport", patientReportRequest)
                val reportpdfFragment: Fragment = PdfReportFragment()

                reportpdfFragment.arguments = bundle
                pushFragment(reportpdfFragment)

            }
        }

        adapter.onPrescriptionImgClickListener = object : DoctorInpatientRecordAdapter.OnImageClickListener{
            override fun onImageClick(pos: Int, item: GetIndoorPatientRecordByPatientIdResponseItem) {

                val bundle = Bundle()
                bundle.putSerializable("patientRecord",item)

                val reportpdfFragment: Fragment = PrescriptionRecordIndoorPatientFragment()

                reportpdfFragment.arguments = bundle
                pushFragment(reportpdfFragment)

            }
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

    private fun getrecords(patientId: Int,selectedSpinnerIndex:Int?=null) {

        ApiManager.getApis().getIndoorPatientRecordsByPatientId(patientId)
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
                    Log.e("recordslist",patientrecordsList.toString())

                    recordsDateList.clear()
                    patientrecordsList.forEach {
                        val enterDate = it.enterDate!!.substring(0, it.enterDate.indexOf("T"))
                        val endDate = it.discahrgeDate?.substring(0, it.discahrgeDate.indexOf("T"))
                        recordsDateList.add(enterDate + " - " + endDate)
                    }
                    val Spinneradapter: ArrayAdapter<String> = ArrayAdapter<String>(
                        requireContext(), android.R.layout.simple_spinner_item, recordsDateList
                    )
                    Spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    recordSpinner.adapter = Spinneradapter

                    if (selectedSpinnerIndex == null)
                        adapter.changeData(patientrecordsList)

                    else {
                        selectedrecordList.clear()

                        selectedrecordList.add(patientrecordsList[selectedSpinnerIndex])
                        Log.e("selectedlist", selectedrecordList.toString())
                        adapter.changeData(selectedrecordList)
                    }
                }
            })
    }

    private fun pushFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack("back")
            .commit()
    }




}