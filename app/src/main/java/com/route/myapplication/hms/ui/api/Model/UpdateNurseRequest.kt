package com.route.myapplication.hms.ui.api.Model


data class UpdateNurseRequest(val id :Int,
val firstName:String,
val lastName :String,
val date :String,
val age : Int,
    val nationalId : String,
    val image : String?,
    val imageName:String?,
    val bloodType :String,
    val phoneNumber:String,
    val address:String,
    val gender:String,
    val userName :String,
    val mail:String,
    val password :String?,
    val role :String,
    val departmentId:Int?,
    val departmentName :String?,
    val isActive : Boolean,
    val nurseNotes : String?,
    val nurseDegree: String?,
    val nurseSpecialization: String?)


