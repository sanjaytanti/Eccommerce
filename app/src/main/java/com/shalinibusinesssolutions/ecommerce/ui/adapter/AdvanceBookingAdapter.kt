package com.shalinibusinesssolutions.ecommerce.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shalinibusinesssolutions.ecommerce.databinding.AdvancebookingSinglerawBinding
import com.shalinibusinesssolutions.ecommerce.databinding.WishlistSinglerawBinding
import com.shalinibusinesssolutions.ecommerce.ui.activities.ui.RecDiff.AdvanceBookingRecDiff
import com.shalinibusinesssolutions.ecommerce.ui.activities.ui.RecDiff.WishListRecDiff
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.WishListDetailList
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.advanceOrderList
import com.shalinibusinesssolutions.ecommerce.ui.utility.Fragmentinterface.AdvanceBookingInterface

class AdvanceBookingAdapter(var advanceBookingInterface: AdvanceBookingInterface) : RecyclerView.Adapter<AdvanceBookingAdapter.viewHolder>(){

    var modelList = ArrayList<advanceOrderList>()

    class viewHolder(var binding: AdvancebookingSinglerawBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(AdvancebookingSinglerawBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {

         holder.binding.cratedAt.text=modelList[position].Date + " " + modelList[position].Time
         holder.binding.orderID.text=modelList[position].orderid.toString()
         holder.binding.PaymentID.text=modelList[position].transactionno
         holder.binding.PaymentAmt.text=modelList[position].advancepayment.toString()
         holder.binding.orderAmt.text=modelList[position].Ordervalue.toString()

        holder.binding.btnOrdPayment.setOnClickListener{
             advanceBookingInterface.advanceBookingInterface(modelList[position],"advanceBookingPayment",holder.binding,position)
        }



    }

    override fun getItemCount(): Int {
       return modelList.size
    }


    fun Populistitem(newlist: ArrayList<advanceOrderList>) {
        val diffUtil = AdvanceBookingRecDiff(modelList, newlist)
        val result = DiffUtil.calculateDiff(diffUtil)
        modelList = newlist
        result.dispatchUpdatesTo(this)
    }
}