package com.softgates.recharge.reguser

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.softgates.recharge.util.ApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class RegisterViewModel (
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

    private val _mobNo = MutableLiveData<String>()
    val mobNo : LiveData<String?>
        get() = _mobNo

    private val _flatNo = MutableLiveData<String>()
    val flatNo : LiveData<String?>
        get() = _flatNo

    private val _buildingName = MutableLiveData<String>()
    val buildingName : LiveData<String?>
        get() = _buildingName

    private val _pin = MutableLiveData<String>()
    val pin : LiveData<String?>
        get() = _pin

    private val _navigateToScreen = MutableLiveData<Int>()
    val navigateToScreen : LiveData<Int?>
        get() = _navigateToScreen

    init {
        _notifyView.value=0
        _userName.value=""
        _mobNo.value=""
        _flatNo.value=""
        _buildingName.value=""
        Log.e("APIRESPONSE","wishlist api is called...")
    }

    fun onTextChangedName(s: CharSequence, start: Int, before: Int, count: Int) {
        if(!s.toString().isEmpty())
        {
            _userName.value= s.toString()
        }
        else
        {
            _userName.value=""
        }
    }

    fun  getName(): String
    {
       return _userName.value!!
    }
    fun onTextChangedMobileNo(s: CharSequence, start: Int, before: Int, count: Int) {
        if(!s.toString().isEmpty())
        {
            _mobNo.value= s.toString()
        }
        else
        {
            _mobNo.value=""
        }
    }

    fun onTextChangedFlatNo(s: CharSequence, start: Int, before: Int, count: Int) {
        if(!s.toString().isEmpty())
        {
            _flatNo.value= s.toString()
        }
        else
        {
            _flatNo.value=""
        }
    }

    fun onTextChangedBuildingNo(s: CharSequence, start: Int, before: Int, count: Int) {
        if(!s.toString().isEmpty())
        {
            _buildingName.value= s.toString()
        }
        else
        {
            _buildingName.value=""
        }
    }

    fun submit()
    {
       // _navigateToScreen.value=1
      when {
            _userName.value!!.isEmpty() -> _message.value="The username field is required."
            _mobNo.value!!.isEmpty() -> _message.value="The mobile no field is required."
            _flatNo.value!!.isEmpty() -> _message.value="The flat no field is required."
            _buildingName.value!!.isEmpty() -> _message.value="The building no field is required."
            else ->  _navigateToScreen.value=1
        }
    }

 /*   fun loginApi()
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
                    if(response.status== Constant.SUCCEESSSTATUS)
                    {
                        //   _message.value= response.message
                        _navigateToScreen.value=3
                    }
                    else
                    {
                        _message.value= response.message
                    }

                } catch (e: Exception) {
                    _status.value = ApiStatus.ERROR
                    _message.value= "Api Failure "+e.message
                    Log.e(Constant.APIRESPONSE,"login api failure response is......"+e.message.toString())
                }
            }
        }
    }*/

    fun complete() {
        _navigateToScreen.value=0
    }
}