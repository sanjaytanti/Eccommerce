package com.shalinibusinesssolutions.ecommerce.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shalinibusinesssolutions.ecommerce.databinding.CouponsinglerowlauoutBinding
import com.shalinibusinesssolutions.ecommerce.ui.activities.ui.RecDiff.CouponRecDiff
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.CouponDetailList
import com.shalinibusinesssolutions.ecommerce.ui.utility.Fragmentinterface.CouponInterface

class CouponAdapter(var couponInterface:CouponInterface) : RecyclerView.Adapter<CouponAdapter.viewHolder>(){
    var modelList = ArrayList<CouponDetailList>()
    var row_index = 0

    class viewHolder(var binding: CouponsinglerowlauoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(
            CouponsinglerowlauoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {

       holder.binding.couponCode.text=modelList[position].couponcode
        holder.binding.couponDetails.text=modelList[position].terms
        holder.binding.applyNow.setOnClickListener{
            couponInterface.gotoCouponFragmentPage(holder.binding,position,modelList[position])
        }
    }

    override fun getItemCount(): Int {
         return  modelList.size
    }

    fun Populistitem(newlist: ArrayList<CouponDetailList>) {
        val diffUtil = CouponRecDiff(modelList, newlist)
        val result = DiffUtil.calculateDiff(diffUtil)
        modelList = newlist
        result.dispatchUpdatesTo(this)
    }

}