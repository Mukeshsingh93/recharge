package com.softgates.recharge.planlist

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.softgates.recharge.R
import com.softgates.recharge.network.PlanList
import com.softgates.recharge.util.ApiStatus
import com.softgates.recharge.util.Constant
import com.softgates.recharge.util.RechargeApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PlanListViewModel (val sharedPreferences: SharedPreferences,
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

    private val _pin = MutableLiveData<String>()
    val pin : LiveData<String?>
        get() = _pin

    private val _navigateToScreen = MutableLiveData<Int>()
    val navigateToScreen : LiveData<Int?>
        get() = _navigateToScreen

    private val _sessionEx = MutableLiveData<Int?>()
    val sessionEx: LiveData<Int?>
        get() = _sessionEx

    private val _GetPlanlist = MutableLiveData<List<PlanList>>()
    val GetPlanlist: LiveData<List<PlanList>>
        get() = _GetPlanlist

    var userName: String="";
     var password: String="";


    init {
        _sessionEx.value =0
        _notifyView.value=0
        _pin.value="123456"
        planApi()
        Log.e("APIRESPONSE","wishlist api is called...")
    }





    fun planApi()
    {
        if(!Constant.connected(context))
        {
            _message.value= context.resources.getString(R.string.nointernet)
        }
        else
        {
            var hashMap : HashMap<String, String>
                    = HashMap<String, String> ()
            hashMap.put("password" , pin.value!!)
            _status.value = ApiStatus.LOADING
            coroutineScope.launch {
                // Get the Deferred object for our Retrofit request
                Log.e(Constant.APIRESPONSE,"login api hashmap....."+hashMap.toString())
                var getPropertiesDeferred = RechargeApi.retrofitService.getPlan()
                try {
                    val response = getPropertiesDeferred.await()
                    Log.e(Constant.APIRESPONSE,"login api response is......"+response.toString())
                    Log.e(Constant.APIRESPONSE,"wishlistapi api response is......"+response.toString())
                    _status.value = ApiStatus.DONE
                    if(response.status== Constant.SUCCEESSSTATUS)
                    {
                        //   _message.value= response.message
                        _GetPlanlist.value =  response.plans!! as MutableList<PlanList>
                    }
                    else
                    {
                        _message.value= response.message
                    }
                } catch (e: Exception) {
                    _status.value = ApiStatus.ERROR
                   // _navigateToScreen.value=3
                    _message.value= "Api Failure "+e.message
                    Log.e(Constant.APIRESPONSE,"login api failure response is......"+e.message.toString())
                }
            }
        }
    }

    fun planlistview(name:String,mobileno:String,flatno:String,buildingname:String,buyid:String,type:String,date:String)
    {
        Log.e("RECHARGE","recharge data....."+name+"...mobileno..."+mobileno+"...flatno....."+flatno+"....buildingname..."+buildingname+".....buyid..."+buyid+"....type...."+type+"....date..."+date)
        selectPlanApi(name,mobileno,flatno,buildingname,buyid,type,date)
    }

    fun selectPlanApi(name:String,mobileno:String,flatno:String,buildingname:String,buyid:String,type:String,date:String)
    {
        if(!Constant.connected(context))
        {
            _message.value= context.resources.getString(R.string.nointernet)
        }
        else
        {
            var userid: String =  sharedPreferences.getString(Constant.USERID,"").toString();
            Log.e("USERID","userid........"+userid.toString())
            var hashMap : HashMap<String, String>
                    = HashMap<String, String> ()
            hashMap.put("user_id" ,userid)
            hashMap.put("name" , name)
            hashMap.put("phone" , mobileno)
            hashMap.put("flat_no" , flatno)
            hashMap.put("building_name" , buildingname)
            hashMap.put("buy_plan" , buyid)
            hashMap.put("type" , type)
            hashMap.put("c_date" , date)
            _status.value = ApiStatus.LOADING
            coroutineScope.launch {
                // Get the Deferred object for our Retrofit request
                Log.e(Constant.APIRESPONSE,"login api hashmap....."+hashMap.toString())
                var getPropertiesDeferred = RechargeApi.retrofitService.selectPlan(hashMap!!)
                try {
                    val response = getPropertiesDeferred.await()
                    Log.e(Constant.APIRESPONSE,"login api response is......"+response.toString())
                    Log.e(Constant.APIRESPONSE,"wishlistapi api response is......"+response.toString())
                    _status.value = ApiStatus.DONE
                    if(response.status== Constant.SUCCEESSSTATUS)
                    {
                        //   _message.value= response.message
                        userName = response.Credentials!!.login.toString()
                        password = response.Credentials!!.password.toString()
                        _navigateToScreen.value=1
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
    }

    fun complete() {

    }
}