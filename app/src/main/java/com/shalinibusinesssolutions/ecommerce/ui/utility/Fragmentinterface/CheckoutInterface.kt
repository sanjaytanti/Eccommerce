package com.shalinibusinesssolutions.ecommerce.ui.utility.Fragmentinterface

import com.shalinibusinesssolutions.ecommerce.databinding.AddresslayoutBinding
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.AddressList

interface CheckoutInterface {
    fun gotoCheckoutFragmentPage( binding: AddresslayoutBinding,position: Int,addressList: AddressList ,flag:String)

}