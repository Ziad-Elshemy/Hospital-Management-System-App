package com.route.myapplication.hms.ui.api.Model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GetPatientTestsByPatientIdResponse(

	@field:SerializedName("GetPatientTestsByPatientIdResponse")
	val getPatientTestsByPatientIdResponse: List<GetPatientTestsByPatientIdResponseItem?>? = null
)

data class NumericalDetailsItem(

	@field:SerializedName("numericalValue")
	val numericalValue: Float? = null,

	@field:SerializedName("unit")
	val unit: String? = null,

	@field:SerializedName("testParameterId")
	val testParameterId: Int? = null,

	@field:SerializedName("testParameterName")
	val testParameterName: String? = null,

	@field:SerializedName("min_Range")
	val minRange: Float? = null,

	@field:SerializedName("max_Range")
	val maxRange: Float? = null
)

data class GetPatientTestsByPatientIdResponseItem(

	@field:SerializedName("patientTestId")
	val patientTestId: Int? = null,

	@field:SerializedName("patientName")
	val patientName: String? = null,

	@field:SerializedName("doctorName")
	val doctorName: String? = null,

	@field:SerializedName("indoorPatientRecordId")
	val indoorPatientRecordId: Any? = null,

	@field:SerializedName("patientId")
	val patientId: Int? = null,

	@field:SerializedName("doctorId")
	val doctorId: Int? = null,

	@field:SerializedName("numericalDetails")
	val numericalDetails: List<NumericalDetailsItem?>? = null,

	@field:SerializedName("categoricalDetails")
	val categoricalDetails: List<Any?>? = null,

	@field:SerializedName("testDate")
	val testDate: String? = null,

	@field:SerializedName("testName")
	val testName: String? = null
) : Serializable
