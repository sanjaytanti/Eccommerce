package com.shalinibusinesssolutions.ecommerce.ui.activities.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.shalinibusinesssolutions.ecommerce.databinding.FragmentCartBinding

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)

//        val toolbar =
//            requireActivity().findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
//        toolbar.setNavigationIcon(R.drawable.ic_back)
//        toolbar.setNavigationOnClickListener {
//            //   findNavController().navigate(R.id.action_productFragment_to_homeFragment)
        // }
        return binding.root
    }


}