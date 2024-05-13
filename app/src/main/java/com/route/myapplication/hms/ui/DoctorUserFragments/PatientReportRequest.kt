package com.route.myapplication.hms.ui.DoctorUserFragments

import java.io.Serializable

data class PatientReportRequest ( var patientId:Int,
    var dischargeDate:String
    ) : Serializable