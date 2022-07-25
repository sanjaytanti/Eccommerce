package com.shalinibusinesssolutions.ecommerce.ui.activities.ui.wishlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.shalinibusinesssolutions.ecommerce.R
import com.shalinibusinesssolutions.ecommerce.databinding.FragmentWishlistBinding
import com.shalinibusinesssolutions.ecommerce.databinding.ViewbasketsinglerowBinding
import com.shalinibusinesssolutions.ecommerce.databinding.WishlistSinglerawBinding
import com.shalinibusinesssolutions.ecommerce.ui.adapter.ViewCardAdapter
import com.shalinibusinesssolutions.ecommerce.ui.adapter.Wishlistadapter
import com.shalinibusinesssolutions.ecommerce.ui.api.RetrofitClient
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.AddToCartResponse
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.CartDetailList
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.GetWishlistResponse
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.WishListDetailList
import com.shalinibusinesssolutions.ecommerce.ui.utility.Fragmentinterface.WishlistInterface
import com.shalinibusinesssolutions.ecommerce.ui.utility.SharedPreferencesUtil
import com.shalinibusinesssolutions.ecommerce.ui.utility.popFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class WishlistFragment : Fragment(),View.OnClickListener,WishlistInterface {

    private lateinit var binding: FragmentWishlistBinding
    lateinit var wishlistadapter: Wishlistadapter
    var listwishlist=ArrayList<WishListDetailList>()
    var userid = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWishlistBinding.inflate(layoutInflater, container, false)
        userid = SharedPreferencesUtil().getUserId(requireContext()).toString().toInt()

        binding.recWishlist?.layoutManager = LinearLayoutManager(requireContext())
        wishlistadapter= Wishlistadapter(this)
        binding.imgBack?.setOnClickListener(this)
        getwishlistData()
        return binding.root

    }


    private fun getwishlistData() {
       var call=RetrofitClient.apiservice.getWishcard(userid)
        call.enqueue(object : Callback<GetWishlistResponse>{
            override fun onResponse(
                call: Call<GetWishlistResponse>,
                response: Response<GetWishlistResponse>
            ) {
               if(response.isSuccessful)
               {
                   if(response.body()?.status=="true")
                   {
                       listwishlist=response.body()?.data!!
                       wishlistadapter.Populistitem(listwishlist)
                       binding.recWishlist?.adapter=wishlistadapter
                       binding.progressbar?.visibility=View.GONE

                   }

               }
            }

            override fun onFailure(call: Call<GetWishlistResponse>, t: Throwable) {
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

    override fun gotoUserWishlistFragmentPage(
        wishListDetailList: WishListDetailList,
        status: String,
        binding: WishlistSinglerawBinding,
        position: Int
    ) {
            var call =RetrofitClient.apiservice.deleteWishList(wishListDetailList.ID,userid)
           call.enqueue(object :Callback<AddToCartResponse>{
               override fun onResponse(
                   call: Call<AddToCartResponse>,
                   response: Response<AddToCartResponse>
               ) {

                   if (response.isSuccessful)
                   {
                      if(response.body()?.status=="true")
                       {
                           if(wishlistadapter.modelList.size>0) {
                               if(wishlistadapter.modelList.size==1) {
                                   wishlistadapter.modelList.removeAt(position)
                                   wishlistadapter.notifyDataSetChanged()
                                   requireActivity().popFragment()
                               }
                               else
                               {
                                   wishlistadapter.modelList.removeAt(position)
                                   wishlistadapter.notifyDataSetChanged()


                               }
                           }
                       }
                   }

               }

               override fun onFailure(call: Call<AddToCartResponse>, t: Throwable) {


               }

           })

    }


}