package com.route.myapplication.hms.ui.api.Model

import retrofit2.http.Body

data class TestRequest (var LabName:String,
                        var createdDtm:String,
                        var patientId:Int,
                        var doctorId:Int,
                        var indoorPatientRecordId:Int?)
