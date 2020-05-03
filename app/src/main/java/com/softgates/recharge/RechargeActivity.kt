package com.softgates.recharge

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class RechargeActivity : AppCompatActivity()
{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recharge_activity)

        val submitbtn = findViewById(R.id.submitbtn) as CardView
        submitbtn.setOnClickListener {

            val inten = Intent(this@RechargeActivity, CouponActivity::class.java)
            startActivity(inten)
            //  finish()

        }

    }
}