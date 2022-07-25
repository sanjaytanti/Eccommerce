package com.shalinibusinesssolutions.ecommerce.ui.utility.Fragmentinterface

import com.shalinibusinesssolutions.ecommerce.databinding.ViewbasketsinglerowBinding
import com.shalinibusinesssolutions.ecommerce.databinding.WishlistSinglerawBinding
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.CartDetailList
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.WishListDetailList

interface WishlistInterface {

    fun gotoUserWishlistFragmentPage(
        wishListDetailList: WishListDetailList,
        status: String,
        binding: WishlistSinglerawBinding,
        position: Int
    )
}