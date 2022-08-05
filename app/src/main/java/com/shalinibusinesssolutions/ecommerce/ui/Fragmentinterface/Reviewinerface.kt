package com.shalinibusinesssolutions.ecommerce.ui.Fragmentinterface

import com.shalinibusinesssolutions.ecommerce.databinding.RatingdetailssinglerawBinding
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.orderdetilslist

interface Reviewinerface {
    fun reviewinerface(
        RevOrderDetailsList: orderdetilslist,
        binding: RatingdetailssinglerawBinding,
        position: Int

    )
}