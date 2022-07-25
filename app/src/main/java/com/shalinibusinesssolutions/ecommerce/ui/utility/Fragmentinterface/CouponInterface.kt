package com.shalinibusinesssolutions.ecommerce.ui.utility.Fragmentinterface

import com.shalinibusinesssolutions.ecommerce.databinding.CouponsinglerowlauoutBinding
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.CouponDetailList


interface CouponInterface {

    fun gotoCouponFragmentPage(binding: CouponsinglerowlauoutBinding, position:Int, couponDetailList: CouponDetailList)
}