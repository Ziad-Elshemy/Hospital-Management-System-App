package com.route.myapplication.hms.ui.api.Model

import com.google.gson.annotations.SerializedName

data class GetNurseByIdResponse(

	@field:SerializedName("departmentName")
	val departmentName: String? = null,

	@field:SerializedName("lastName")
	val lastName: String? = null,

	@field:SerializedName("image")
	val image: Any? = null,

	@field:SerializedName("imageName")
	val imageName: Any? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("role")
	val role: Any? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("mail")
	val mail: String? = null,

	@field:SerializedName("departmentId")
	val departmentId: Int? = null,

	@field:SerializedName("bloodType")
	val bloodType: String? = null,

	@field:SerializedName("userName")
	val userName: String? = null,

	@field:SerializedName("isActive")
	val isActive: Boolean? = null,

	@field:SerializedName("nurseSpecialization")
	val nurseSpecialization: String? = null,

	@field:SerializedName("firstName")
	val firstName: String? = null,

	@field:SerializedName("password")
	val password: Any? = null,

	@field:SerializedName("phoneNumber")
	val phoneNumber: String? = null,

	@field:SerializedName("nationalId")
	val nationalId: String? = null,

	@field:SerializedName("createdDtm")
	val createdDtm: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("nurseDegree")
	val nurseDegree: String? = null,

	@field:SerializedName("age")
	val age: Int? = null
)
