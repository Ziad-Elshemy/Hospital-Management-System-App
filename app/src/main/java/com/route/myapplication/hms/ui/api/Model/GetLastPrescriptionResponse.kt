package com.route.myapplication.hms.ui.api.Model

import com.google.gson.annotations.SerializedName

data class GetLastPrescriptionResponse(

	@field:SerializedName("result")
	val result: LastPrescriptionResult? = null,

	@field:SerializedName("exception")
	val exception: Any? = null,

	@field:SerializedName("asyncState")
	val asyncState: Any? = null,

	@field:SerializedName("isFaulted")
	val isFaulted: Boolean? = null,

	@field:SerializedName("isCanceled")
	val isCanceled: Boolean? = null,

	@field:SerializedName("creationOptions")
	val creationOptions: Int? = null,

	@field:SerializedName("isCompletedSuccessfully")
	val isCompletedSuccessfully: Boolean? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("status")
	val status: Int? = null,

	@field:SerializedName("isCompleted")
	val isCompleted: Boolean? = null
)

data class LastPrescriptionResult(

	@field:SerializedName("presciption")
	val presciption: Any? = null,

	@field:SerializedName("prescription")
	val prescription: Prescription? = null,

	@field:SerializedName("doctorFullName")
	val doctorFullName: String? = null,

	@field:SerializedName("department")
	val department: String? = null
)

data class Prescription(

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
	val prescriptionItems: List<PrescriptionListItem?>? = null,

	@field:SerializedName("diagnosis")
	val diagnosis: String? = null,

	@field:SerializedName("re_appointement_date")
	val reAppointementDate: String? = null,

	@field:SerializedName("prescription_Date")
	val prescriptionDate: String? = null
)

data class PrescriptionListItem(

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
