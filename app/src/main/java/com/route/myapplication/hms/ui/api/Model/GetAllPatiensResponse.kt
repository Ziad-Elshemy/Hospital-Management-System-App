package com.route.myapplication.hms.ui.api.Model

import com.google.gson.annotations.SerializedName

data class GetAllPatiensResponse(

	@field:SerializedName("GetAllPatiensResponse")
	val getAllPatiensResponse: List<GetAllPatiensResponseItem?>? = null
)

data class GetAllPatiensResponseItem(

	@field:SerializedName("departmentName")
	val departmentName: String? = null,

	@field:SerializedName("firstName")
	val firstName: String? = null,

	@field:SerializedName("lastName")
	val lastName: String? = null,

	@field:SerializedName("phoneNumber")
	val phoneNumber: String? = null,

	@field:SerializedName("nationalId")
	val nationalId: String? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("mail")
	val mail: String? = null,

	@field:SerializedName("createdDtm")
	val createdDtm: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("bloodType")
	val bloodType: String? = null,

	@field:SerializedName("userName")
	val userName: String? = null,

	@field:SerializedName("isActive")
	val isActive: Boolean? = null
)
