package com.route.myapplication.hms.ui.api.Model

import com.google.gson.annotations.SerializedName

data class AddVitalsResponse(

	@field:SerializedName("vitals_date")
	val vitalsDate: String? = null,

	@field:SerializedName("note")
	val note: Note? = null,

	@field:SerializedName("patientid")
	val patientid: Int? = null,

	@field:SerializedName("ecg")
	val ecg: Any? = null,

	@field:SerializedName("nurseId")
	val nurseId: Int? = null,

	@field:SerializedName("noteId")
	val noteId: Int? = null,

	@field:SerializedName("pressure")
	val pressure: String? = null,

	@field:SerializedName("respirationRate")
	val respirationRate: Int? = null,

	@field:SerializedName("indoorPatientRecordId")
	val indoorPatientRecordId: Any? = null,

	@field:SerializedName("patient")
	val patient: Any? = null,

	@field:SerializedName("pulseRate")
	val pulseRate: Int? = null,

	@field:SerializedName("temperature")
	val temperature: Int? = null,

	@field:SerializedName("nurse")
	val nurse: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class VitalsNote(

	@field:SerializedName("doctor")
	val doctor: Any? = null,

	@field:SerializedName("createdDate")
	val createdDate: String? = null,

	@field:SerializedName("indoorPatientRecordId")
	val indoorPatientRecordId: Int? = null,

	@field:SerializedName("doctorId")
	val doctorId: Any? = null,

	@field:SerializedName("subject")
	val subject: String? = null,

	@field:SerializedName("nurse")
	val nurse: Any? = null,

	@field:SerializedName("nurseId")
	val nurseId: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("body")
	val body: String? = null
)
