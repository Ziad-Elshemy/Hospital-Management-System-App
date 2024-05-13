package com.route.myapplication.hms.ui.api.Model

import com.google.gson.annotations.SerializedName

data class GetFreeBedsResponse(

	@field:SerializedName("GetFreeBedsResponse")
	val getFreeBedsResponse: List<GetFreeBedsResponseItem?>? = null
)

data class GetFreeBedsResponseItem(

	@field:SerializedName("number")
	val number: Int? = null,

	@field:SerializedName("reserved")
	val reserved: Boolean? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("room")
	val room: Any? = null,

	@field:SerializedName("roomId")
	val roomId: Int? = null
)
