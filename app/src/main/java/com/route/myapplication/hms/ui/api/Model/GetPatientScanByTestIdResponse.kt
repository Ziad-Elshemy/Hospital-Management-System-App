package com.route.myapplication.hms.ui.api.Model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GetPatientScanByScanIdResponse(

	@field:SerializedName("scanName")
	val scanName: String? = null,

	@field:SerializedName("patientName")
	val patientName: String? = null,

	@field:SerializedName("doctorName")
	val doctorName: String? = null,

	@field:SerializedName("patientScanId")
	val patientScanId: Int? = null,

	@field:SerializedName("writtenReport")
	val writtenReport: String? = null,

	@field:SerializedName("indoorPatientRecordId")
	val indoorPatientRecordId: Int? = null,

	@field:SerializedName("patientId")
	val patientId: Int? = null,

	@field:SerializedName("doctorId")
	val doctorId: Int? = null,

	@field:SerializedName("scanImages")
	val scanImages: List<Any?>? = null,

	@field:SerializedName("scanDate")
	val scanDate: String? = null
) : Serializable
