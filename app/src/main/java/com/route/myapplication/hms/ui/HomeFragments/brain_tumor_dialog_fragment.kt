package com.route.myapplication.hms.ui.HomeFragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.route.myapplication.hms.R
import com.route.myapplication.hms.ui.HomeActivity
import com.route.myapplication.hms.ui.ui.BrainTumorClassification

class brain_tumor_dialog_fragment :DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView : View = inflater.inflate(R.layout.brain_tumor_dialog,container,false)


        rootView.findViewById<Button>(R.id.classificationbtn).setOnClickListener {


            activity?.let{
                val intent = Intent (it, BrainTumorClassification::class.java)
                it.startActivity(intent)
            }


        }

//        rootView.findViewById<Button>(R.id.segmentationbtn).setOnClickListener {
//            val bundle = requireArguments()
//            val patientId: Int = bundle.getInt("patientId")
//
//
//            val bundle_ = Bundle()
//            bundle_.putInt("patientId",patientId)
//
//            val viewLabFragment : Fragment = GetLabFragment()
//            viewLabFragment.arguments  = bundle_
//
//            pushFragment(viewLabFragment)
//            dismiss()
//
//
//        }

        return rootView
    }


    private fun pushFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack("back")
            .commit()
    }

}