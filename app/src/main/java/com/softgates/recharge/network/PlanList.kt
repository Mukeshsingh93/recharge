package com.softgates.recharge.network

import com.squareup.moshi.Json

data class PlanList(

    @Json(name="id")
var id: String? = null,

@Json(name="plan_name")
var plan_name: String? = null,

@Json(name="plan_time")
var plan_time: String? = null,

@Json(name="data")
    var data: String? = null,

    @Json(name="price")
    var price: String? = null,

@Json(name="description")
var description: String? = null

)