package com.shalinibusinesssolutions.ecommerce.ui.Fragment.Rating

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.shalinibusinesssolutions.ecommerce.R
import com.shalinibusinesssolutions.ecommerce.databinding.FragmentOrderBinding
import com.shalinibusinesssolutions.ecommerce.databinding.FragmentRatingBinding
import com.shalinibusinesssolutions.ecommerce.databinding.OrderSinglerawBinding
import com.shalinibusinesssolutions.ecommerce.ui.adapter.OrderAdapter
import com.shalinibusinesssolutions.ecommerce.ui.adapter.RatingAdapter
import com.shalinibusinesssolutions.ecommerce.ui.api.RetrofitClient
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.getorderResponse
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.orderlist
import com.shalinibusinesssolutions.ecommerce.ui.utility.Fragmentinterface.OrderFragmentInterface
import com.shalinibusinesssolutions.ecommerce.ui.utility.SharedPreferencesUtil
import com.shalinibusinesssolutions.ecommerce.ui.utility.UserObject
import com.shalinibusinesssolutions.ecommerce.ui.utility.popFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RatingFragment : Fragment(),OrderFragmentInterface,View.OnClickListener {

    lateinit var binding :FragmentRatingBinding
    lateinit var ratingAdapter: RatingAdapter
    var orderlist=ArrayList<orderlist>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentRatingBinding.inflate(layoutInflater,container,false)

        binding.recOrder.layoutManager= LinearLayoutManager(requireContext())
        ratingAdapter= RatingAdapter(this)
        binding.progressbar.visibility=View.VISIBLE
        binding.imgBack.setOnClickListener(this)
        getorderlist()

        return binding.root
    }


    fun getorderlist()
    {
        var  userid = SharedPreferencesUtil().getUserId(requireContext()).toString().toInt()
        val call= RetrofitClient.apiservice.getDeliveredOrderlist(userid)
        call.enqueue(object : Callback<getorderResponse> {
            override fun onResponse(
                call: Call<getorderResponse>,
                response: Response<getorderResponse>
            ) {

                if (response.isSuccessful)
                {

                    if (response.body()?.status=="true")
                    {
                        orderlist=response.body()?.data!!
                        ratingAdapter.Populistitem(orderlist)
                        binding.recOrder?.adapter=ratingAdapter
                        binding.progressbar?.visibility=View.GONE

                    }
                }

            }

            override fun onFailure(call: Call<getorderResponse>, t: Throwable) {
            }

        })

    }

    override fun OrderFragmentInterface(
        orderorderlist: orderlist,
        stats: String,
        binding: OrderSinglerawBinding,
        position: Int
    ) {
        UserObject.orderProduct_orderid=orderorderlist.orderid
        UserObject.orderdate=orderorderlist.Date.toString()
        UserObject.orderProduct_ordervalue=orderorderlist.Ordervalue
        UserObject.orderProduct_addreessid=orderorderlist.addressid
        findNavController().navigate(R.id.action_ratingFragment_to_ratingDetailsFragment)

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