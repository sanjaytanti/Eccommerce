package com.shalinibusinesssolutions.ecommerce.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shalinibusinesssolutions.ecommerce.databinding.CartlayoutBinding
import com.shalinibusinesssolutions.ecommerce.ui.activities.ui.RecDiff.ProductDetailsRecDiff
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.ProductDetailList
import com.shalinibusinesssolutions.ecommerce.ui.utility.Fragmentinterface.ProductInterFace

class ProductDetailsAdapter(var productInterFace: ProductInterFace) :
    RecyclerView.Adapter<ProductDetailsAdapter.ViewHolder>() {
    var modelList = ArrayList<ProductDetailList>()

    class ViewHolder(var binding: CartlayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            CartlayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return modelList.size
    }

    fun Populistitem(newlist: ArrayList<ProductDetailList>) {
        val diffUtil = ProductDetailsRecDiff(modelList, newlist)
        val result = DiffUtil.calculateDiff(diffUtil)
        modelList = newlist
        result.dispatchUpdatesTo(this)
    }

}