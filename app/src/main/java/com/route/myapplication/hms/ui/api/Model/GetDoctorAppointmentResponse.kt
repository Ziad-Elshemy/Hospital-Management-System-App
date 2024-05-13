package com.route.myapplication.hms.ui.api.Model

import com.google.gson.annotations.SerializedName

data class GetDoctorAppointmentResponse(

	@field:SerializedName("GetDoctorAppointmentResponse")
	val getDoctorAppointmentResponse: List<GetDoctorAppointmentResponseItem?>? = null
)

data class GetDoctorAppointmentResponseItem(

	@field:SerializedName("patientName")
	val patientName: String? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("patientId")
	val patientId: Int? = null,

	@field:SerializedName("slotTime")
	val slotTime: String? = null,

	@field:SerializedName("examined")
	val examined: Boolean? = null,

	@field:SerializedName("complain")
	val complain: String? = null,

	@field:SerializedName("age")
	val age: Int? = null
)
