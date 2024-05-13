package com.route.myapplication.hms.ui.api.Model

import com.route.myapplication.hms.ui.NurseUserFragments.NoteDto

data class VitalsRequest(var pressure:String,
                         var pulseRate:Int,
                         var temperature:Int,
                         var ecg:String?,
                         var respirationRate:Int,
                         var vitalsDate:String,
                         var nurseId:Int,
                         var noteDto:NoteDto?,
                         var patientId:Int)
