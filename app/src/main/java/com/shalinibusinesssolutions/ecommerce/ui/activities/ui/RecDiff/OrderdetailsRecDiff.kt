package com.shalinibusinesssolutions.ecommerce.ui.activities.ui.RecDiff

import androidx.recyclerview.widget.DiffUtil
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.ProductDetailList
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.orderdetilslist
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.orderlist

class OrderdetailsRecDiff(var oldlist :ArrayList<orderdetilslist>, var newlist :ArrayList<orderdetilslist>) :DiffUtil.Callback()
{
    override fun getOldListSize(): Int {
        return  oldlist.size
    }

    override fun getNewListSize(): Int {
       return newlist.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldlist[oldItemPosition] == newlist[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldlist[oldItemPosition].orderid != newlist[newItemPosition].orderid
    }
}
