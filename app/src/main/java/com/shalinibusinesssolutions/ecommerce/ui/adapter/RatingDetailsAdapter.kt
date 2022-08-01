package com.shalinibusinesssolutions.ecommerce.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.shalinibusinesssolutions.ecommerce.databinding.CartlayoutBinding
import com.shalinibusinesssolutions.ecommerce.databinding.RatingdetailssinglerawBinding
import com.shalinibusinesssolutions.ecommerce.ui.activities.ui.RecDiff.OrderdetailsRecDiff
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.orderdetilslist
import java.lang.String

class RatingDetailsAdapter: RecyclerView.Adapter<RatingDetailsAdapter.ViewHolder>() {
    var modelList = ArrayList<orderdetilslist>()

    class  ViewHolder(var binding : RatingdetailssinglerawBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RatingdetailssinglerawBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        try {
            holder.binding.productName.text = modelList[position].name
            holder.binding.productPrice.text ="₹ " + String.format("% .2f",modelList[position].price).toString()
            holder.binding.productDescription.text = modelList[position].varqty
            holder.binding.productImage.load(modelList[position].Image)

        }
        catch (ex: Exception)
        {

        }





    }

    override fun getItemCount(): Int {
        return modelList.size
    }


    fun Populistitem(newlist: ArrayList<orderdetilslist>) {
        val diffUtil = OrderdetailsRecDiff(modelList, newlist)
        val result = DiffUtil.calculateDiff(diffUtil)
        modelList = newlist
        result.dispatchUpdatesTo(this)
    }
}