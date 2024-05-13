package com.route.myapplication.hms.ui.api.Model

import com.google.gson.annotations.SerializedName

data class GetAllDeptsResponse(

	@field:SerializedName("GetAllDeptsResponse")
	val getAllDeptsResponse: List<GetAllDeptsResponseItem?>? = null
)

data class GetAllDeptsResponseItem(

	@field:SerializedName("departmentName")
	val departmentName: String? = null,

	@field:SerializedName("departmentId")
	val departmentId: Int? = null,

	@field:SerializedName("departmentLocation")
	val departmentLocation: String? = null,

	@field:SerializedName("isActive")
	val isActive: Boolean? = null
)
