package com.route.myapplication.hms.ui.DoctorUserFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.route.myapplication.hms.R
import com.route.myapplication.hms.ui.api.ApiManager
import com.route.myapplication.hms.ui.api.Model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PrescriptionRecordIndoorPatientFragment : Fragment() {

    var patient : GetPatientByIdResponse = GetPatientByIdResponse()
    lateinit var patientName: TextView

   // lateinit var prescription : Prescription_
    var PrescriptionsList: MutableList<Prescription_> = arrayListOf()


    var patientPrescription = GetPrescriptionByPrescriptionIdResponse()

    var patientPrescriptionsList: MutableList<GetPrescriptionByPrescriptionIdResponse> = arrayListOf()

    var patientPrescriptionrecordsList: List<PrescriptionsItem?> = arrayListOf()

    lateinit var recyclerView: RecyclerView
    val adapter = PrescriptionIndoorRecordAdapter(null)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(
            R.layout.fragment_doctor_show_patient_prescriptions,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        patientName = requireView().findViewById(R.id.name_input)
        recyclerView = requireView().findViewById(R.id.prescriptions_recycler)
        recyclerView.adapter = adapter

        val bundle = requireArguments()
        val patientRecord: GetIndoorPatientRecordByPatientIdResponseItem =
            bundle!!.getSerializable("patientRecord") as GetIndoorPatientRecordByPatientIdResponseItem

        patientPrescriptionrecordsList = patientRecord.prescriptions?.toList() ?: listOf()

        patientPrescriptionsList.clear()

        patientPrescriptionrecordsList.forEach {
            getPrescriptionById(it?.prescriptionId!!)
            getpatientById(it.patientId!!)
        }

        adapter.onItemClickListener = object : PrescriptionIndoorRecordAdapter.OnItemClickListener {
            override fun onItemClick(pos: Int, item: Prescription_) {

                val bundle = Bundle()
                bundle.putSerializable("item", item)

                val prescriptionRecordFragment: Fragment = PdfPrescriptionRecordFragment()
                prescriptionRecordFragment.arguments = bundle

                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, prescriptionRecordFragment)
                    .addToBackStack("back")
                    .commit()

            }

        }

    }


    private fun getPrescriptionById(prescriptionId: Int) {

        ApiManager.getApis().getPrescriptionByPrescriptionId(prescriptionId)
            .enqueue(object : Callback<GetPrescriptionByPrescriptionIdResponse> {
                override fun onFailure(
                    call: Call<GetPrescriptionByPrescriptionIdResponse>,
                    t: Throwable
                ) {
                    Toast.makeText(
                        requireContext(),
                        "Throwable" + t.localizedMessage,
                        Toast.LENGTH_LONG
                    ).show()

                }

                override fun onResponse(
                    call: Call<GetPrescriptionByPrescriptionIdResponse>,
                    response: Response<GetPrescriptionByPrescriptionIdResponse>
                ) {
                    PrescriptionsList.clear()
                    patientPrescription = response.body()!!
                    PrescriptionsList.add(patientPrescription.prescription!!)
                    //patientPrescriptionsList.add(patientPrescription)
                   // adapter.changeData(patientPrescriptionsList)

                    adapter.changeData(PrescriptionsList)

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