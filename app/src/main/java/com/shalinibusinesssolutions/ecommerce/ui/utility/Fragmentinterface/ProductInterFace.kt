package com.shalinibusinesssolutions.ecommerce.ui.utility.Fragmentinterface

import com.shalinibusinesssolutions.ecommerce.databinding.CartlayoutBinding
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.ProductList

interface ProductInterFace {

    fun gotoProductFragmentPage(
        productList: ProductList,
        status: String,
        binding: CartlayoutBinding
    )

    fun gotoProductPage(productList: ProductList)

}