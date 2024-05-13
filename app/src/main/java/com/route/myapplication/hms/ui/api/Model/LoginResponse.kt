package com.route.myapplication.hms.ui.api.Model

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @field:SerializedName("role")
    val role: String? = null,

    @field:SerializedName("userName")
    val userName: String? = null,

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("token")
    val token: String? = null
)