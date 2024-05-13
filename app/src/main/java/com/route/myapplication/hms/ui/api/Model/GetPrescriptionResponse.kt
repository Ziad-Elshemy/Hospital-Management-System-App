package com.route.myapplication.hms.ui.api.Model

import com.google.gson.annotations.SerializedName

data class GetPrescriptionResponse(

	@field:SerializedName("GetPrescriptionResponse")
	val getPrescriptionResponse: List<GetPrescriptionResponseItem?>? = null
)


data class GetPrescriptionResponseItem(

	@field:SerializedName("doctor")
	val doctor: Any? = null,

	@field:SerializedName("prescriptionId")
	val prescriptionId: Int? = null,

	@field:SerializedName("patientId")
	val patientId: Int? = null,

	@field:SerializedName("doctorId")
	val doctorId: Int? = null,

	@field:SerializedName("patient")
	val patient: Any? = null,

	@field:SerializedName("prescriptionItems")
	val prescriptionItems: List<PrescriptionItemsItem?>? = null,

	@field:SerializedName("re_appointement_date")
	val reAppointementDate: String? = null,

	@field:SerializedName("prescription_Date")
	val prescriptionDate: String? = null
)

data class PrescriptionItemsItem(

	@field:SerializedName("instructions")
	val instructions: String? = null,

	@field:SerializedName("prescriptionItemId")
	val prescriptionItemId: Int? = null,

	@field:SerializedName("dose")
	val dose: String? = null,

	@field:SerializedName("medicine_concentration")
	val medicineConcentration: String? = null,

	@field:SerializedName("durarion")
	val durarion: String? = null,

	@field:SerializedName("medicineType")
	val medicineType: String? = null,

	@field:SerializedName("medicine_Name")
	val medicineName: String? = null
)
