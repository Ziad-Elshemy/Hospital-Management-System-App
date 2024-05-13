package com.route.myapplication.hms.ui.api.Model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GetIndoorPatientsResponse(

	@field:SerializedName("GetIndoorPatientsResponse")
	val getIndoorPatientsResponse: List<GetIndoorPatientsResponseItem?>? = null
)

data class GetIndoorPatientsResponseItem(

	@field:SerializedName("firstName")
	val firstName: String? = null,

	@field:SerializedName("lastName")
	val lastName: String? = null,

	@field:SerializedName("image")
	val image: Any? = null,

	@field:SerializedName("phoneNumber")
	val phoneNumber: String? = null,

	@field:SerializedName("indoorPatientId")
	val indoorPatientId: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("age")
	val age: Int? = null
) : Serializable
