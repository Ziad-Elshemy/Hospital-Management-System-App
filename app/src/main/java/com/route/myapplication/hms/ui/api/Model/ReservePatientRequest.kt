package com.route.myapplication.hms.ui.api.Model

data class ReservePatientRequest(
val causeOfAdmission:String,
val roomId:Int,
val bedId:Int,
val departmentId:Int,
val oralMedicalHistory : String,
val orderdByDoctorId:Int,
val patientId : Int,
val enterDate:String,
)
