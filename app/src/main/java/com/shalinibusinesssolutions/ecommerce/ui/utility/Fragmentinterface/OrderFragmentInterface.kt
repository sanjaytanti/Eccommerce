package com.shalinibusinesssolutions.ecommerce.ui.utility.Fragmentinterface

import com.shalinibusinesssolutions.ecommerce.databinding.OrderSinglerawBinding
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.orderlist

interface OrderFragmentInterface {

    fun OrderFragmentInterface( orderorderlist: orderlist,stats:String,binding: OrderSinglerawBinding,position:Int)

}