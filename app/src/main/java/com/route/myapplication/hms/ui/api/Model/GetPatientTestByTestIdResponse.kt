package com.route.myapplication.hms.ui.api.Model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GetPatientTestByTestIdResponse(

	@field:SerializedName("patientTestId")
	val patientTestId: Int? = null,

	@field:SerializedName("patientName")
	val patientName: String? = null,

	@field:SerializedName("doctorName")
	val doctorName: String? = null,

	@field:SerializedName("indoorPatientRecordId")
	val indoorPatientRecordId: Int? = null,

	@field:SerializedName("patientId")
	val patientId: Int? = null,

	@field:SerializedName("doctorId")
	val doctorId: Int? = null,

	@field:SerializedName("numericalDetails")
	val numericalDetails: List<NumericalDetailsItemTest?>? = null,

	@field:SerializedName("categoricalDetails")
	val categoricalDetails: List<Any?>? = null,

	@field:SerializedName("testDate")
	val testDate: String? = null,

	@field:SerializedName("testName")
	val testName: String? = null
) : Serializable

data class NumericalDetailsItemTest(

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
