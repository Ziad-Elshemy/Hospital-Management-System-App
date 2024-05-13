package com.route.myapplication.hms.ui.api.Model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GetPrescriptionByPrescriptionIdResponse(

	@field:SerializedName("presciption")
	val presciption: Any? = null,

	@field:SerializedName("prescription")
	val prescription: Prescription_? = null,

	@field:SerializedName("doctorFullName")
	val doctorFullName: String? = null,

	@field:SerializedName("department")
	val department: String? = null
) : Serializable

data class PrescriptionItemsItem_(

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

data class Prescription_(

	@field:SerializedName("doctor")
	val doctor: Any? = null,

	@field:SerializedName("prescriptionId")
	val prescriptionId: Int? = null,

	@field:SerializedName("indoorPatientRecordId")
	val indoorPatientRecordId: Int? = null,

	@field:SerializedName("patientId")
	val patientId: Int? = null,

	@field:SerializedName("doctorId")
	val doctorId: Int? = null,

	@field:SerializedName("patient")
	val patient: Any? = null,

	@field:SerializedName("prescriptionItems")
	val prescriptionItems: List<PrescriptionItemsItem_?>? = null,

	@field:SerializedName("diagnosis")
	val diagnosis: String? = null,

	@field:SerializedName("re_appointement_date")
	val reAppointementDate: String? = null,

	@field:SerializedName("prescription_Date")
	val prescriptionDate: String? = null
) : Serializable
