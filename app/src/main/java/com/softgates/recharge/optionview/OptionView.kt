package com.softgates.recharge.optionview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.softgates.recharge.databinding.OptionviewBinding
import com.softgates.recharge.databinding.ThankuviewBinding

class OptionView : Fragment() {

    lateinit var binding: OptionviewBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = OptionviewBinding.inflate(inflater)
        binding.existcustomer.setOnClickListener {
            val action = OptionViewDirections.actionOptionViewToExistingView()
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.newcustomer.setOnClickListener {
            val action = OptionViewDirections.actionOptionViewToRegister()
            NavHostFragment.findNavController(this).navigate(action)
        }
        return binding.root

    }
}