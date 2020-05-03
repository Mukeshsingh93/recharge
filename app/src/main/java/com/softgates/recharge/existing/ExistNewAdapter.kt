package com.softgates.recharge.existing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.softgates.recharge.databinding.ExistinguserlistBinding
import com.softgates.recharge.network.CustomerDetails

class ExistNewAdapter (private val onClickListener: OnClick ) :  ListAdapter<CustomerDetails, RecyclerView.ViewHolder>(completeListDiffCallback()) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ExistNewAdapter.ViewHolder).bind(getItem(position)!!,onClickListener,position)
        //     holder.bind(getItem(position)!!, onClickListener,position)
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): RecyclerView.ViewHolder {
        return ExistNewAdapter.ViewHolder.from(parent)
        // return  ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ExistinguserlistBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: CustomerDetails, clickListener: OnClick, position:Int) {
            binding.viewModel = item
            binding.index = position
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ExistinguserlistBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
        //eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjMsImlzcyI6Imh0dHBzOi8va2Fuei5hcHAvZGVtby9hcGkvdXNlci9sb2dpbiIsImlhdCI6MTU4MTQwODAzOCwiZXhwIjoxNTgxNDExNjM4LCJuYmYiOjE1ODE0MDgwMzgsImp0aSI6ImQ2alJZMk52bGI1VG85OVUifQ.12LqXBXPDfWwCsiuuT3gk4p43SyCWww8tNk9EqUIoDE
    }

    /*fun setState(state: ApiStatus) {
         this.state = state
         Log.e("CHANGEVIEW","ApiStatus state is...."+state)
         notifyItemChanged(super.getItemCount())
     }*/
}

class OnClick(val clickListener: (marsProperty: CustomerDetails, type:Int, index:Int) -> Unit) {
    fun onRenew(marsProperty: CustomerDetails, index:Int) = clickListener(marsProperty,1,index)
    fun onDesc(marsProperty: CustomerDetails, index:Int) = clickListener(marsProperty,2,index)
}

class completeListDiffCallback : DiffUtil.ItemCallback<CustomerDetails>() {

    override fun areItemsTheSame(oldItem: CustomerDetails, newItem: CustomerDetails): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: CustomerDetails, newItem: CustomerDetails): Boolean {
        return oldItem == newItem
    }
}