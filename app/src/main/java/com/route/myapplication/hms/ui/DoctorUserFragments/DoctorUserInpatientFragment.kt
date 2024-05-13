package com.route.myapplication.hms.ui.DoctorUserFragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.route.myapplication.hms.R
import com.route.myapplication.hms.ui.NurseUserFragments.NurseUserShowVitalSignsFragment
import com.route.myapplication.hms.ui.api.ApiManager
import com.route.myapplication.hms.ui.api.Model.GetDoctorByIdResponse
import com.route.myapplication.hms.ui.api.Model.GetIndoorPatientRecordByPatientIdResponseItem
import com.route.myapplication.hms.ui.api.Model.GetIndoorPatientsResponseItem
import com.route.myapplication.hms.ui.api.Model.GetPatientsScansByPatientIdResponseItem
import com.route.myapplication.hms.ui.ui.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DoctorUserInpatientFragment : Fragment() {
    var inPatientsList: List<GetIndoorPatientsResponseItem> = arrayListOf()

    lateinit var recyclerView: RecyclerView
    lateinit var adapter : DoctorInpatientAdapter

    var doctor : GetDoctorByIdResponse = GetDoctorByIdResponse()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctor_user_inpatient, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView =requireView().findViewById(R.id.Doctorinpatient_recyclerView)
        adapter = DoctorInpatientAdapter(inPatientsList)
        recyclerView.adapter = adapter

        val bundle = requireArguments()
        val doctorId: Int = bundle.getInt("doctorId")

        getDoctorById(doctorId)


//        getInpatients(1)

        adapter.onDischargeImgClickListener = object : DoctorInpatientAdapter.OnImageClickListener{
            override fun onImageClick(pos: Int, item: GetIndoorPatientsResponseItem) {
                val bundle = Bundle()
                bundle.putInt("patientId", item.id!!)

                val dischargeFragment : Fragment = DoctorUserDischargePatientFragment()
                dischargeFragment.arguments  = bundle
                pushFragment(dischargeFragment)
            }

        }


        adapter.onVitalSignsImgClickListener = object : DoctorInpatientAdapter.OnImageClickListener{
            override fun onImageClick(pos: Int, item: GetIndoorPatientsResponseItem) {

                val bundle = Bundle()
                bundle.putInt("patientId", item.id!!)

                val showVitalsFragment : Fragment = NurseUserShowVitalSignsFragment()
                showVitalsFragment.arguments  = bundle

                pushFragment(showVitalsFragment)
            }
        }

        adapter.onLabImgClickListener = object : DoctorInpatientAdapter.OnImageClickListener{
            override fun onImageClick(pos: Int, item: GetIndoorPatientsResponseItem) {

                val bundle1 = Bundle()
                bundle1.putInt("patientId", item.id!!)

                val dialog : DialogFragment = lab_dialog_fragment()
                dialog.arguments  = bundle1

                dialog.show(parentFragmentManager,"custom dialog")

            }
        }

        adapter.onScanImgClickListener = object : DoctorInpatientAdapter.OnImageClickListener{
            override fun onImageClick(pos: Int, item: GetIndoorPatientsResponseItem) {
                val bundle2 = Bundle()
                bundle2.putInt("patientId", item.id!!)

                val dialog : DialogFragment = scan_dialog_fragment()
                dialog.arguments  = bundle2

                dialog.show(parentFragmentManager,"custom dialog")

            }
        }

        adapter.onReportImgClickListener = object : DoctorInpatientAdapter.OnImageClickListener{
            override fun onImageClick(pos: Int, item: GetIndoorPatientsResponseItem) {
                val bundle = Bundle()
                bundle.putInt("patientId", item.id!!)

                val recordFragment : Fragment = DoctorUserPatientIndoorRecordFragment()
                recordFragment.arguments  = bundle
                pushFragment(recordFragment)
            }
        }

        adapter.onPrescriptionImgClickListener = object : DoctorInpatientAdapter.OnImageClickListener{
            override fun onImageClick(pos: Int, item: GetIndoorPatientsResponseItem) {

                val bundle = Bundle()
                bundle.putInt("patientId", item.id!!)

                val dialog : DialogFragment = prescription_dialog_fragment()
                dialog.arguments  = bundle

                dialog.show(parentFragmentManager,"custom dialog")

            }
        }

    }


    private fun pushFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack("back")
            .commit()
    }


    private fun getInpatients(DeptId: Int) {

        ApiManager.getApis().getInPatients(DeptId).enqueue(object : Callback<ArrayList<GetIndoorPatientsResponseItem>>{

                override fun onFailure(
                    call: Call<ArrayList<GetIndoorPatientsResponseItem>>,
                    t: Throwable
                ) {
                    Toast.makeText(
                        requireContext(),
                        "Throwable" + t.localizedMessage,
                        Toast.LENGTH_LONG
                    ).show()

                }

                override fun onResponse(
                    call: Call<ArrayList<GetIndoorPatientsResponseItem>>,
                    response: Response<ArrayList<GetIndoorPatientsResponseItem>>
                ) {
                    inPatientsList = response.body()!!.toList()
                    adapter.changeData(inPatientsList)

                }
            })
    }


    private fun getDoctorById(doctorID: Int) {

        ApiManager.getApis().getDoctorById(doctorID).enqueue(object : Callback<GetDoctorByIdResponse> {
            override fun onFailure(
                call: Call<GetDoctorByIdResponse>,
                t: Throwable
            ) {
                Toast.makeText(
                    requireContext(),
                    "Throwable" + t.localizedMessage,
                    Toast.LENGTH_LONG
                ).show()

            }

            override fun onResponse(
                call: Call<GetDoctorByIdResponse>,
                response: Response<GetDoctorByIdResponse>
            ) {
                doctor = response.body()!!
                getInpatients(doctor.departmentId!!)
            }

        })
    }






}