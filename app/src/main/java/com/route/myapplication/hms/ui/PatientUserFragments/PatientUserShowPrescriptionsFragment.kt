package com.route.myapplication.hms.ui.PatientUserFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.route.myapplication.hms.Constants
import com.route.myapplication.hms.R
import com.route.myapplication.hms.ui.DoctorUserFragments.PdfFragment
import com.route.myapplication.hms.ui.DoctorUserFragments.PrescriptionListAdapter
import com.route.myapplication.hms.ui.api.ApiManager
import com.route.myapplication.hms.ui.api.Model.GetAllPrescriptionsOfPatientResponseItem
import com.route.myapplication.hms.ui.api.Model.GetPatientByIdResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PatientUserShowPrescriptionsFragment : Fragment() {

    var patient : GetPatientByIdResponse = GetPatientByIdResponse()
    var prescriptionsList: List<GetAllPrescriptionsOfPatientResponseItem> = arrayListOf()
    lateinit var recyclerView: RecyclerView
    val adapter = PrescriptionListAdapter(null)

    val patientId = Constants.userID.toInt()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_patient_user_sow_prescriptions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = requireView().findViewById(R.id.prescriptions_recycler)


        getPrescription(patientId)
        getpatientById(patientId)
        recyclerView.adapter = adapter


        adapter.onItemClickListener = object : PrescriptionListAdapter.OnItemClickListener {
            override fun onItemClick(pos: Int, item: GetAllPrescriptionsOfPatientResponseItem) {

                val bundle = Bundle()
                bundle.putSerializable("item",item)

                val pdfFragment : Fragment = PdfFragment()
                pdfFragment.arguments  = bundle

                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, pdfFragment)
                    .addToBackStack("back")
                    .commit()

            }
        }


    }

    private fun getPrescription(patientId: Int) {

        ApiManager.getApis().getPrescriptions(patientId)
            .enqueue(object : Callback<ArrayList<GetAllPrescriptionsOfPatientResponseItem>> {
                override fun onFailure(
                    call: Call<ArrayList<GetAllPrescriptionsOfPatientResponseItem>>,
                    t: Throwable
                ) {
                    Toast.makeText(
                        requireContext(),
                        "Throwable" + t.localizedMessage,
                        Toast.LENGTH_LONG
                    ).show()

                }

                override fun onResponse(
                    call: Call<ArrayList<GetAllPrescriptionsOfPatientResponseItem>>,
                    response: Response<ArrayList<GetAllPrescriptionsOfPatientResponseItem>>
                ) {
                    prescriptionsList = response.body()!!.toList()
                    adapter.changeData(prescriptionsList)
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
                }

            })
    }

}