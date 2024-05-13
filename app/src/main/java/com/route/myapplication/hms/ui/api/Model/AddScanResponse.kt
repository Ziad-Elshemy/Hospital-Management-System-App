package com.route.myapplication.hms.ui.api.Model

import com.google.gson.annotations.SerializedName

data class AddScanResponse(

	@field:SerializedName("indoorPatientRecord")
	val indoorPatientRecord: Any? = null,

	@field:SerializedName("indoorPatientRecordId")
	val indoorPatientRecordId: Any? = null,

	@field:SerializedName("patientId")
	val patientId: Int? = null,

	@field:SerializedName("doctorId")
	val doctorId: Int? = null,

	@field:SerializedName("scanId")
	val scanId: Int? = null,

	@field:SerializedName("createdDtm")
	val createdDtm: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)