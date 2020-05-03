package com.softgates.recharge.network

import com.squareup.moshi.Json

data class UserData(
    @Json(name="name")
    var name: String? = null,

    @Json(name="id")
    var id: String? = null,

    @Json(name="email")
    var email: String? = null



    )