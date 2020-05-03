package com.softgates.recharge.network

import com.squareup.moshi.Json

data class Credentials(

    @Json(name="login")
    var login: String? = null,

    @Json(name="password")
    var password: String? = null
)