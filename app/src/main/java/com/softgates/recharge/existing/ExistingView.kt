package com.softgates.recharge.existing

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.softgates.recharge.ProgressDialog
import com.softgates.recharge.R
import com.softgates.recharge.databinding.ActivityPlanBinding
import com.softgates.recharge.databinding.ExistingviewBinding
import com.softgates.recharge.network.CustomerDetails
import com.softgates.recharge.util.ApiStatus
import com.softgates.recharge.util.Constant
import java.util.*

class ExistingView  : Fragment()
{
    lateinit var binding: ExistingviewBinding
    private lateinit var viewModel : ExistingViewModel
    private lateinit var loader: ProgressDialog
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var sharedPreferences: SharedPreferences
    var name:String=""
    var mobno:String=""
    var flatno:String=""
    var buildingno:String=""

    val c = Calendar.getInstance()
    var year = c.get(Calendar.YEAR)
    var month = c.get(Calendar.MONTH)
    var day = c.get(Calendar.DAY_OF_MONTH)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ExistingviewBinding.inflate(inflater)
        //     var view = inflater.inflate(R.layout.login_view, container, false)
        val application = requireNotNull(this.activity).application
        sharedPreferences = (activity as AppCompatActivity).getSharedPreferences(Constant.SHAREDPREFERENCEFILE, Context.MODE_PRIVATE)

        val viewModelFactory =  ExistingViewModelFactory(sharedPreferences,application)

//        name = PlanListViewArgs.fromBundle(arguments!!).name
//        mobno = PlanListViewArgs.fromBundle(arguments!!).mobileno
//        flatno = PlanListViewArgs.fromBundle(arguments!!).flatno
//        buildingno = PlanListViewArgs.fromBundle(arguments!!).buildingname

        Log.e("PLANDATA","plan name value..."+name.toString())
        Log.e("PLANDATA","plan mobno value..."+mobno.toString())
        Log.e("PLANDATA","plan flatno value..."+flatno)
        Log.e("PLANDATA","plan buildingno value..."+buildingno)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ExistingViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.message.observe(viewLifecycleOwner, Observer {
            it?.let {
                Toast.makeText(activity,it.toString(), Toast.LENGTH_SHORT).show()
            }
        })

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

//        linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//        binding.searchrecyclerview?.setLayoutManager(linearLayoutManager)

        linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.searchrecyclerview?.setLayoutManager(linearLayoutManager)
       val adapter = ExistNewAdapter(OnClick { data, type, position ->
            //  Toast.makeText(context, "${nightId}", Toast.LENGTH_LONG).show()
            Log.e("CARTLIST","click is....$position !! type...$type")
            if(type==1)
            {
                Log.e("DELETEITEM","delete item is....."+position)

                showDialog(data)

                //  showDialog(data.id!!)
                //  viewModel.planlistview(name,mobno,flatno,buildingno,data.id!!);
            }
            else if(type==2)
            {
               // registerSuccessDialog(data);
            }
        })

        viewModel.GetPlanlist.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
            Log.e("APIRESPONSE","offer product is....."+it.size)
        })

        binding.searchrecyclerview?.adapter = adapter
        viewModel.notifyItem.observe(viewLifecycleOwner, Observer {
            Log.e("ADDIMAGE","notifyItem observer is calledd....")
            binding.searchrecyclerview?.adapter?.notifyDataSetChanged();
            //   viewModel.updateposition()
        })

        viewModel.navigateToScreen.observe(viewLifecycleOwner, Observer {
            Log.e("PLANLISTVIEW","plan list view username......"+it)
            it?.let {
                if(it==1)
                {
                    Log.e("PLANLISTVIEW","plan list view username......"+viewModel.userName)
                    Log.e("PLANLISTVIEW","plan list view password......"+viewModel.password)
                    val action = ExistingViewDirections.actionExistingViewToThankuView(viewModel.userName,viewModel.password)
                 //   val action = PlanListViewDirections.actionPlanListViewToThankuView(viewModel.userName,viewModel.password)
                    NavHostFragment.findNavController(this).navigate(action)
                    viewModel.complete()
                }
                //  Toast.makeText(activity,it.toString(), Toast.LENGTH_SHORT).show()
            }
        })

        binding.mobilenoradiobtn?.setOnClickListener {

        binding.searchbyname!!.isChecked = false
            viewModel.setSearch(0)
        }

        binding.searchbyname?.setOnClickListener {
            binding.mobilenoradiobtn!!.isChecked = false
            viewModel.setSearch(1)
        }

        return binding.root
    }


    fun showDialog(custDetails: CustomerDetails) {
        val dialog = Dialog(activity as AppCompatActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.custom_dialogbox_otp)
        val no: RelativeLayout = dialog.findViewById(R.id.no) as RelativeLayout
        val yes: RelativeLayout = dialog.findViewById(R.id.yes) as RelativeLayout
        dialog.getWindow()!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT);
        no.setOnClickListener {
            // dialog.hide()

            val c = Calendar.getInstance()
            year = c.get(Calendar.YEAR)
            //  mYear =mYear-18;
            month = c.get(Calendar.MONTH)
            day = c.get(Calendar.DAY_OF_MONTH)
            c.set(year, month, day)
            val dpd = DatePickerDialog((activity as AppCompatActivity), DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in textbox
                //   lblDate.setText("" + dayOfMonth + " " + MONTHS[monthOfYear] + ", " + year)
//                    binding.expirydate.setText("" + dayOfMonth + "-" + (monthOfYear+1) + "-" + year)
//                    viewModel.setExpiryDate("" + dayOfMonth + "-" + (monthOfYear+1) + "-" + year)
                Log.e("MONTHDATE","monthdate iss......."+"" + dayOfMonth + "-" + (monthOfYear+1) + "-" + year)

                var date:String= ""+ dayOfMonth + "-" + (monthOfYear+1) + "-" + year;

                Log.e("RECHARGE","monthdate iss......."+"" + dayOfMonth + "-" + (monthOfYear+1) + "-" + year)
                Log.e("RECHARGE","userid........." + custDetails.id)
                Log.e("RECHARGE","buyplan........." + custDetails.buy_plan)
                Log.e("RECHARGE","login........." + custDetails.logindetails!!.get(0).login)
                Log.e("RECHARGE","password........." + custDetails.logindetails!!.get(0).PASSWORD)

               viewModel.rechargeview(custDetails.id.toString(), custDetails.buy_plan.toString(),custDetails.logindetails!!.get(0).login.toString(), custDetails.logindetails!!.get(0).PASSWORD.toString(),"CREDIT",date);

                dialog.hide()
            }, year, month, day)
            dpd.show()
            dpd.getDatePicker().setMinDate(c.timeInMillis)
        }
        yes.setOnClickListener {
            dialog.hide()
            viewModel.rechargeview(custDetails.id.toString(), custDetails.buy_plan.toString(),custDetails.logindetails!!.get(0).login.toString(), custDetails.logindetails!!.get(0).PASSWORD.toString(),"CASH","");

            //   viewModel.rechargeview(name,mobno,flatno,buildingno,id,"CASH","");
            /*   val action = ProfileTabViewDirections.actionProfiletabToAddCommercialView()
              // action.loginType="VENDOR"
               NavHostFragment.findNavController(this).navigate(action)*/
        }
        dialog.show()
    }






    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loader = ProgressDialog(view.context)
    }

}