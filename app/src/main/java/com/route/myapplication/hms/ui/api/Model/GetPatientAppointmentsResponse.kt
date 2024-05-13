package com.route.myapplication.hms.ui.api.Model

import com.google.gson.annotations.SerializedName

data class GetPatientAppointmentsResponse(

	@field:SerializedName("GetPatientAppointmentsResponse")
	val getPatientAppointmentsResponse: List<GetPatientAppointmentsResponseItem?>? = null
)

data class GetPatientAppointmentsResponseItem(

	@field:SerializedName("patientName")
	val patientName: String? = null,

	@field:SerializedName("doctorName")
	val doctorName: String? = null,

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

	@field:SerializedName("appointmentDate")
	val appointmentDate: String? = null,

	@field:SerializedName("age")
	val age: Int? = null
)
