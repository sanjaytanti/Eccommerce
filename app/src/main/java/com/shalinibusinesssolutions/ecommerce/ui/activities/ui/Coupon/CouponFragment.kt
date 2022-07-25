package com.shalinibusinesssolutions.ecommerce.ui.activities.ui.Coupon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.shalinibusinesssolutions.ecommerce.R
import com.shalinibusinesssolutions.ecommerce.databinding.CouponsinglerowlauoutBinding
import com.shalinibusinesssolutions.ecommerce.databinding.FragmentCouponBinding
import com.shalinibusinesssolutions.ecommerce.ui.adapter.CouponAdapter
import com.shalinibusinesssolutions.ecommerce.ui.api.RetrofitClient
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.CouponDetailList
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.GetCouponListResponse
import com.shalinibusinesssolutions.ecommerce.ui.utility.Fragmentinterface.CouponInterface
import com.shalinibusinesssolutions.ecommerce.ui.utility.nextFragment
import com.shalinibusinesssolutions.ecommerce.ui.utility.popFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CouponFragment : Fragment(),View.OnClickListener,CouponInterface {

    lateinit var binding: FragmentCouponBinding
    lateinit var couponAdapter: CouponAdapter
    var couponlist=ArrayList<CouponDetailList>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= FragmentCouponBinding.inflate(layoutInflater,container,false)
        binding.imgBack.setOnClickListener(this)
        binding.couponRecList?.layoutManager = LinearLayoutManager(requireContext())
   //     couponAdapter= CouponAdapter(this)
        couponAdapter=CouponAdapter(this)

        getcoupontData()


        return binding.root

    }

    private fun getcoupontData() {
        var call= RetrofitClient.apiservice.getCouponList()
        call.enqueue(object : Callback<GetCouponListResponse> {
            override fun onResponse(
                call: Call<GetCouponListResponse>,
                response: Response<GetCouponListResponse>
            ) {
                if(response.isSuccessful)
                {
                    if(response.body()?.status=="true")
                    {
                        couponlist=response.body()?.data!!
                        couponAdapter.Populistitem(couponlist)
                        binding.couponRecList?.adapter=couponAdapter
                        binding.progressbar?.visibility=View.GONE

                    }

                }
            }

            override fun onFailure(call: Call<GetCouponListResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "" + t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }


    override fun onClick(p0: View?) {
        when(p0?.id)
        {
            R.id.img_back->
            {
                requireActivity().popFragment()
            }
        }
    }

    override fun gotoCouponFragmentPage(
        binding: CouponsinglerowlauoutBinding,
        position: Int,
        couponDetailList: CouponDetailList
    ) {
        var couponbundle= bundleOf(
            "couponid" to couponDetailList.ID,
            "couponcode" to couponDetailList.couponcode,
            "couponDiscountAmount" to couponDetailList.DiscountAmount,
            "couponDiscountpercentage" to couponDetailList.Discountpercentage,
            "couponAmount" to couponDetailList.Amount,
            "couponterms" to couponDetailList.terms,
            "coupon" to "bycoupon")

        requireActivity().nextFragment(R.id.action_couponFragment_to_checkoutFragment,couponbundle)

    }

}