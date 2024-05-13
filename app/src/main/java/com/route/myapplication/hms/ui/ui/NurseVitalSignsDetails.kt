package com.route.myapplication.hms.ui.ui

import java.util.*

data class NurseVitalSignsDetails(
    val Date:String?,
//    val Time : String?,
    val Ecg:Int?,
    val BloodPressure : String?,
    val PulseRate :Int?,
    val Temperature :Int?,
    val RespiratoryRate : Int?,
    val Signature : String?,
    val Comments: String?
)
