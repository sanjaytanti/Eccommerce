package com.shalinibusinesssolutions.ecommerce.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import coil.load
import com.shalinibusinesssolutions.ecommerce.databinding.CustomLayoutBinding
import com.shalinibusinesssolutions.ecommerce.ui.activities.ui.RecDiff.BannerRecDiff
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.BannerList

class BannerAdapterNew(
    var context: Context,
    var oldlist: MutableList<BannerList>,
    var viewPager2: ViewPager2
) : RecyclerView.Adapter<BannerAdapterNew.BannerViewHolder>() {

    class BannerViewHolder(var binding: CustomLayoutBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {

        return BannerViewHolder(
            CustomLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    private val runnable = Runnable {
        oldlist.addAll(oldlist)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        //     Picasso.with(context).load(RetrofitClient.BASE_URL + "BannerImages/" + oldlist[position].image).into(holder.binding.imageView)


        holder.binding.imageView.load(oldlist[position].image)

        if (position == oldlist.size - 2) {
            viewPager2.post(runnable)
        }

    }

    override fun getItemCount(): Int {
        return oldlist.size
    }

    fun getarraylist(): MutableList<BannerList> {
        return oldlist

    }

    fun Populistitem(newlist: MutableList<BannerList>) {
        val diffUtil = BannerRecDiff(oldlist, newlist)
        val result = DiffUtil.calculateDiff(diffUtil)
        oldlist = newlist
        result.dispatchUpdatesTo(this)
    }

    //    init {
//        this.context = context
//        this.oldlist = oldlist
//        this.viewPager2 = viewPager2
//    }


}