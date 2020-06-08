package com.softgates.recharge.planlist

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
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.softgates.recharge.ProgressDialog
import com.softgates.recharge.R
import com.softgates.recharge.databinding.ActivityPlanBinding
import com.softgates.recharge.databinding.LoginActivityBinding
import com.softgates.recharge.databinding.PlanlistviewBinding
import com.softgates.recharge.network.PlanList
import com.softgates.recharge.util.ApiStatus
import com.softgates.recharge.util.Constant
import kotlinx.android.synthetic.main.description_layout.*
import java.util.*

class PlanListView : Fragment()
{
    lateinit var binding: ActivityPlanBinding
    private lateinit var viewModel : PlanListViewModel
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
        binding = ActivityPlanBinding.inflate(inflater)
        //     var view = inflater.inflate(R.layout.login_view, container, false)
        val application = requireNotNull(this.activity).application
        sharedPreferences = (activity as AppCompatActivity).getSharedPreferences(Constant.SHAREDPREFERENCEFILE, Context.MODE_PRIVATE)
        val viewModelFactory =
            PlanListViewModelFactory(sharedPreferences,application)
           name = PlanListViewArgs.fromBundle(arguments!!).name
           mobno = PlanListViewArgs.fromBundle(arguments!!).mobileno
           flatno = PlanListViewArgs.fromBundle(arguments!!).flatno
           buildingno = PlanListViewArgs.fromBundle(arguments!!).buildingname

        Log.e("PLANDATA","plan name value..."+name.toString())
        Log.e("PLANDATA","plan mobno value..."+mobno.toString())
        Log.e("PLANDATA","plan flatno value..."+flatno)
        Log.e("PLANDATA","plan buildingno value..."+buildingno)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(PlanListViewModel::class.java)
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

       linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.planrecyclerview.setLayoutManager(linearLayoutManager)
        val adapter = PlanListAdapter(OnClick { data, type, position ->
            //  Toast.makeText(context, "${nightId}", Toast.LENGTH_LONG).show()
            Log.e("CARTLIST","click is....$position !! type...$type")
            if(type==1)
            {
                Log.e("DELETEITEM","delete item is....."+position)
                showDialog(data.id!!)
               //  viewModel.planlistview(name,mobno,flatno,buildingno,data.id!!);
            }
            else if(type==2)
            {
                registerSuccessDialog(data);
            }
        })

        viewModel.GetPlanlist.observe(viewLifecycleOwner, Observer {
            Log.e("ADDTOPRODUCT","offer product is....."+it.size)
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        })

        viewModel.navigateToScreen.observe(viewLifecycleOwner, Observer {

            Log.e("PLANLISTVIEW","plan list view username......"+it)

            it?.let {
                if(it==1)
                {

                    Log.e("PLANLISTVIEW","plan list view username......"+viewModel.userName)
                    Log.e("PLANLISTVIEW","plan list view password......"+viewModel.password)

                    val action = PlanListViewDirections.actionPlanListViewToThankuView(viewModel.userName,viewModel.password)
                    NavHostFragment.findNavController(this).navigate(action)
                    viewModel.complete()
                }

                //  Toast.makeText(activity,it.toString(), Toast.LENGTH_SHORT).show()
            }
        })
        binding.planrecyclerview.adapter = adapter
        return binding.root
    }

    fun registerSuccessDialog(data:PlanList)
    {
        val dialogView = layoutInflater.inflate(R.layout.description_layout, null)
        val dialog = BottomSheetDialog((activity as AppCompatActivity))
        dialog.setCancelable(true);
        dialog.setContentView(dialogView)
        dialog.show()
        dialog.planname.text=data.plan_name
        dialog.plantime.text=data.plan_time
        dialog.plandata.text=data.data
        dialog.description.text=data.description
        dialog.price.text=data.price
      /*  dialogView.thanksregistration_btn.setOnClickListener {
            dialog.hide()
        }*/
    }

    fun showDialog(id:String) {
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

                Log.e("MONTHDATE","monthdate iss......."+"" + dayOfMonth + "-" + (monthOfYear+1) + "-" + year)

                viewModel.planlistview(name,mobno,flatno,buildingno,id,"CREDIT",date);

                dialog.hide()
            }, year, month, day)
            dpd.show()
            dpd.getDatePicker().setMinDate(c.timeInMillis)
        }
        yes.setOnClickListener {
            dialog.hide()
            viewModel.planlistview(name,mobno,flatno,buildingno,id,"CASH","");
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