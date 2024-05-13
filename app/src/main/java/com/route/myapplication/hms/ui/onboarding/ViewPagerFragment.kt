package com.route.myapplication.hms.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.route.myapplication.hms.R
import com.route.myapplication.hms.ui.onboarding.Screens.firstScreenFragment
import com.route.myapplication.hms.ui.onboarding.Screens.secondScreenFragment
import com.route.myapplication.hms.ui.onboarding.Screens.thirdScreenFragment

class ViewPagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_view_pager, container, false)

        val fragmentList = arrayListOf<Fragment>(firstScreenFragment(),secondScreenFragment(),thirdScreenFragment())
        val adapter = ViewPagerAdapter(fragmentList,requireActivity().supportFragmentManager,lifecycle)

        view.findViewById<ViewPager2>(R.id.viewPager).adapter = adapter

        return view
    }

}