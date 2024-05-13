package com.route.myapplication.hms.ui.api.Model

data class AdminPatientsTableResponse (
    val imageID: Int?=null,
    var name : String?=null,
    var nationalID : String?=null,
    var email: String?=null,
    var gender : String?=null,
    var age : Int?=null,
    var address : String?=null,
    var phone : String?=null,
    var blood_type : String?=null
        )
