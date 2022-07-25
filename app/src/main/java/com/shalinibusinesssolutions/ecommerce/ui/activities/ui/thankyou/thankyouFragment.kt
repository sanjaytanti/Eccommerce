package com.shalinibusinesssolutions.ecommerce.ui.activities.ui.thankyou

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shalinibusinesssolutions.ecommerce.R
import com.shalinibusinesssolutions.ecommerce.databinding.FragmentThankyouBinding
import com.shalinibusinesssolutions.ecommerce.ui.utility.nextFragment

class thankyouFragment : Fragment(),View.OnClickListener {

    lateinit var binding : FragmentThankyouBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= FragmentThankyouBinding.inflate(layoutInflater,container,false)
        binding.llContinue.setOnClickListener(this)
        return  binding.root



    }

    override fun onClick(p0: View?) {
        when(p0?.id)
        {
            R.id.ll_continue ->
            {
                requireActivity().nextFragment(R.id.action_thankyouFragment_to_homeFragment)
            }
        }
    }


}