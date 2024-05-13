package com.route.myapplication.hms.ui.api.Model

import com.google.gson.annotations.SerializedName

data class GetIndoorPatientsByDepartmentIdResponse(

	@field:SerializedName("GetIndoorPatientsByDepartmentIdResponse")
	val getIndoorPatientsByDepartmentIdResponse: List<GetIndoorPatientsByDepartmentIdResponseItem?>? = null
)

data class GetIndoorPatientsByDepartmentIdResponseItem(

	@field:SerializedName("lastName")
	val lastName: String? = null,

	@field:SerializedName("image")
	val image: Any? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("room_Number")
	val roomNumber: Int? = null,

	@field:SerializedName("floor_Number")
	val floorNumber: Int? = null,

	@field:SerializedName("causeOfAdmission")
	val causeOfAdmission: String? = null,

	@field:SerializedName("oralMedicalHistory")
	val oralMedicalHistory: String? = null,

	@field:SerializedName("firstName")
	val firstName: String? = null,

	@field:SerializedName("enterDate")
	val enterDate: String? = null,

	@field:SerializedName("phoneNumber")
	val phoneNumber: String? = null,

	@field:SerializedName("indoorPatientId")
	val indoorPatientId: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("bedNumber")
	val bedNumber: Int? = null,

	@field:SerializedName("age")
	val age: Int? = null
)
