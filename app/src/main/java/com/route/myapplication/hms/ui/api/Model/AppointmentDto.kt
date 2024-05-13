package com.route.myapplication.hms.ui.api.Model



data class AppointmentDto(var appointmentDate :String,
                          var appointmentType:String,
                          var complain : String,
                          var patientId : Int,
                          var doctorId :Int)
