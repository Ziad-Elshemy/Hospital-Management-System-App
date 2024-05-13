package com.route.myapplication.hms.ui.PatientUserFragments

import com.google.gson.annotations.SerializedName

data class PatientUpcomingAppointmentDetails(val doctorName: String,
                                             val slotTime: String,
                                             val appointmentDate: String,
                                             val timeAcronym: String)
