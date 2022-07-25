package com.shalinibusinesssolutions.ecommerce.ui.activities.ui.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.shalinibusinesssolutions.ecommerce.R
import com.shalinibusinesssolutions.ecommerce.databinding.FragmentOrderBinding
import com.shalinibusinesssolutions.ecommerce.databinding.OrderSinglerawBinding
import com.shalinibusinesssolutions.ecommerce.ui.adapter.AdvanceBookingAdapter
import com.shalinibusinesssolutions.ecommerce.ui.adapter.OrderAdapter
import com.shalinibusinesssolutions.ecommerce.ui.api.RetrofitClient
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.advanceOrderList
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.getorderResponse
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.orderlist
import com.shalinibusinesssolutions.ecommerce.ui.utility.Fragmentinterface.OrderFragmentInterface
import com.shalinibusinesssolutions.ecommerce.ui.utility.SharedPreferencesUtil
import com.shalinibusinesssolutions.ecommerce.ui.utility.UserObject
import com.shalinibusinesssolutions.ecommerce.ui.utility.nextFragment
import com.shalinibusinesssolutions.ecommerce.ui.utility.popFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderFragment : Fragment(),View.OnClickListener,OrderFragmentInterface {

    lateinit var binding : FragmentOrderBinding
    lateinit var orderAdapter: OrderAdapter
    var orderlist=ArrayList<orderlist>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= FragmentOrderBinding.inflate(layoutInflater,container,false)
        binding.recOrder.layoutManager= LinearLayoutManager(requireContext())
        orderAdapter= OrderAdapter(this)
        binding.progressbar.visibility=View.VISIBLE
        binding.imgBack.setOnClickListener(this)
        orderlistData()

        return  binding.root
    }

    private fun orderlistData() {

        var  userid = SharedPreferencesUtil().getUserId(requireContext()).toString().toInt()
        val call=RetrofitClient.apiservice.getorderlist(userid)
          call.enqueue(object : Callback<getorderResponse>{
              override fun onResponse(
                  call: Call<getorderResponse>,
                  response: Response<getorderResponse>
              ) {

                  if (response.isSuccessful)
                  {

                      if (response.body()?.status=="true")
                      {
                          orderlist=response.body()?.data!!
                          orderAdapter.Populistitem(orderlist)
                          binding.recOrder?.adapter=orderAdapter
                          binding.progressbar?.visibility=View.GONE

                      }
                  }

              }

              override fun onFailure(call: Call<getorderResponse>, t: Throwable) {
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


        requireActivity().nextFragment(R.id.action_orderFragment2_to_orderDetailsFragment)
    }


}