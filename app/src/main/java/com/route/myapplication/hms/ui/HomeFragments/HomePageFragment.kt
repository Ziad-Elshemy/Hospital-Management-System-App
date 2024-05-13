package com.route.myapplication.hms.ui.HomeFragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import com.route.myapplication.hms.R
import com.route.myapplication.hms.ui.DoctorUserFragments.lab_dialog_fragment
import com.route.myapplication.hms.ui.EcgSignalActivity
import com.route.myapplication.hms.ui.HomeActivity

class HomePageFragment : Fragment() {

    lateinit var doctorLayout : LinearLayout
    lateinit var chatbot : LinearLayout
    lateinit var ecg : LinearLayout
    lateinit var depts : LinearLayout
    lateinit var brainTumor : LinearLayout
    lateinit var login : LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        doctorLayout = requireView().findViewById(R.id.doctorsLayout)
        chatbot = requireView().findViewById(R.id.chatbot)
        ecg = requireView().findViewById(R.id.ecg)
        depts = requireView().findViewById(R.id.specialities)
        brainTumor = requireView().findViewById(R.id.brainTumor)
        login = requireView().findViewById(R.id.login)



        doctorLayout.setOnClickListener {
            pushFragment(DoctorFragment())

        }

        chatbot.setOnClickListener {
            pushFragment(ChatBotFragment())
        }

        ecg.setOnClickListener {
            val intent = Intent(context, EcgSignalActivity::class.java)
            startActivity(intent)
        }

        depts.setOnClickListener {
            pushFragment(DepartmentsFragment())
        }
        brainTumor.setOnClickListener {

            val dialog : DialogFragment = brain_tumor_dialog_fragment()

            dialog.show(parentFragmentManager,"custom dialog")

         //   pushFragment(BrainTumorFragment())

//            val intent = Intent(this, HomeActivity::class.java)
//            startActivity(intent)
//            finish()
        }
        login.setOnClickListener {
            pushFragment(LoginFragment())
        }

    }

    private fun pushFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack("")
            .commit()
    }



}