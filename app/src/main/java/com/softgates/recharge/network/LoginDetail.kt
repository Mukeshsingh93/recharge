package com.softgates.recharge.network

import com.squareup.moshi.Json

data class LoginDetail(

    @Json(name="login")
    var login: String? = null,

    @Json(name="PASSWORD")
    var PASSWORD: String? = null
)