package com.shalinibusinesssolutions.ecommerce.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shalinibusinesssolutions.ecommerce.databinding.ReviewlayoutBinding
import com.shalinibusinesssolutions.ecommerce.ui.RecDiff.ReviewRecDiff
import com.shalinibusinesssolutions.ecommerce.ui.activities.ui.RecDiff.OrderdetailsRecDiff
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.RatingDetailsList
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.orderdetilslist

class ReviewDetailsAdapter: RecyclerView.Adapter<ReviewDetailsAdapter.ViewHolder>() {
    var modelList = ArrayList<RatingDetailsList>()

    class ViewHolder(var binding: ReviewlayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ReviewlayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.productReview.text=modelList[position].Review.toString()
    }

    override fun getItemCount(): Int {

        return  modelList.size
    }

    fun Populistitem(newlist: ArrayList<RatingDetailsList>) {
        val diffUtil = ReviewRecDiff(modelList, newlist)
        val result = DiffUtil.calculateDiff(diffUtil)
        modelList = newlist
        result.dispatchUpdatesTo(this)
    }
}