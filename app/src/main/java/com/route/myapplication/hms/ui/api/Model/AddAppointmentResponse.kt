package com.route.myapplication.hms.ui.api.Model

import com.google.gson.annotations.SerializedName

data class AddAppointmentResponse(

    @field:SerializedName("doctor")
    val doctor: Any? = null,

    @field:SerializedName("appointmentType")
    val appointmentType: String? = null,

    @field:SerializedName("patientId")
    val patientId: Int? = null,

    @field:SerializedName("doctorId")
    val doctorId: Int? = null,

    @field:SerializedName("patient")
    val patient: Any? = null,

    @field:SerializedName("examined")
    val examined: Boolean? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("complain")
    val complain: String? = null,

    @field:SerializedName("appointmentCharge")
    val appointmentCharge: Int? = null,

    @field:SerializedName("appointmentDate")
    val appointmentDate: String? = null
)