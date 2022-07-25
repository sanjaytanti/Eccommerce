package com.shalinibusinesssolutions.ecommerce.ui.utility.Fragmentinterface

import com.shalinibusinesssolutions.ecommerce.databinding.AdvancebookingSinglerawBinding
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.advanceOrderList


interface AdvanceBookingInterface {
    fun advanceBookingInterface(
        advanceOrderList: advanceOrderList,
        status: String,
        binding: AdvancebookingSinglerawBinding,
        position: Int
    )
}