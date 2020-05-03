package com.softgates.recharge.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

object Constant
{

    const val ADDPRODUCTBTN = "ADDPRODUCTBTN"

    const val PROPERTYDETAILDATA ="PROPERTYDETAILDATA"
    const val ADDCOMMERCIAL ="ADDCOMMERCIAL"
    const val ADDRESIDENT ="ADDRESIDENT"
    const val SUCCEESSSTATUS = "1"
    const val CATID = "CATID"
    const val SUBCATID = "SUBCATID"
    const val PROPERTYDETAIL = "PROPERTYDETAIL"
    const val TOKENEXPIRED = 505
    const val TOKEN = ""
    const val LANGUAGAE = "LANGUAGAE"
    const val LOGINGVIEW = "LOGINGVIEW"
    const val APIRESPONSE = "APIRESPONSE"
    const val ADDTOCARTLISTVIEW = "ADDTOCARTLISTVIEW"
    const val PRODUCTVIEW = "PRODUCTVIEW"
    const val WISHLISTVIEW = "WISHLISTVIEW"
    const val ORDERVIEW = "ORDERVIEW"
    const val USERID = "USERID"
    const val FAQ = "FAQ"
    const val CONTACTUS = "CONTACTUS"
    const val PROFILEVIEW = "PROFILEVIEW"
    const val WEBVIEW = "WEBVIEW"
    const val PRODUCTDETAIL = "PRODUCTDETAIL"
    const val ALLPRODUCTDETAIL = "ALLPRODUCTDETAIL"
    const val OFFERDETAIL = "OFFERDETAIL"
    const val SHAREDPREFERENCEFILE = "SHAREDPREFERENCEFILE"

    fun connected(context:Context) : Boolean
    {
        val connectivityManager = context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        return  isConnected
      //  return true
    }

}

