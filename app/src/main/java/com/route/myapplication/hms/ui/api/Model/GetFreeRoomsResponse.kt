package com.route.myapplication.hms.ui.api.Model

import com.google.gson.annotations.SerializedName

data class GetFreeRoomsResponse(

	@field:SerializedName("GetFreeRoomsResponse")
	val getFreeRoomsResponse: List<GetFreeRoomsResponseItem?>? = null
)

data class GetFreeRoomsResponseItem(

	@field:SerializedName("roomNumber")
	val roomNumber: Int? = null,

	@field:SerializedName("roomCharges")
	val roomCharges: Int? = null,

	@field:SerializedName("departmentId")
	val departmentId: Int? = null,

	@field:SerializedName("floorNumber")
	val floorNumber: Int? = null,

	@field:SerializedName("numberOfBeds")
	val numberOfBeds: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("roomType")
	val roomType: String? = null
)
