package com.shalinibusinesssolutions.ecommerce.ui.activities.ui.RecDiff

import androidx.recyclerview.widget.DiffUtil
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.CartDetailList
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.WishListDetailList

class WishListRecDiff (var oldlist: ArrayList<WishListDetailList>,
                       var newlist: ArrayList<WishListDetailList>) : DiffUtil.Callback() {
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
        return oldlist[oldItemPosition].ID != newlist[newItemPosition].ID
    }

}