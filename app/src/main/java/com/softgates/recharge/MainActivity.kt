package com.softgates.recharge

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        var  android_id : String  = Settings.Secure.getString(this.getContentResolver(),
            Settings.Secure.ANDROID_ID);
//d4c8daefa26f4f56
        val telephonyManager =
            getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        if (ActivityCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(Manifest.permission.READ_PHONE_STATE),
                101
            )
            return
        }
        var IMEINumber:String  = telephonyManager.deviceId
//        var IMEINumbesr:String  = telephonyManager.imei
        Log.e("LOGINID","Android id is......"+android_id)
        Log.e("LOGINID","Android imei no is......"+IMEINumber)
      //  Log.e("LOGINID","Android imei no final is......"+IMEINumbesr)
    }
}
