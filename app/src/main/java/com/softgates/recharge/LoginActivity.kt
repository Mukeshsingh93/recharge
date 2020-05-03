package com.softgates.recharge

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class LoginActivity : AppCompatActivity()
{

    private lateinit var loader: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        loader = ProgressDialog(this)
        val s:String="Hi"
        lateinit var t:String
        val signinbtn = findViewById(R.id.signin) as CardView
        val email = findViewById(R.id.email) as EditText
        val password = findViewById(R.id.password) as EditText
        signinbtn.setOnClickListener {
            loader.setLoading(true)

            Handler().postDelayed({

                loader.setLoading(false)
                /*  val inten = Intent(this@LoginActivity, LoginActivity::class.java)
                  startActivity(inten)
                  finish()*/
                if(email.text.toString().isEmpty())
                {
                    Toast.makeText(this,"Email id is required",Toast.LENGTH_SHORT).show()
                }
               // else if(!email.text.toString().equals("anil@smartgroupdxb.com") || !password.text.toString().equals("123456"))
                else if(!email.text.toString().equals("anil@gmail.com") || !password.text.toString().equals("123456"))
                {
                    Toast.makeText(this,"you have enter wrong email id and password",Toast.LENGTH_SHORT).show()
                }
                else
                {
                    val inten = Intent(this@LoginActivity, RechargeActivity::class.java)
                    startActivity(inten)
                    finish()
                }
//            val inten = Intent(this@SplashActivity, Activity_Login::class.java)
//            startActivity(inten)
//            finish()
            }, 3000)
          //  finish()
        }
    }
}