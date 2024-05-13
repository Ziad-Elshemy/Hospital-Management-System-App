package com.route.myapplication.hms.ui.api.Model

import com.google.gson.annotations.SerializedName

data class GetVitalsResponse(

	@field:SerializedName("getVitalsResponse")
	val getVitalsResponse: List<getVitalsResponseItem?>? = null
)

data class Note(

	@field:SerializedName("createdDate")
	val createdDate: String? = null,

	@field:SerializedName("subject")
	val subject: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("body")
	val body: String? = null
)

data class getVitalsResponseItem(

	@field:SerializedName("vitalsignId")
	val vitalsignId: Int? = null,

	@field:SerializedName("vitals_date")
	val vitalsDate: String? = null,

	@field:SerializedName("nurseName")
	val nurseName: String? = null,

	@field:SerializedName("patientName")
	val patientName: String? = null,

	@field:SerializedName("note")
	val note: Note? = null,

	@field:SerializedName("respirationRate")
	val respirationRate: Int? = null,

	@field:SerializedName("pulseRate")
	val pulseRate: Int? = null,

	@field:SerializedName("temperature")
	val temperature: Int? = null,

	@field:SerializedName("ecg")
	val ecg: Any? = null,

	@field:SerializedName("pressure")
	val pressure: String? = null
)
