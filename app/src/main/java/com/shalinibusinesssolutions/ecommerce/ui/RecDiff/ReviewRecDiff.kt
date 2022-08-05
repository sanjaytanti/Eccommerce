package com.shalinibusinesssolutions.ecommerce.ui.RecDiff

import androidx.recyclerview.widget.DiffUtil
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.AddressList
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.RatingDetailsList

class ReviewRecDiff(var oldlist:ArrayList<RatingDetailsList>,var newlist: ArrayList<RatingDetailsList>) :DiffUtil.Callback()
{
    override fun getOldListSize(): Int {
        return oldlist.size
    }

    override fun getNewListSize(): Int {
       return  newlist.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldlist[oldItemPosition]==newlist[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldlist[oldItemPosition]!=newlist[newItemPosition]
    }


}