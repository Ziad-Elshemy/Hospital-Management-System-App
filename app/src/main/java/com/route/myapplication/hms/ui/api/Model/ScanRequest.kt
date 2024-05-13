package com.route.myapplication.hms.ui.api.Model

import retrofit2.http.Body

data class ScanRequest (var scanName:String,
                        var createdDtm:String,
                        var patientId:Int,
                        var doctorId:Int,
                        var indoorPatientRecordId:Int?)
