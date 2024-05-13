package com.route.myapplication.hms.ui.api.Model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GetPatientsScansByPatientIdResponse(

	@field:SerializedName("GetPatientsScansByPatientIdResponse")
	val getPatientsScansByPatientIdResponse: List<GetPatientsScansByPatientIdResponseItem?>? = null
)

data class GetPatientsScansByPatientIdResponseItem(

	@field:SerializedName("scanName")
	val scanName: String? = null,

	@field:SerializedName("patientName")
	val patientName: String? = null,

	@field:SerializedName("image")
	val image: Any? = null,

	@field:SerializedName("doctorName")
	val doctorName: String? = null,

	@field:SerializedName("patientScanId")
	val patientScanId: Int? = null,

	@field:SerializedName("writtenReport")
	val writtenReport: String? = null,

	@field:SerializedName("indoorPatientRecordId")
	val indoorPatientRecordId: Any? = null,

	@field:SerializedName("patientId")
	val patientId: Int? = null,

	@field:SerializedName("doctorId")
	val doctorId: Int? = null,

	@field:SerializedName("scanDate")
	val scanDate: String? = null
) : Serializable
