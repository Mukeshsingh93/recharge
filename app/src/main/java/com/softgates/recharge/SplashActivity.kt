package com.softgates.recharge

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashActivity  : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        Handler().postDelayed({
//            val inten = Intent(this@SplashActivity, LoginActivity::class.java)
//            startActivity(inten)
//            finish()
            val inten = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(inten)
            finish()
        }, 3000)
    }
}