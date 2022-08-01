package com.shalinibusinesssolutions.ecommerce.ui.Fragment.Rating

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.shalinibusinesssolutions.ecommerce.R
import com.shalinibusinesssolutions.ecommerce.databinding.FragmentRatingBinding
import com.shalinibusinesssolutions.ecommerce.ui.activities.ui.order.orderDetailsAdapter
import com.shalinibusinesssolutions.ecommerce.ui.adapter.OrderDetailsAdapter
import com.shalinibusinesssolutions.ecommerce.ui.adapter.RatingDetailsAdapter
import com.shalinibusinesssolutions.ecommerce.ui.api.RetrofitClient
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.getOrderDetailsResponse
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.orderdetilslist
import com.shalinibusinesssolutions.ecommerce.ui.utility.SharedPreferencesUtil
import com.shalinibusinesssolutions.ecommerce.ui.utility.UserObject
import com.shalinibusinesssolutions.ecommerce.ui.utility.popFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RatingDetailsFragment : Fragment(),View.OnClickListener {

    lateinit var binding : FragmentRatingBinding
    lateinit var ratingDetailsAdapter: RatingDetailsAdapter
    var orderlist=ArrayList<orderdetilslist>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentRatingBinding.inflate(layoutInflater,container,false)
        binding.recOrder.layoutManager= LinearLayoutManager(requireContext())
        ratingDetailsAdapter= RatingDetailsAdapter()
        binding.progressbar.visibility=View.VISIBLE
        binding.imgBack.setOnClickListener(this)
        getOrderListData()
        return binding.root
    }

    private fun getOrderListData() {

        var  userid = SharedPreferencesUtil().getUserId(requireContext()).toString().toInt()

        val call = RetrofitClient.apiservice.getRatingDetails(userid, UserObject.orderProduct_orderid)

        call.enqueue(object : Callback<getOrderDetailsResponse> {
            override fun onResponse(
                call: Call<getOrderDetailsResponse>,
                response: Response<getOrderDetailsResponse>
            ) {
                if (response.isSuccessful)
                {

                    if (response.body()?.status=="true")
                    {
                        orderlist =response.body()?.data!!

                        ratingDetailsAdapter.Populistitem(orderlist)
                        binding.recOrder?.adapter= ratingDetailsAdapter
                        binding.progressbar?.visibility=View.GONE

                    }
                }

            }

            override fun onFailure(call: Call<getOrderDetailsResponse>, t: Throwable) {

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


}