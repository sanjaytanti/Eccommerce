package com.shalinibusinesssolutions.ecommerce.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.shalinibusinesssolutions.ecommerce.databinding.CartlayoutBinding
import com.shalinibusinesssolutions.ecommerce.databinding.SearchproductlayoutsiglerawBinding
import com.shalinibusinesssolutions.ecommerce.ui.activities.ui.RecDiff.ProductRecDiff
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.ProductList
import com.shalinibusinesssolutions.ecommerce.ui.utility.Fragmentinterface.ProductInterFace
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.String
import java.util.*
import kotlin.collections.ArrayList

class SearchProductAdapter(var productInterFace: ProductInterFace): RecyclerView.Adapter<SearchProductAdapter.Viewholder>(),Filterable {

    var modelList = ArrayList<ProductList>()
    public var backupdata=ArrayList<ProductList>()
    var context: Context? = null


    class  Viewholder(var binding: SearchproductlayoutsiglerawBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
     return Viewholder(SearchproductlayoutsiglerawBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        var price = 0.0f
        context=holder.itemView.context
        holder.binding.productName.text = modelList[position].name
        holder.binding.productPrice.text =
            "₹ " + String.format("% .2f", modelList[position].mrp).toString()
        holder.binding.productQty.text =
            modelList[position].qty.toString() + " " + modelList[position].unit
        holder.binding.productImage.load(modelList[position].Image)
        price =
            holder.binding.qty.text.toString().toInt().toFloat() * modelList[position].mrp.toFloat()
        holder.binding.stock.text = "₹ " + String.format("% .2f", price).toString()
        holder.binding.productDescription.text = modelList[position].description

        holder.binding.productImage.setOnClickListener {
            productInterFace.gotoProductPage(modelList[position])
        }

    }

    override fun getItemCount(): Int {
        return modelList.size
    }

    fun Populistitem(newlist: ArrayList<ProductList>) {
        val diffUtil = ProductRecDiff(modelList, newlist)
        val result = DiffUtil.calculateDiff(diffUtil)
        modelList = newlist
        result.dispatchUpdatesTo(this)
    }



    override fun getFilter(): Filter {
        return  filter1
    }


    var filter1: Filter = object : Filter() {
        override fun performFiltering(keyward: CharSequence): FilterResults {
            //       Toast.makeText(context, "hi", Toast.LENGTH_SHORT).show()
            val filterdata = ArrayList<ProductList>()

            if (keyward.toString().isEmpty()) {
                //filterdata.addAll(backupdata)
            } else {
                for (model in backupdata) {
                    if (model.name.toString().toLowerCase(Locale.ROOT).contains(keyward.toString().toLowerCase(Locale.ROOT))) {
                        filterdata.add(model)
                    }
                }
            }
            val filterResults = FilterResults()
            filterResults.values = filterdata
            return filterResults
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
               modelList.clear()
               modelList.addAll((results.values) as ArrayList<ProductList> )
               notifyDataSetChanged()
        }
    }

}

