package com.route.myapplication.hms.ui.api.Model

import com.google.gson.annotations.SerializedName

data class GetFreeTimeSlotsByDocIdResponse(

	@field:SerializedName("doctorName")
	val doctorName: String? = null,

	@field:SerializedName("doctorId")
	val doctorId: Int? = null,

	@field:SerializedName("workSchedules")
	val workSchedules: List<WorkSchedulesItem?>? = null
)

data class WorkSchedulesItem(

	@field:SerializedName("dayOfWork")
	val dayOfWork: Int? = null,

	@field:SerializedName("slots")
	val slots: List<SlotsItem?>? = null
)

data class SlotsItem(

	@field:SerializedName("slot_Id")
	val slotId: Int? = null,

	@field:SerializedName("slotTime")
	val slotTime: String? = null,

	@field:SerializedName("slotNumber")
	val slotNumber: Int? = null
)
