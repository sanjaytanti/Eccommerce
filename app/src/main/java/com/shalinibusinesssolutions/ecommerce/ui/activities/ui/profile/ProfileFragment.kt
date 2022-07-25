package com.shalinibusinesssolutions.ecommerce.ui.activities.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.shalinibusinesssolutions.ecommerce.R
import com.shalinibusinesssolutions.ecommerce.databinding.FragmentProfileBinding
import com.shalinibusinesssolutions.ecommerce.ui.utility.nextFragment
import com.shalinibusinesssolutions.ecommerce.ui.utility.popFragment


class ProfileFragment : Fragment(),View.OnClickListener {
    private lateinit var binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.advanceBooking.setOnClickListener(this)
        binding.orderList.setOnClickListener (this)
        binding.imgBack?.setOnClickListener(this)
        return binding.root
    }

    override fun onClick(p0: View?) {
        when(p0?.id)
        {
            R.id.advance_booking->
            {
                 requireActivity().nextFragment(R.id.action_profileFragment_to_advanceBookingSummaryFragment)
            }
            R.id.order_list->
            {
                requireActivity().nextFragment(R.id.action_profileFragment_to_orderFragment22)
            }
            R.id.img_back->
            {
                requireActivity().popFragment()

            }
        }
    }


}