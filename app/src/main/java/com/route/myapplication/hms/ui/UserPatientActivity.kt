package com.route.myapplication.hms.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.navigation.NavigationView
import com.route.myapplication.hms.R
import com.route.myapplication.hms.ui.PatientUserFragments.*

class UserPatientActivity : AppCompatActivity() {


    lateinit var toggle : ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    lateinit var appBarLayout: AppBarLayout
    lateinit var drawerIcon : ImageView
    lateinit var homeIcon : ImageView
    lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_patient)

        drawerLayout = findViewById(R.id.drawerLayout_patient)
        drawerIcon = findViewById(R.id.drawer_icon_patient)
        homeIcon = findViewById(R.id.home_icon_patient)
        navigationView = findViewById(R.id.nav_view_patient)


        drawerIcon.setOnClickListener {
            drawerLayout.open()
        }

        homeIcon.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        val navView: NavigationView = findViewById(R.id.nav_view_patient)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            if (menuItem.itemId == R.id.nav_profile) {
                pushFragment(PatientUserDashboardFragment(), menuItem.title.toString())

            } else if (menuItem.itemId == R.id.nav_show_appointment_patient) {
                pushFragment(PatientUserShowAppointmentsFragment(), menuItem.title.toString())

            } else if (menuItem.itemId == R.id.nav_make_appointment_patient) {
                pushFragment(MakeAppointmentFragment(), menuItem.title.toString())

            } else if (menuItem.itemId == R.id.nav_show_lab_patient) {
                pushFragment(PatientUserShowTestsFragment(), menuItem.title.toString())

            } else if (menuItem.itemId == R.id.nav_show_scan_patient) {
                pushFragment(PatientUserShowSansFragment(), menuItem.title.toString())

            } else if (menuItem.itemId == R.id.nav_show_prescription_patient) {
                pushFragment(PatientUserShowPrescriptionsFragment(), menuItem.title.toString())

            } else if (menuItem.itemId == R.id.nav_show_record_patient) {
                pushFragment(PatientUserShowRecordsFragment(), menuItem.title.toString())

            } else if (menuItem.itemId == R.id.nav_logout_patient) {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()

            }

            return@setNavigationItemSelectedListener true
        }
        navView.setCheckedItem(R.id.nav_profile)
        pushFragment(PatientUserDashboardFragment(),"Profile")
    }

        private fun pushFragment(fragment: Fragment, title:String) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack("")
                .commit()
            setTitle(title)
            drawerLayout.close()

        }
}