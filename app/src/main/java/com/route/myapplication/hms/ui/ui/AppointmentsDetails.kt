package com.route.myapplication.hms.ui.ui

import java.util.*

data class AppointmentsDetails(val patientID: Int,
                               var name : String,
                               var gender : String,
                               var age: Int,
                               var time_convension :String,
                               var time : String)
