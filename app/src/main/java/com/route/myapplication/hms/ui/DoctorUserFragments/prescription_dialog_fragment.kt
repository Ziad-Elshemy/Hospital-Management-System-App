package com.route.myapplication.hms.ui.DoctorUserFragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.route.myapplication.hms.R
import com.route.myapplication.hms.ui.api.Model.GetAllPrescriptionsOfPatientResponseItem

class prescription_dialog_fragment :DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView : View = inflater.inflate(R.layout.doctor_prescription_dialog,container,false)


        rootView.findViewById<Button>(R.id.addbtn).setOnClickListener {
//            val mArgs = arguments
//            val patientID:Int = mArgs!!.getInt("patientId")
//
//            val bundle = Bundle()
//            bundle.putInt("Id", patientID) // Put anything what you want
//
//            DoctorAddPrescriptionFragment().arguments = bundle


            val bundle = requireArguments()
            val patientId: Int = bundle.getInt("patientId")


            val bundle_ = Bundle()
            bundle_.putInt("patientId",patientId)

            val AddPrescriptionFragment : Fragment = DoctorAddPrescriptionFragment()
            AddPrescriptionFragment.arguments  = bundle_

            pushFragment(AddPrescriptionFragment)
            dismiss()


        }

        rootView.findViewById<Button>(R.id.viewbtn).setOnClickListener {

            val bundle = requireArguments()
            val patientId: Int = bundle.getInt("patientId")

            Log.e("P_ID",patientId.toString())

            val bundle_ = Bundle()
            bundle_.putInt("patientId",patientId)

            val ViewPrescriptionFragment : Fragment = DoctorShowPatientPrescriptionsFragment()
            ViewPrescriptionFragment.arguments  = bundle_

            pushFragment(ViewPrescriptionFragment)
            dismiss()


        }
        rootView.findViewById<Button>(R.id.cancelbtn).setOnClickListener {
            dismiss()

        }
        return rootView
    }


    private fun pushFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack("back")
            .commit()
    }

}