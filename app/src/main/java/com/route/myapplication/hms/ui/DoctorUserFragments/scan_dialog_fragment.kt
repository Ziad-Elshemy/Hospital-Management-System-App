package com.route.myapplication.hms.ui.DoctorUserFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.route.myapplication.hms.R

class scan_dialog_fragment :DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView : View = inflater.inflate(R.layout.doctor_scan_dialog,container,false)


        rootView.findViewById<Button>(R.id.addbtn).setOnClickListener {

            val bundle = requireArguments()
            val patientId: Int = bundle.getInt("patientId")


            val bundle_ = Bundle()
            bundle_.putInt("patientId",patientId)

            val makeScanFragment : Fragment = MakeScanFragment()
            makeScanFragment.arguments  = bundle_

            pushFragment(makeScanFragment)
            dismiss()


        }

        rootView.findViewById<Button>(R.id.viewbtn).setOnClickListener {

            val bundle = requireArguments()
            val patientId: Int = bundle.getInt("patientId")


            val bundle_ = Bundle()
            bundle_.putInt("patientId",patientId)

            val getScanFragment : Fragment = GetScanFragment()
            getScanFragment.arguments  = bundle_

            pushFragment(getScanFragment)
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