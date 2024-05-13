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

class lab_dialog_appoinments_fragment :DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView : View = inflater.inflate(R.layout.doctor_lab_dialog,container,false)


        rootView.findViewById<Button>(R.id.addbtn).setOnClickListener {

            val bundle = requireArguments()
            val patientId: Int = bundle.getInt("patientId")


            val bundle_ = Bundle()
            bundle_.putInt("patientId",patientId)

            val makeLabFragment : Fragment = MakeLabAppoinmentsFragment()
            makeLabFragment.arguments  = bundle_

            pushFragment(makeLabFragment)
            dismiss()


        }

        rootView.findViewById<Button>(R.id.viewbtn).setOnClickListener {
            val bundle = requireArguments()
            val patientId: Int = bundle.getInt("patientId")


            val bundle_ = Bundle()
            bundle_.putInt("patientId",patientId)

            val viewLabFragment : Fragment = GetLabAppointmentFragment()
            viewLabFragment.arguments  = bundle_

            pushFragment(viewLabFragment)
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
            .addToBackStack("")
            .commit()
    }

}