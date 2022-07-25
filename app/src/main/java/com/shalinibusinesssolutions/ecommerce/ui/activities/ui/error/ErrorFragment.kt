package com.shalinibusinesssolutions.ecommerce.ui.activities.ui.error

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shalinibusinesssolutions.ecommerce.R
import com.shalinibusinesssolutions.ecommerce.databinding.FragmentErrorBinding
import com.shalinibusinesssolutions.ecommerce.databinding.FragmentThankyouBinding
import com.shalinibusinesssolutions.ecommerce.ui.utility.nextFragment

class ErrorFragment : Fragment(),View.OnClickListener {

    lateinit var binding:FragmentErrorBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {


        binding= FragmentErrorBinding.inflate(layoutInflater,container,false)

        binding.llContinue.setOnClickListener(this)
        return  binding.root

    }

    override fun onClick(p0: View?) {
        when(p0?.id)
        {
            R.id.ll_continue->
            {
                requireActivity().nextFragment(R.id.action_errorFragment_to_homeFragment)

            }

        }
    }


}