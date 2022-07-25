package com.shalinibusinesssolutions.ecommerce.ui.utility.Fragmentinterface

import com.shalinibusinesssolutions.ecommerce.databinding.ViewbasketsinglerowBinding
import com.shalinibusinesssolutions.ecommerce.databinding.WishlistSinglerawBinding
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.CartDetailList

interface ViewCardInterface {
    suspend fun gotoViewBasketFragmentPage(
        cartDetailList: CartDetailList,
        status: String,
        binding: ViewbasketsinglerowBinding,
        position: Int
    )



}