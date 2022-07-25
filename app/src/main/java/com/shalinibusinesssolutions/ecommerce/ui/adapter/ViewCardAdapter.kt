package com.shalinibusinesssolutions.ecommerce.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.shalinibusinesssolutions.ecommerce.databinding.ViewbasketsinglerowBinding
import com.shalinibusinesssolutions.ecommerce.ui.activities.ui.RecDiff.ViewCartRecDiff
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.CartDetailList
import com.shalinibusinesssolutions.ecommerce.ui.utility.Fragmentinterface.ViewCardInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.String

class ViewCardAdapter(var viewCardInterface: ViewCardInterface) :
    RecyclerView.Adapter<ViewCardAdapter.ViewHolder>() {
    var modelList = ArrayList<CartDetailList>()

    class ViewHolder(var binding: ViewbasketsinglerowBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ViewbasketsinglerowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.productImage.load(modelList[position].Image)
        holder.binding.productName.text = modelList[position].name
        holder.binding.productPrice.text =
            "₹ " + String.format("% .2f", modelList[position].price).toString()
        holder.binding.qty.text =
            modelList[position].qty.toString() //+ " " +  modelList[position].unit
        holder.binding.productDescription.text = modelList[position].varqty

        holder.binding.llAdd.setOnClickListener {

            CoroutineScope(Dispatchers.Main).launch {
                viewCardInterface.gotoViewBasketFragmentPage(
                    modelList[position],
                    "Add",
                    holder.binding,
                    position
                )
            }
            // addToCard()

        }
        holder.binding.llMinus
            .setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch {
                    viewCardInterface.gotoViewBasketFragmentPage(
                        modelList[position],
                        "Minus",
                        holder.binding,
                        position
                    )
                }


            }

        holder.binding.llDelete.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                viewCardInterface.gotoViewBasketFragmentPage(
                    modelList[position],
                    "Delete",
                    holder.binding,
                    position
                )

            }


        }
    }


    override fun getItemCount(): Int {
        return modelList.size
    }

    fun Populistitem(newlist: ArrayList<CartDetailList>) {
        val diffUtil = ViewCartRecDiff(modelList, newlist)
        val result = DiffUtil.calculateDiff(diffUtil)
        modelList = newlist
        result.dispatchUpdatesTo(this)
    }

}