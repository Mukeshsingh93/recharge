package com.softgates.recharge.network

import com.squareup.moshi.Json

data class ResponseModel(

    @Json(name="status")
    var status: String? = null,

    @Json(name="message")
    var message: String? = null,

    @Json(name="user_id")
    var user_id: String? = null,

    @Json(name="password")
    var password: String? = null,


    @Json(name="userData")
    var userData: UserData? = null,

    @Json(name="Credentials")
    var Credentials: Credentials? = null,

    @Json(name="plans")
    var plans: MutableList<PlanList>? = null,

    @Json(name="customerdetails")
    var customerdetails: MutableList<CustomerDetails>? = null



)