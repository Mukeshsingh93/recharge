package com.softgates.recharge

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity


class CouponActivity : AppCompatActivity()
{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.coupon_activity)
   }


    fun sendMessage(view: View?) { // Do something in response to button click
        val inten = Intent(this@CouponActivity, ThankuActivity::class.java)
        startActivity(inten)
       // finish()
    }
}