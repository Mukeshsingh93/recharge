package com.softgates.recharge.network

import com.squareup.moshi.Json

data class CustomerDetails(

    @Json(name="id")
    var id: String? = null,

    @Json(name="name")
    var name: String? = null,

    @Json(name="mobile")
    var mobile: String? = null,

    @Json(name="flat_no")
    var flat_no: String? = null,

    @Json(name="building_name")
    var building_name: String? = null,

    @Json(name="date")
    var date: String? = null,

    @Json(name="buy_plan")
    var buy_plan: String? = null,

    @Json(name="logindetails")
    var logindetails: MutableList<LoginDetail>? = null
)