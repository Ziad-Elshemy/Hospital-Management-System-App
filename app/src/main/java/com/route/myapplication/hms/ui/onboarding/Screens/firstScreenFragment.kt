package com.route.myapplication.hms.ui.onboarding.Screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.route.myapplication.hms.R

class firstScreenFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_first_screen, container, false)


        val viewPager =  activity?.findViewById<ViewPager2>(R.id.viewPager)

        view.findViewById<TextView>(R.id.next).setOnClickListener {
            viewPager?.currentItem = 1
        }
        return view
    }

}