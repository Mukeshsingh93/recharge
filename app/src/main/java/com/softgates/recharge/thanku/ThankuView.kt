package com.softgates.recharge.thanku

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.softgates.recharge.databinding.RechargeActivityBinding
import com.softgates.recharge.databinding.ThankuviewBinding
import com.softgates.recharge.reguser.RegisterViewModel

class ThankuView : Fragment()
{
    lateinit var binding: ThankuviewBinding
    private lateinit var viewModel : RegisterViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ThankuviewBinding.inflate(inflater)

        val username = ThankuViewArgs.fromBundle(arguments!!).username
        val password = ThankuViewArgs.fromBundle(arguments!!).password

        binding.username.text = "Username : "+username
        binding.password.text = "Password : "+password
        //     var view = inflater.inflate(R.layout.login_view, container, false)
      /*  val application = requireNotNull(this.activity).application
        val viewModelFactory =
            RegisterViewModelFactory(application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(RegisterViewModel::class.java)
        binding.viewModel = viewModel*/

        binding.okay!!.setOnClickListener {

            val action = ThankuViewDirections.actionThankuViewToRegister()
            NavHostFragment.findNavController(this).navigate(action)
        }

      /*  Handler().postDelayed({
           val action = ThankuViewDirections.actionThankuViewToRegister()
             NavHostFragment.findNavController(this).navigate(action)
            // viewModel.complete()
        }, 3000)*/
        return binding.root
    }
}