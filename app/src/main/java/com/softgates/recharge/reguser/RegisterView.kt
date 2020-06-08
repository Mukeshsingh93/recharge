package com.softgates.recharge.reguser

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.softgates.recharge.databinding.LoginViewBinding
import com.softgates.recharge.databinding.RechargeActivityBinding

class RegisterView : Fragment()
{
    lateinit var binding: RechargeActivityBinding
    private lateinit var viewModel : RegisterViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = RechargeActivityBinding.inflate(inflater)
        //     var view = inflater.inflate(R.layout.login_view, container, false)
        val application = requireNotNull(this.activity).application
        val viewModelFactory =
            RegisterViewModelFactory(application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(RegisterViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.navigateToScreen.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it==1)
                {

                  var name=  viewModel.userName!!.value
                  var mobileno=  viewModel.mobNo!!.value
                  var flatno=  viewModel.flatNo!!.value
                  var buildingno=  viewModel.buildingName!!.value

                    Log.e("LOGDATA","name is...."+name.toString())
                    val action = RegisterViewDirections.actionCouponActivityToPlanListView(name!!,mobileno!!,flatno!!,buildingno!!)
                    NavHostFragment.findNavController(this).navigate(action)
                    viewModel.complete()
                }
                //  Toast.makeText(activity,it.toString(), Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.message.observe(viewLifecycleOwner, Observer {
            it?.let {
                Toast.makeText(activity,it.toString(), Toast.LENGTH_SHORT).show()
            }
        })
        return binding.root
    }
}