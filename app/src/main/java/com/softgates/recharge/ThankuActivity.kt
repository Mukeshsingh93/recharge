package com.softgates.recharge

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class ThankuActivity  : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.thankuactivity)

        Handler().postDelayed({
            val inten = Intent(this@ThankuActivity, LoginActivity::class.java)
            startActivity(inten)
            finish()
//            val inten = Intent(this@SplashActivity, Activity_Login::class.java)
//            startActivity(inten)
//            finish()
        }, 3000)
    }
}