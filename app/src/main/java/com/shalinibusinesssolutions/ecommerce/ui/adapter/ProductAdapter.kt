package com.shalinibusinesssolutions.ecommerce.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.shalinibusinesssolutions.ecommerce.databinding.CartlayoutBinding
import com.shalinibusinesssolutions.ecommerce.ui.activities.ui.RecDiff.ProductRecDiff
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.ProductList
import com.shalinibusinesssolutions.ecommerce.ui.utility.Fragmentinterface.ProductInterFace
import java.lang.String

//class ProductAdapter(private val context: Context, private val modelList:ArrayList<ProductList>,
//                     var productInterFace: ProductInterFace
//) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

class ProductAdapter(var productInterFace: ProductInterFace) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    var modelList = ArrayList<ProductList>()

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
        var price = 0.0f

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

        holder.binding.llAdd.setOnClickListener {
            productInterFace.gotoProductFragmentPage(modelList[position], "Add", holder.binding)

        }

        holder.binding.llMinus.setOnClickListener {

            productInterFace.gotoProductFragmentPage(modelList[position], "Minus", holder.binding)
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


}