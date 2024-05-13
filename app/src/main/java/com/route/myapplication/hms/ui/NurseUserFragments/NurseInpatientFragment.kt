package com.route.myapplication.hms.ui.NurseUserFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.route.myapplication.hms.R
import com.route.myapplication.hms.ui.DoctorUserFragments.*
import com.route.myapplication.hms.ui.api.ApiManager
import com.route.myapplication.hms.ui.api.Model.GetDoctorByIdResponse
import com.route.myapplication.hms.ui.api.Model.GetIndoorPatientsResponseItem
import com.route.myapplication.hms.ui.api.Model.GetNurseByIdResponse
import com.route.myapplication.hms.ui.ui.DoctorInpatientAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NurseInpatientFragment : Fragment() {

    var inPatientsList: List<GetIndoorPatientsResponseItem> = arrayListOf()

    lateinit var recyclerView: RecyclerView
    lateinit var adapter : NurseInpatientAdapter

    var nurse : GetNurseByIdResponse = GetNurseByIdResponse()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nurse_inpatient, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView =requireView().findViewById(R.id.Nurseinpatient_recyclerView)
        adapter = NurseInpatientAdapter(inPatientsList)
        recyclerView.adapter = adapter

//        val bundle = requireArguments()
//        val nurseId: Int = bundle.getInt("nurseId")
//
//        getNurseById(nurseId)


        getInpatients(1)

        adapter.onVitalSignsImgClickListener = object : NurseInpatientAdapter.OnImageClickListener{
            override fun onImageClick(pos: Int, item: GetIndoorPatientsResponseItem) {
                val bundle = Bundle()
                bundle.putInt("patientId", item.id!!)

                val showVitalsFragment : Fragment = NurseUserShowVitalSignsFragment()
                showVitalsFragment.arguments  = bundle
                pushFragment(showVitalsFragment)
            }
        }

        adapter.onaddVitalsImgClickListener = object : NurseInpatientAdapter.OnImageClickListener{
            override fun onImageClick(pos: Int, item: GetIndoorPatientsResponseItem) {

                val bundle = Bundle()
                bundle.putSerializable("Indoorpatient", item)

                val addVitalsFragment : Fragment = NurseUserAddVitalSignFragment()
                addVitalsFragment.arguments  = bundle
                pushFragment(addVitalsFragment)

            }
        }

        adapter.onPrescriptionImgClickListener = object : NurseInpatientAdapter.OnImageClickListener{
            override fun onImageClick(pos: Int, item: GetIndoorPatientsResponseItem) {
                 pushFragment(NurseUserShowMedicineFragment())

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

        ApiManager.getApis().getInPatients(DeptId).enqueue(object :
            Callback<ArrayList<GetIndoorPatientsResponseItem>> {

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

    private fun getNurseById(nurseID: Int) {

        ApiManager.getApis().getNurseByIdResponse(nurseID).enqueue(object : Callback<GetNurseByIdResponse> {
            override fun onFailure(
                call: Call<GetNurseByIdResponse>,
                t: Throwable
            ) {
                Toast.makeText(
                    requireContext(),
                    "Throwable" + t.localizedMessage,
                    Toast.LENGTH_LONG
                ).show()

            }

            override fun onResponse(
                call: Call<GetNurseByIdResponse>,
                response: Response<GetNurseByIdResponse>
            ) {
                nurse = response.body()!!
                getInpatients(nurse.departmentId!!)
            }

        })
    }


}