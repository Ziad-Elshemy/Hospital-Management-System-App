package com.route.myapplication.hms.ui.api.Model

import com.google.gson.annotations.SerializedName

data class GetAllEmpsResponse(

	@field:SerializedName("GetAllEmpsResponse")
	val getAllEmpsResponse: List<GetAllEmpsResponseItem?>? = null
)

data class NurseDtosItem(

	@field:SerializedName("firstName")
	val firstName: String? = null,

	@field:SerializedName("lastName")
	val lastName: String? = null,

	@field:SerializedName("phoneNumber")
	val phoneNumber: String? = null,

	@field:SerializedName("createdDtm")
	val createdDtm: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("isActive")
	val isActive: Boolean? = null
)

data class GetAllEmpsResponseItem(

	@field:SerializedName("departmentName")
	val departmentName: String? = null,

	@field:SerializedName("nurseDtos")
	val nurseDtos: List<NurseDtosItem?>? = null,

	@field:SerializedName("departmentId")
	val departmentId: Int? = null,

	@field:SerializedName("departmentLocation")
	val departmentLocation: Any? = null,

	@field:SerializedName("doctorDtos")
	val doctorDtos: List<DoctorDtosItem?>? = null,

	@field:SerializedName("isActive")
	val isActive: Boolean? = null
)

data class DoctorDtosItem(

	@field:SerializedName("firstName")
	val firstName: String? = null,

	@field:SerializedName("lastName")
	val lastName: String? = null,

	@field:SerializedName("phoneNumber")
	val phoneNumber: String? = null,

	@field:SerializedName("createdDtm")
	val createdDtm: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("isActive")
	val isActive: Boolean? = null
)
