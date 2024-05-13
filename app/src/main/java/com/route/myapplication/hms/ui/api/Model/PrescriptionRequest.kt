package com.route.myapplication.hms.ui.api.Model

import com.route.myapplication.hms.ui.DoctorUserFragments.prescriptionItemDetails



data class PrescriptionRequest(var prescriptionItems:MutableList<prescriptionItemDetails>,
                               var prescription_Date : String,
                               var re_appointement_date :String,
                                var patientId : Int,
                                var doctorId : Int,
                                var diagnosis: String,
                                var indoorPatientRecordId : Int?, )
