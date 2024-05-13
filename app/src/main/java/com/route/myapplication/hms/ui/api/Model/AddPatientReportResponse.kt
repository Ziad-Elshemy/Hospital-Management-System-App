package com.route.myapplication.hms.ui.api.Model

import com.google.gson.annotations.SerializedName

data class AddPatientReportResponse(

	@field:SerializedName("listOfMedicineNames")
	val listOfMedicineNames: List<Any?>? = null,

	@field:SerializedName("enterDate")
	val enterDate: Any? = null,

	@field:SerializedName("indoorPatientRecordId")
	val indoorPatientRecordId: Any? = null,

	@field:SerializedName("patientId")
	val patientId: Int? = null,

	@field:SerializedName("testNames")
	val testNames: List<Any?>? = null,

	@field:SerializedName("scanNames")
	val scanNames: List<Any?>? = null,

	@field:SerializedName("lastPrescription")
	val lastPrescription: LastPrescription? = null,

	@field:SerializedName("recommendation")
	val recommendation: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("dateOfDischarge")
	val dateOfDischarge: String? = null,

	@field:SerializedName("causeOfAdmission")
	val causeOfAdmission: String? = null
)

data class PrescriptionReport(

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
	val prescriptionItems: List<PrescriptionItemsItem?>? = null,

	@field:SerializedName("diagnosis")
	val diagnosis: String? = null,

	@field:SerializedName("re_appointement_date")
	val reAppointementDate: String? = null,

	@field:SerializedName("prescription_Date")
	val prescriptionDate: String? = null
)

data class LastPrescriptionReport(

	@field:SerializedName("presciption")
	val presciption: Any? = null,

	@field:SerializedName("prescription")
	val prescription: Prescription? = null,

	@field:SerializedName("doctorFullName")
	val doctorFullName: String? = null,

	@field:SerializedName("department")
	val department: String? = null
)

data class PrescriptionItemsItemReport(

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
