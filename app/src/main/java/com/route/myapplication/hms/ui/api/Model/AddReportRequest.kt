package com.route.myapplication.hms.ui.api.Model

import retrofit2.http.Body

data class AddReportRequest (var patientId:Int?,
                             var dateOfDischarge:String?,
                             var recommendation:String?,
                             var indoorPatientRecordId:Int?,
                             var enterDate:String?)
