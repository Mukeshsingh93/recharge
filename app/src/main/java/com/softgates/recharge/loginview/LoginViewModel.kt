package com.softgates.recharge.loginview

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.softgates.recharge.R
import com.softgates.recharge.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoginViewModel (val sharedPreferences: SharedPreferences,
                      application: Application
) : AndroidViewModel(application) {


    private val context = getApplication<Application>().applicationContext
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _message = MutableLiveData<String>()
    val message: LiveData<String?>
        get() = _message

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus?>
        get() = _status

    private val _loadingStatus = MutableLiveData<Int>()
    val loadingStatus : LiveData<Int?>
        get() = _loadingStatus

    private val _notifyItem = MutableLiveData<Int>()
    val notifyItem : LiveData<Int?>
        get() = _notifyItem

    private val _notifyView = MutableLiveData<Int>()
    val notifyView : LiveData<Int?>
        get() = _notifyView

    private val _notifyItemRemove = MutableLiveData<Int>()
    val notifyItemRemove : LiveData<Int?>
        get() = _notifyItemRemove


    private val _userName = MutableLiveData<String>()
    val userName : LiveData<String?>
        get() = _userName



    private val _pin = MutableLiveData<String>()
    val pin : LiveData<String?>
        get() = _pin

    private val _navigateToScreen = MutableLiveData<Int>()
    val navigateToScreen : LiveData<Int?>
        get() = _navigateToScreen

    private val _sessionEx = MutableLiveData<Int?>()
    val sessionEx: LiveData<Int?>
        get() = _sessionEx

    init {
        _sessionEx.value =0
        _notifyView.value=0
//       _userName.value="MUK2819"
   //     _userName.value="TEST4614"
        _userName.value=""
//        _userName.value=""
    //     _pin.value="Test@#2020"
         _pin.value=""
//        _pin.value="123456"
        Log.e("APIRESPONSE","wishlist api is called...")
     //   gitgitApi()
    }

    fun onTextChangedEmail(s: CharSequence, start: Int, before: Int, count: Int) {
        if(!s.toString().isEmpty())
        {
            _userName.value= s.toString()
        }
        else
        {
            _userName.value=""
        }
    }

    fun onTextChangedPin(s: CharSequence, start: Int, before: Int, count: Int) {
        if(!s.toString().isEmpty())
        {
            _pin.value= s.toString()
        }
        else
        {
            _pin.value=""
        }
    }

    fun signin()
    {
        when {
            _userName.value!!.isEmpty() -> _message.value="The username field is required."
      //      !ValidationUtil.isEmailValid(_email.value.toString()) ->_message.value="The Email address is not valid"
            _pin.value!!.isEmpty() -> _message.value="The password field is required."
            else -> loginApis()
        }
    }

    fun loginApi()
    {
        if(!Constant.connected(context))
        {
            _message.value= context.resources.getString(R.string.nointernet)
        }
        else
        {
            var hashMap : HashMap<String, String>
                    = HashMap<String, String> ()
            hashMap.put("input" , userName.value!!)
            hashMap.put("password" , pin.value!!)
            _status.value = ApiStatus.LOADING
            coroutineScope.launch {
                // Get the Deferred object for our Retrofit request
                Log.e(Constant.APIRESPONSE,"login api hashmap....."+hashMap.toString())
                var getPropertiesDeferred = RechargeApi.retrofitService.login(hashMap!!)
                try {
                    val response = getPropertiesDeferred.await()
                    Log.e(Constant.APIRESPONSE,"login api response is......"+response.toString())
                    Log.e(Constant.APIRESPONSE,"wishlistapi api response is......"+response.toString())
                    _status.value = ApiStatus.DONE
                    if(response.status==Constant.SUCCEESSSTATUS)
                    {
                        //   _message.value= response.message
                        sharedPreferences.edit { putString(Constant.USERID,response.userData!!.id) }
                        _navigateToScreen.value=3
                    }
                    else
                    {
                       _message.value= response.message
                    }
               }
                catch (e: Exception) {
                    _status.value = ApiStatus.ERROR
                    _message.value= "Api Failure "+e.message
                    Log.e(Constant.APIRESPONSE,"login api failure response is......"+e.message.toString())
                }
            }
        }
    }

    fun loginApis()
    {
        if(!Constant.connected(context))
        {
            _message.value= context.resources.getString(R.string.nointernet)
        }
        else
        {
            _status.value = ApiStatus.LOADING
            coroutineScope.launch {
                // Get the Deferred object for our Retrofit request
                var getPropertiesDeferred = RechargeApis.retrofitService.logins()
                try {
                    val response = getPropertiesDeferred.await()
                    Log.e(Constant.APIRESPONSE,"login api response is......"+response.toString())
                    Log.e(Constant.APIRESPONSE,"wishlistapi api response is......"+response.toString())
                    _status.value = ApiStatus.DONE
                    if(response.status==Constant.SUCCEESSSTATUS)
                    {
                        //   _message.value= response.message
                        sharedPreferences.edit { putString(Constant.USERID,response.userData!!.id) }
                        _navigateToScreen.value=3
                    }
                    else
                    {
                        _message.value= response.message
                    }
                }
                catch (e: Exception) {
                  //  _status.value = ApiStatus.ERROR
                   // _message.value= "Api Failure "+e.message
               //   Log.e(Constant.APIRESPONSE,"recharge recarge login api failure response is......"+e.message.toString())
                    if(e.message.toString().equals("HTTP 404 Not Found"))
                    {

                    }
                    else
                    {
                        loginApi()
                    }
                }
            }
        }
    }

    fun complete() {
        _navigateToScreen.value=0
    }
}