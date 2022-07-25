package com.shalinibusinesssolutions.ecommerce.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.shalinibusinesssolutions.ecommerce.databinding.WishlistSinglerawBinding
import com.shalinibusinesssolutions.ecommerce.ui.activities.ui.RecDiff.ViewCartRecDiff
import com.shalinibusinesssolutions.ecommerce.ui.activities.ui.RecDiff.WishListRecDiff
import com.shalinibusinesssolutions.ecommerce.ui.activities.ui.wishlist.WishlistFragment
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.CartDetailList
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.WishListDetailList
import com.shalinibusinesssolutions.ecommerce.ui.utility.Fragmentinterface.ViewCardInterface
import com.shalinibusinesssolutions.ecommerce.ui.utility.Fragmentinterface.WishlistInterface
import java.lang.String

class Wishlistadapter(var wishlistInterface: WishlistInterface) : RecyclerView.Adapter<Wishlistadapter.ViewHolder>()
{
    var modelList = ArrayList<WishListDetailList>()

    class ViewHolder(var binding: WishlistSinglerawBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return  ViewHolder(WishlistSinglerawBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.productImage.load(modelList[position].Image)
        holder.binding.productName.text = modelList[position].name
        holder.binding.productPrice.text = "₹ " + String.format("% .2f", modelList[position].price).toString()
        holder.binding.productDescription.text=modelList[position].qty.toString() + " " + modelList[position].unit
        holder.binding.llDelete.setOnClickListener{

            wishlistInterface.gotoUserWishlistFragmentPage(
                modelList[position],
                "Delete",
                holder.binding,
                position

            )
        }

    }
    override fun getItemCount(): Int {
        return  modelList.size
    }

    fun Populistitem(newlist: ArrayList<WishListDetailList>) {
        val diffUtil = WishListRecDiff(modelList, newlist)
        val result = DiffUtil.calculateDiff(diffUtil)
        modelList = newlist
        result.dispatchUpdatesTo(this)
    }
}