package com.shalinibusinesssolutions.ecommerce.ui.activities.ui.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.shalinibusinesssolutions.ecommerce.R
import com.shalinibusinesssolutions.ecommerce.databinding.FragmentOrderDetailsBinding
import com.shalinibusinesssolutions.ecommerce.ui.adapter.OrderAdapter
import com.shalinibusinesssolutions.ecommerce.ui.adapter.OrderDetailsAdapter
import com.shalinibusinesssolutions.ecommerce.ui.api.RetrofitClient
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.*
import com.shalinibusinesssolutions.ecommerce.ui.utility.SharedPreferencesUtil
import com.shalinibusinesssolutions.ecommerce.ui.utility.UserObject
import com.shalinibusinesssolutions.ecommerce.ui.utility.popFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

lateinit var binding:FragmentOrderDetailsBinding
lateinit var orderDetailsAdapter: OrderDetailsAdapter
var orderlist=ArrayList<orderdetilslist>()
 var addresslist= ArrayList<AddressList>()



class OrderDetailsFragment : Fragment(),View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentOrderDetailsBinding.inflate(layoutInflater,container,false)


        binding.recOrder.layoutManager= LinearLayoutManager(requireContext())
        orderDetailsAdapter= OrderDetailsAdapter()
        binding.progressbar.visibility=View.VISIBLE
        binding.imgBack.setOnClickListener(this)
        getAddressData()
        orderlistData()
        binding.orderID.text= "Order No :" + UserObject.orderProduct_orderid.toString()
        binding.orderOn.text="Order Date :" + UserObject.orderdate
        binding.orderStatus.text="Not Delivered"

        binding.total.text=UserObject.orderProduct_ordervalue.toString()
        binding.netTotal.text=UserObject.orderProduct_ordervalue.toString()
        binding.totalPay.text=UserObject.orderProduct_ordervalue.toString()

        return binding.root
    }


    private fun getAddressData() {

        var  userid = SharedPreferencesUtil().getUserId(requireContext()).toString().toInt()

        var call = RetrofitClient.apiservice.getUserAddressbyaddressid(userid,UserObject.orderProduct_addreessid)
        call.enqueue(object : Callback<AddressResponse> {
            override fun onResponse(
                call: Call<AddressResponse>,
                response: Response<AddressResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body()?.status == "true") {
                        var position=0
                        addresslist = response.body()?.data!!
                        binding.address.text=addresslist[position].Name.toString() + ", " + addresslist[position].MobNO + ", "+
                                "Flat :" + addresslist[position].Flat + " ," + "Area : " +  addresslist[position].Area + "," + "Landmark :" +  addresslist[position].Landmark + "," +
                                "pincode : " +  addresslist[position].Pincode+ ", " + addresslist[position].state


                    } else {
                        Toast.makeText(
                            requireContext(),
                            "" + response.body()!!.message.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.progressbar.visibility = View.GONE

                    }
                }
            }

            override fun onFailure(call: Call<AddressResponse>, t: Throwable) {

            }

        })

    }

    private fun orderlistData() {

        var  userid = SharedPreferencesUtil().getUserId(requireContext()).toString().toInt()

        val call =RetrofitClient.apiservice.getorderproductdetails(userid,UserObject.orderProduct_orderid)

           call.enqueue(object : Callback<getOrderDetailsResponse>{
               override fun onResponse(
                   call: Call<getOrderDetailsResponse>,
                   response: Response<getOrderDetailsResponse>
               ) {
                   if (response.isSuccessful)
                   {

                       if (response.body()?.status=="true")
                       {
                           orderlist=response.body()?.data!!

                           orderDetailsAdapter.Populistitem(orderlist)
                           binding.recOrder?.adapter= orderDetailsAdapter
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