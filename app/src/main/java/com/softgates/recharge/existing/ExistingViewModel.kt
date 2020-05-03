package com.softgates.recharge.existing

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.softgates.recharge.R
import com.softgates.recharge.network.CustomerDetails
import com.softgates.recharge.util.ApiStatus
import com.softgates.recharge.util.Constant
import com.softgates.recharge.util.RechargeApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ExistingViewModel (val sharedPreferences: SharedPreferences,
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

    private val _mobileno = MutableLiveData<String>()
    val mobileno : LiveData<String?>
        get() = _mobileno

    private val _name = MutableLiveData<String>()
    val name : LiveData<String?>
        get() = _name

    private val _navigateToScreen = MutableLiveData<Int>()
    val navigateToScreen : LiveData<Int?>
        get() = _navigateToScreen

    private val _sessionEx = MutableLiveData<Int?>()
    val sessionEx: LiveData<Int?>
        get() = _sessionEx

    private val _GetPlanlist = MutableLiveData<List<CustomerDetails>>()
    val GetPlanlist: LiveData<List<CustomerDetails>>
        get() = _GetPlanlist

    private val searchlist = ArrayList<CustomerDetails>()

    var searchBy: Int=0;

    var userName: String="";
    var password: String="";

    init {
        _sessionEx.value =0
        _notifyView.value=0
        _pin.value="123456"
        _mobileno.value=""
        _name.value=""
        Log.e("APIRESPONSE","wishlist api is called...")
    }

    fun onTextChangedMobileno(s: CharSequence, start: Int, before: Int, count: Int) {
        if(!s.toString().isEmpty())
        {
            _mobileno.value= s.toString()
        }
        else
        {
            _mobileno.value=""
        }
    }

    fun onTextChangedName(s: CharSequence, start: Int, before: Int, count: Int) {
        if(!s.toString().isEmpty())
        {
            _name.value= s.toString()
        }
        else
        {
            _name.value=""
        }
    }


    fun rechargeview(userId:String,buyPlan:String,login:String,password:String,type:String,date:String)
    {
        Log.e("RECHARGE","recharge userid....."+userId+"...buyplan..."+buyPlan+"...login....."+login+"....password..."+password+"....type...."+type+"....date..."+date)
        selectPlanApi(userId,buyPlan,login,password,type,date)

    }

    fun selectPlanApi(userId:String,buyPlan:String,login:String,userpassword:String,type:String,date:String)
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
            hashMap.put("user_id" ,userId)
            hashMap.put("buy_plan" , buyPlan)
            hashMap.put("login" , login)
            hashMap.put("password" , userpassword)
            hashMap.put("type" , type)
            hashMap.put("c_date" , date)
            _status.value = ApiStatus.LOADING
            coroutineScope.launch {
                // Get the Deferred object for our Retrofit request
                Log.e(Constant.APIRESPONSE,"renew api hashmap....."+hashMap.toString())
                var getPropertiesDeferred = RechargeApi.retrofitService.selectrevenue(hashMap!!)
                try {
                    val response = getPropertiesDeferred.await()
                    Log.e(Constant.APIRESPONSE,"renew api response is......"+response.toString())
                    _status.value = ApiStatus.DONE
                    if(response.status== Constant.SUCCEESSSTATUS)
                    {
                        //   _message.value= response.message
                        userName = login.toString()
                        password = userpassword.toString()
                        _navigateToScreen.value=1
                    }
                    else
                    {
                        _message.value= response.message
                    }
                } catch (e: Exception) {
                    _status.value = ApiStatus.ERROR
                    _message.value= "Api Failure "+e.message
                    Log.e(Constant.APIRESPONSE,"renew api failure response is......"+e.message.toString())
                }
            }
        }
    }


    fun search()
    {
        when {
            searchBy.equals(0) ->
                when{
                    _mobileno.value!!.isEmpty() -> _message.value="The mobile no field is required."
                    else -> searchApi(_mobileno.value!!.toString())
                }
            searchBy.equals(1) ->
                when{
                    _name.value!!.isEmpty() -> _message.value="The name field is required."
                    else -> searchApi(_name.value!!.toString())
                }
        }
    }

    fun setSearch(search:Int)
    {
        searchBy=search
    }

    fun searchApi(searchText:String)
    {
        if(!Constant.connected(context))
        {
            _message.value= context.resources.getString(R.string.nointernet)
        }
        else
        {
            if(searchlist.size>0)
            {
                searchlist.clear()
                _notifyItem.value = 1

            }
            var hashMap : HashMap<String, String>
                    = HashMap<String, String> ()
            hashMap.put("input" , searchText.toString())
            _status.value = ApiStatus.LOADING
            coroutineScope.launch {
                // Get the Deferred object for our Retrofit request
                Log.e(Constant.APIRESPONSE,"login api hashmap....."+hashMap.toString())
                var getPropertiesDeferred = RechargeApi.retrofitService.search(hashMap!!)
                try {
                    val response = getPropertiesDeferred.await()
                    Log.e(Constant.APIRESPONSE,"login api response is......"+response.toString())
                    _status.value = ApiStatus.DONE
                    if(response.status == Constant.SUCCEESSSTATUS)
                    {
                        Log.e(Constant.APIRESPONSE,"success is called......"+response.toString())


                        Log.e(Constant.APIRESPONSE,"Search api success is called......"+response.toString())




                        //   _message.value= response.message
                        searchlist.addAll(response.customerdetails!! as MutableList<CustomerDetails>)
                        _GetPlanlist.value=searchlist
                        Log.e(Constant.APIRESPONSE,"success size size size is called......"+GetPlanlist.value!!.size)
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