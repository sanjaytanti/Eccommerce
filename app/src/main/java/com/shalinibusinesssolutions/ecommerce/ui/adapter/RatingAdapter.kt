package com.shalinibusinesssolutions.ecommerce.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shalinibusinesssolutions.ecommerce.databinding.OrderSinglerawBinding
import com.shalinibusinesssolutions.ecommerce.ui.activities.ui.RecDiff.OrderRecDiff
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.orderlist
import com.shalinibusinesssolutions.ecommerce.ui.utility.Fragmentinterface.OrderFragmentInterface

class RatingAdapter(var orderFragmentInterface: OrderFragmentInterface) : RecyclerView.Adapter<RatingAdapter.ViewHolder>(){
    var modelList = ArrayList<orderlist>()

    class ViewHolder(var binding: OrderSinglerawBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(OrderSinglerawBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.cratedAt.text=modelList[position].Date + " " + modelList[position].Time
        holder.binding.orderID.text=modelList[position].orderid.toString()
        holder.binding.PaymentID.text=modelList[position].status
        // holder.binding.PaymentAmt.text=modelList[position].advancepayment.toString()
        holder.binding.orderAmt.text=modelList[position].Ordervalue.toString()
        holder.binding.llMain.setOnClickListener{

            orderFragmentInterface.OrderFragmentInterface(modelList[position],"orderlist",holder.binding,position)
        }

    }

    override fun getItemCount(): Int {
        return modelList.size
    }
    fun Populistitem(newlist: ArrayList<orderlist>) {
        val diffUtil = OrderRecDiff(modelList, newlist)
        val result = DiffUtil.calculateDiff(diffUtil)
        modelList = newlist
        result.dispatchUpdatesTo(this)
    }
}