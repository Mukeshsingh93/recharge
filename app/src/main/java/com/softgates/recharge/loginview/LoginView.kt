package com.softgates.recharge.loginview

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.softgates.recharge.ProgressDialog
import com.softgates.recharge.databinding.LoginActivityBinding
import com.softgates.recharge.util.ApiStatus
import com.softgates.recharge.util.Constant

class LoginView : Fragment()
{
    lateinit var binding: LoginActivityBinding
    private lateinit var viewModel : LoginViewModel
    private lateinit var loader: ProgressDialog
    lateinit var sharedPreferences: SharedPreferences


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding = LoginActivityBinding.inflate(inflater)
        //     var view = inflater.inflate(R.layout.login_view, container, false)
        val application = requireNotNull(this.activity).application
        sharedPreferences = (activity as AppCompatActivity).getSharedPreferences(Constant.SHAREDPREFERENCEFILE, Context.MODE_PRIVATE)

        val viewModelFactory =
            LoginViewModelFactory(sharedPreferences,application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.message.observe(viewLifecycleOwner, Observer {
            it?.let {
                Toast.makeText(activity,it.toString(), Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.navigateToScreen.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it==3)
                {
                     val action = LoginViewDirections.actionLoginViewToOptionView()
                    // action.propertyView = data!!
                      NavHostFragment.findNavController(this).navigate(action)
                    viewModel.complete()
                }
              //  Toast.makeText(activity,it.toString(), Toast.LENGTH_SHORT).show()
            }
        })

       /* binding.signin.setOnClickListener {
           // val action = LoginViewDirections.actionLoginViewToCouponActivity()
           // action.propertyView = data!!
          //  NavHostFragment.findNavController(this).navigate(action)
           this.findNavController().navigate(
               LoginViewDirections.actionLoginViewToCouponActivity()
           )
         //   viewModel.complete()
            Log.e("CHECKDATA","ondata is called stripe stripe stripe stripe")
          //  this.findNavController().navigate(LoginViewDirections.actionLoginViewToCouponActivity())
        }*/

        viewModel.status.observe(viewLifecycleOwner, Observer {
            when (it) {
                ApiStatus.LOADING -> {
                    loader.setLoading(true)
                }
                ApiStatus.ERROR -> {
                    loader.setLoading(false)
                }
                ApiStatus.DONE -> {
                    loader.setLoading(false)
                }
            }
        })
        return binding.root
}
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loader = ProgressDialog(view.context)
    }
}