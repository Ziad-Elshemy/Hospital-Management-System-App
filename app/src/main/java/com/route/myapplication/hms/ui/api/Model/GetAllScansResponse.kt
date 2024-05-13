package com.route.myapplication.hms.ui.api.Model

import com.google.gson.annotations.SerializedName

data class GetAllScansResponse(

	@field:SerializedName("GetAllScansResponse")
	val getAllScansResponse: List<GetAllScansResponseItem?>? = null
)

data class GetAllScansResponseItem(

	@field:SerializedName("scanName")
	val scanName: String? = null,

	@field:SerializedName("scanCharge")
	val scanCharge: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
