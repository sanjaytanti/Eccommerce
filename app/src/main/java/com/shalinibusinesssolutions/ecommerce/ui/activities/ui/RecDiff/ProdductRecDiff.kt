package com.shalinibusinesssolutions.ecommerce.ui.activities.ui.RecDiff

import androidx.recyclerview.widget.DiffUtil
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.ProductList

class ProductRecDiff(var oldlist: ArrayList<ProductList>, var newlist: ArrayList<ProductList>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldlist.size
    }

    override fun getNewListSize(): Int {
        return newlist.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldlist[oldItemPosition] == newlist[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldlist[oldItemPosition].ID != newlist[oldItemPosition].ID
    }
}