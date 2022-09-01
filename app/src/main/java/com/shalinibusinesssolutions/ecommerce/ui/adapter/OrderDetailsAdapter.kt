package com.shalinibusinesssolutions.ecommerce.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shalinibusinesssolutions.ecommerce.databinding.OrderSinglerawBinding
import com.shalinibusinesssolutions.ecommerce.databinding.OrderdetailsSinglerawBinding
import com.shalinibusinesssolutions.ecommerce.ui.activities.ui.RecDiff.OrderRecDiff
import com.shalinibusinesssolutions.ecommerce.ui.activities.ui.RecDiff.OrderdetailsRecDiff
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.orderdetilslist
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.orderlist

class OrderDetailsAdapter  : RecyclerView.Adapter<OrderDetailsAdapter.viewHolder>(){
    var modelList = ArrayList<orderdetilslist>()

    class viewHolder(var binding: OrderdetailsSinglerawBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(OrderdetailsSinglerawBinding.inflate( LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.binding.tvProductname.text=modelList[position].name
        holder.binding.tvVariationqty.text=modelList[position].varqty
        holder.binding.tvMrp.text=modelList[position].mrp.toString()
        holder.binding.tvDiscount.text=modelList[position].discount.toString()
        holder.binding.tvPrice.text=modelList[position].price.toString()

    }

    override fun getItemCount(): Int {
             return  modelList.size
    }

    fun Populistitem(newlist: ArrayList<orderdetilslist>) {
        val diffUtil = OrderdetailsRecDiff(modelList, newlist)
        val result = DiffUtil.calculateDiff(diffUtil)
        modelList = newlist
        result.dispatchUpdatesTo(this)
    }


}