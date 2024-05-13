package com.route.myapplication.hms.ui.api.Model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GetAllPrescriptionsOfPatientResponse(

	@field:SerializedName("GetAllPrescriptionsOfPatientResponse")
	val getAllPrescriptionsOfPatientResponse: List<GetAllPrescriptionsOfPatientResponseItem?>? = null
)

data class PrescriptionItems(

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

data class GetAllPrescriptionsOfPatientResponseItem (

	@field:SerializedName("presciption")
	val presciption: Any? = null,

	@field:SerializedName("prescription")
	val prescription: PrescriptionDto? = null,

	@field:SerializedName("doctorFullName")
	val doctorFullName: String? = null,

	@field:SerializedName("department")
	val department: String? = null
) : Serializable

data class PrescriptionDto(

	@field:SerializedName("doctor")
	val doctor: Any? = null,

	@field:SerializedName("prescriptionId")
	val prescriptionId: Int? = null,

	@field:SerializedName("indoorPatientRecordId")
	val indoorPatientRecordId: Any? = null,

	@field:SerializedName("patientId")
	val patientId: Int? = null,

	@field:SerializedName("doctorId")
	val doctorId: Int? = null,

	@field:SerializedName("patient")
	val patient: Any? = null,

	@field:SerializedName("prescriptionItems")
	val prescriptionItems: List<PrescriptionItemsItem?>? = null,

	@field:SerializedName("diagnosis")
	val diagnosis: Any? = null,

	@field:SerializedName("re_appointement_date")
	val reAppointementDate: String? = null,

	@field:SerializedName("prescription_Date")
	val prescriptionDate: String? = null
)
