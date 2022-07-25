package com.shalinibusinesssolutions.ecommerce.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.makeramen.roundedimageview.RoundedImageView
import com.shalinibusinesssolutions.ecommerce.R
import com.shalinibusinesssolutions.ecommerce.ui.api.RetrofitClient
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.BannerList
import com.squareup.picasso.Picasso


class BannerAdapter(
    context: Context,
    sliderItems: MutableList<BannerList>,
    viewPager2: ViewPager2
) : RecyclerView.Adapter<BannerAdapter.ViewHolder>() {
    private val context: Context
    private val viewPager2: ViewPager2
    private val sliderItems: List<BannerList>

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: RoundedImageView

        fun setImage(sliderItem: BannerList?) {
            // imageView.setImageResource(sliderItem.getBannerImage());
        }

        init {
            imageView = itemView.findViewById(R.id.imageView)
        }
    }

    private val runnable = Runnable {
        sliderItems.addAll(sliderItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.custom_layout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // holder.setImage(sliderItems.get(position));
        Picasso.with(context)
            .load(RetrofitClient.BASE_URL + "BannerImages/" + sliderItems[position].image)
            .into(holder.imageView)

        //    holder.binding.EmpImage.load(SimpleApi.BASE_URL + "image/" + oldlist[position].image)
        if (position == sliderItems.size - 2) {
            viewPager2.post(runnable)
        }
    }

    override fun getItemCount(): Int {
        return sliderItems.size
    }

    //  private Integer [] images = {R.drawable.bannerone,R.drawable.bannertwo,R.drawable.bannerthree,R.drawable.bannerfour};
    init {
        this.context = context
        this.sliderItems = sliderItems
        this.viewPager2 = viewPager2
    }
}