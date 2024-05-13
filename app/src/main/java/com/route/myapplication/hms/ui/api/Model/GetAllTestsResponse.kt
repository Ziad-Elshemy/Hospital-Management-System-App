package com.route.myapplication.hms.ui.api.Model

import com.google.gson.annotations.SerializedName

data class GetAllTestsResponse(

	@field:SerializedName("GetAllTestsResponse")
	val getAllTestsResponse: List<GetAllTestsResponseItem?>? = null
)

data class NumericalParamtersItem(

	@field:SerializedName("unit")
	val unit: String? = null,

	@field:SerializedName("inputPattern")
	val inputPattern: String? = null,

	@field:SerializedName("testParameterName")
	val testParameterName: String? = null,

	@field:SerializedName("min_Range")
	val minRange: Double? = null,

	@field:SerializedName("testId")
	val testId: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("max_Range")
	val maxRange: Double? = null,

	@field:SerializedName("fieldType")
	val fieldType: String? = null
)

data class GetAllTestsResponseItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("categoricalParamters")
	val categoricalParamters: List<Any?>? = null,

	@field:SerializedName("numericalParamters")
	val numericalParamters: List<NumericalParamtersItem?>? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("testCharge")
	val testCharge: Int? = null
)
