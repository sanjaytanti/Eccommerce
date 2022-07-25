package com.shalinibusinesssolutions.ecommerce.ui.viewmodel

import androidx.lifecycle.ViewModel

class CartCountViewModel : ViewModel() {
    var qty = 0
    var price = 0.0f
//
//    init {
//      var   qty=0
//       var price=0.0f
//    }

    fun setPrice(qty1: Int, price1: Float): Float {
        price = qty1.toFloat() * price1
        return price
    }

    fun addCount(qty1: Int): Int {
        qty = qty1 + 1
        return qty
    }

    fun minusCount(qty1: Int): Int {
        qty = qty1 - 1
        return qty
    }

    fun resetCount(): Int {
        qty = 0
        return qty
    }

}