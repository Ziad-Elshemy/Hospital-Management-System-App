package com.route.myapplication.hms.ui.api.Model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GetIndoorPatientRecordByPatientIdResponse(

	@field:SerializedName("GetIndoorPatientRecordByPatientIdResponse")
	val getIndoorPatientRecordByPatientIdResponse: List<GetIndoorPatientRecordByPatientIdResponseItem?>? = null
)

data class PrescriptionsItem(

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
	val prescriptionItems: List<Any?>? = null,

	@field:SerializedName("diagnosis")
	val diagnosis: String? = null,

	@field:SerializedName("re_appointement_date")
	val reAppointementDate: String? = null,

	@field:SerializedName("prescription_Date")
	val prescriptionDate: String? = null
)

data class GetIndoorPatientRecordByPatientIdResponseItem(

	@field:SerializedName("enterDate")
	val enterDate: String? = null,

	@field:SerializedName("patientTests")
	val patientTests: List<PatientTestsItem?>? = null,

	@field:SerializedName("patientscans")
	val patientscans: List<PatientscansItem?>? = null,

	@field:SerializedName("roomNumber")
	val roomNumber: Int? = null,

	@field:SerializedName("patientRecordId")
	val patientRecordId: Int? = null,

	@field:SerializedName("floorNumber")
	val floorNumber: Int? = null,

	@field:SerializedName("bedNumber")
	val bedNumber: Int? = null,

	@field:SerializedName("discahrgeDate")
	val discahrgeDate: String? = null,

	@field:SerializedName("prescriptions")
	val prescriptions: List<PrescriptionsItem?>? = null,

	@field:SerializedName("roomType")
	val roomType: String? = null
) : Serializable

data class PatientTestsItem(

	@field:SerializedName("doctor")
	val doctor: Any? = null,

	@field:SerializedName("indoorPatientRecord")
	val indoorPatientRecord: Any? = null,

	@field:SerializedName("patientTestId")
	val patientTestId: Int? = null,

	@field:SerializedName("test")
	val test: Any? = null,

	@field:SerializedName("indoorPatientRecordId")
	val indoorPatientRecordId: Int? = null,

	@field:SerializedName("patientId")
	val patientId: Int? = null,

	@field:SerializedName("doctorId")
	val doctorId: Int? = null,

	@field:SerializedName("patient")
	val patient: Any? = null,

	@field:SerializedName("categoricalDetails")
	val categoricalDetails: List<Any?>? = null,

	@field:SerializedName("numericalDetails")
	val numericalDetails: List<Any?>? = null,

	@field:SerializedName("testId")
	val testId: Int? = null,

	@field:SerializedName("testDate")
	val testDate: String? = null
)

data class PatientscansItem(

	@field:SerializedName("doctor")
	val doctor: Any? = null,

	@field:SerializedName("indoorPatientRecord")
	val indoorPatientRecord: Any? = null,

	@field:SerializedName("patientScanId")
	val patientScanId: Int? = null,

	@field:SerializedName("writtenReport")
	val writtenReport: String? = null,

	@field:SerializedName("indoorPatientRecordId")
	val indoorPatientRecordId: Int? = null,

	@field:SerializedName("patientId")
	val patientId: Int? = null,

	@field:SerializedName("doctorId")
	val doctorId: Int? = null,

	@field:SerializedName("scanImages")
	val scanImages: List<Any?>? = null,

	@field:SerializedName("patient")
	val patient: Any? = null,

	@field:SerializedName("scanId")
	val scanId: Int? = null,

	@field:SerializedName("scan")
	val scan: Any? = null,

	@field:SerializedName("scanDate")
	val scanDate: String? = null
)
