package com.softgates.recharge.existing

import android.util.Log
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


@BindingAdapter("date")
fun binddateTimeText(textView: TextView, date: String) {
    if(date!=null)
    {
        //val dates:String="2019-10-24 05:00:00"
        val dates:String=date
        Log.e("DATETIME","finaldate before..."+dates.toString())
        val dateformat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date: Date =dateformat.parse(dates)
        val fdateFormat =  SimpleDateFormat("dd MMMM yyyy")
        val finaldate = fdateFormat.format(date)
        Log.e("DATETIME","finaldate..."+finaldate.toString())
        textView.text= finaldate
    }
}