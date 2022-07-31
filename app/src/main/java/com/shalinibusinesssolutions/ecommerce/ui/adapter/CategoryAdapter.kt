package com.shalinibusinesssolutions.ecommerce.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.shalinibusinesssolutions.ecommerce.databinding.CategoryLayoutBinding
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.CategoryList
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.DashBoardResponse
import com.shalinibusinesssolutions.ecommerce.ui.utility.Fragmentinterface.CategoryInterface
import retrofit2.Callback

class CategoryAdapter(
    private val context: Context,
    private val modelList: ArrayList<CategoryList>,
    var categoryInterface: CategoryInterface
) :

    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: CategoryLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setDataToView(position: Int, holder: ViewHolder) {
            var currentItem = modelList[position]

            binding.categoryTv.text = modelList[position].name
            // UrlImageViewHelper.setUrlDrawable(binding.categoryImage,RetrofitClient.BASE_URL + "CategoryImages/" + modelList[position].image)
            // Picasso.with(context).load(RetrofitClient.BASE_URL + "CategoryImages/" + modelList[position].image).into(binding.categoryImage)

            binding.categoryImage.load(modelList[position].image)
            binding.categoryImage.setOnClickListener {
                categoryInterface.gotoCategoryFragmentPage(modelList[position])

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {

        return ViewHolder(
            CategoryLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {

        holder.setDataToView(position, holder)
    }

    override fun getItemCount(): Int {
        return modelList.size
    }


}