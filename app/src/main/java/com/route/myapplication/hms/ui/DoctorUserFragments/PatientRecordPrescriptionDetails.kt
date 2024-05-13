package com.route.myapplication.hms.ui.DoctorUserFragments

import com.route.myapplication.hms.ui.api.Model.GetDoctorByIdResponse
import com.route.myapplication.hms.ui.api.Model.GetPrescriptionByPrescriptionIdResponse

data class PatientRecordPrescriptionDetails(val prescription: GetPrescriptionByPrescriptionIdResponse,
val doctor : GetDoctorByIdResponse
)
