package com.shalinibusinesssolutions.ecommerce.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shalinibusinesssolutions.ecommerce.databinding.AddresslayoutBinding
import com.shalinibusinesssolutions.ecommerce.ui.activities.ui.RecDiff.AddressRecDiff
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.AddressList
import com.shalinibusinesssolutions.ecommerce.ui.utility.Fragmentinterface.CheckoutInterface
import com.shalinibusinesssolutions.ecommerce.ui.utility.UserObject

class AddressAdapter(var checkoutInterface: CheckoutInterface) : RecyclerView.Adapter<AddressAdapter.viewHolder>()
{
    var modelList = ArrayList<AddressList>()
    var row_index = 0

    class viewHolder(var binding: AddresslayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
            return viewHolder( AddresslayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {

        var currentpostion=modelList[position]

        holder.binding.address.text=modelList[position].Name.toString() + ", " + modelList[position].MobNO + ", "+
        "Flat :" + modelList[position].Flat + " ," + "Area : " +  modelList[position].Area + "," + "Landmark :" +  modelList[position].Landmark + "," +
       "pincode : " +  modelList[position].Pincode+ ", " + modelList[position].state

        holder.binding.llDelete.setOnClickListener{
            checkoutInterface.gotoCheckoutFragmentPage(holder.binding,position,currentpostion,"Delete")

        }
        if (row_index == position) {
                holder.binding.radioCheck.isChecked=true
                UserObject.address_id=modelList[position].ID
        }
        else {
            holder.binding.radioCheck.isChecked=false

        }


        holder.binding.radioCheck.setOnClickListener{

            row_index = position
            notifyDataSetChanged()
            UserObject.address_id=modelList[row_index].ID

          //  Toast.makeText(holder.itemView.context, " address1" + modelList[row_index].ID.toString(), Toast.LENGTH_SHORT).show()

            // addressAdapter.notifyDataSetChanged()


        }

    }

    override fun getItemCount(): Int {

        return  modelList.size

    }

    fun Populistitem(newlist: ArrayList<AddressList>) {
        val diffUtil = AddressRecDiff(modelList, newlist)
        val result = DiffUtil.calculateDiff(diffUtil)
        modelList = newlist
        result.dispatchUpdatesTo(this)
    }

}