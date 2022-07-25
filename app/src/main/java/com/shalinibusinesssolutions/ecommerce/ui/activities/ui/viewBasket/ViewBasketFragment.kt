package com.shalinibusinesssolutions.ecommerce.ui.activities.ui.viewBasket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.shalinibusinesssolutions.ecommerce.R
import com.shalinibusinesssolutions.ecommerce.databinding.FragmentViewBasketBinding
import com.shalinibusinesssolutions.ecommerce.databinding.ViewbasketsinglerowBinding
import com.shalinibusinesssolutions.ecommerce.ui.adapter.ViewCardAdapter
import com.shalinibusinesssolutions.ecommerce.ui.api.RetrofitClient
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.AddToCartDetailResponse
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.AddToCartResponse
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.CartDetailList
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.GetAddToCardResponse
import com.shalinibusinesssolutions.ecommerce.ui.repository.AppRepository
import com.shalinibusinesssolutions.ecommerce.ui.utility.Fragmentinterface.ViewCardInterface
import com.shalinibusinesssolutions.ecommerce.ui.utility.SharedPreferencesUtil
import com.shalinibusinesssolutions.ecommerce.ui.utility.UserObject
import com.shalinibusinesssolutions.ecommerce.ui.utility.nextFragment
import com.shalinibusinesssolutions.ecommerce.ui.utility.popFragment
import com.shalinibusinesssolutions.ecommerce.ui.viewmodel.CartCountViewModel
import com.shalinibusinesssolutions.ecommerce.ui.viewmodel.UpdateToCartModel
import com.shalinibusinesssolutions.ecommerce.ui.viewmodel.ViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewBasketFragment : Fragment(), ViewCardInterface, View.OnClickListener {
    lateinit var binding: FragmentViewBasketBinding
    lateinit var viewCardAdapter: ViewCardAdapter
    lateinit var cartCountViewModel: CartCountViewModel
    lateinit var updateToCartModel: UpdateToCartModel

    private var cardList: ArrayList<CartDetailList> = ArrayList()

    var userid = 0
    var subtotal = 0.0f
    var deliveryfee = 0.0f
    var totalfee = 0.0f

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentViewBasketBinding.inflate(layoutInflater,container,false)

        userid = SharedPreferencesUtil().getUserId(requireContext()).toString().toInt()


        binding.progressbar.visibility = View.VISIBLE

        binding.recCheckout.layoutManager = LinearLayoutManager(requireContext())
        viewCardAdapter = ViewCardAdapter(this)
        cartCountViewModel = ViewModelProvider(requireActivity())[CartCountViewModel::class.java]

        initViewModel()
        getData()

        binding.btnCheckout.setOnClickListener(this)
        binding.imgBack.setOnClickListener(this)

        return binding.root

    }

    private fun initViewModel() {

        val appRepository = AppRepository(RetrofitClient.apiservice)

        updateToCartModel = ViewModelProvider(
            requireActivity(),
            ViewModelFactory(appRepository, requireContext())
        )[UpdateToCartModel::class.java]

    }

    private fun getData() {
        var call = RetrofitClient.apiservice.getAddToCardDetails(userid)
        call.enqueue(object : Callback<AddToCartDetailResponse> {
            override fun onResponse(
                call: Call<AddToCartDetailResponse>,
                response: Response<AddToCartDetailResponse>
            ) {

                if (response.isSuccessful) {
                    if (response.body()?.status == "true") {
                        cardList.clear()
                        cardList = response.body()!!.data
                        viewCardAdapter.Populistitem(cardList)
                        binding.recCheckout.adapter = viewCardAdapter
                        binding.progressbar.visibility = View.GONE
                        subtotal = 0.0f
                        totalfee = 0.0f

                        if (cardList.size > 0) {
                            for (i in cardList.indices) {
                                subtotal = subtotal + cardList[i].price.toString().toFloat()

                            }
                        }
                        binding.TvSubTotal.text = "₹ " + String.format("% .2f", subtotal).toString()
                        totalfee = totalfee + subtotal
                        binding.TvTotalAmt.text = "₹ " + String.format("% .2f", totalfee).toString()
                        UserObject.finalAmount=totalfee
                        UserObject.grandtotalamount=totalfee
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

            override fun onFailure(call: Call<AddToCartDetailResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show()

            }

        })
    }


    override suspend fun gotoViewBasketFragmentPage(
        cartDetailList: CartDetailList,
        status: String,
        cartbinding: ViewbasketsinglerowBinding,
        position: Int
    ) {
        var qty = 0
        var price = 0.0f

        if (status == "Add") {

            qty = cartCountViewModel.addCount(cartbinding.qty.text.toString().toInt())
            //   Toast.makeText(requireContext(), "" + cartDetailList.Pricecal.toString(), Toast.LENGTH_SHORT).show()
            price = cartCountViewModel.setPrice(qty, cartDetailList.Pricecal.toString().toFloat())
            cartbinding.qty.text = qty.toString()
            cartbinding.productPrice.text = "₹ " + String.format("% .2f", price).toString()
            cartDetailList.qty=qty
            cartDetailList.price=price
            viewCardAdapter.modelList[position]= cartDetailList
            subtotal = 0.0f
            totalfee = 0.0f

            if (viewCardAdapter.modelList.size > 0) {
                for (i in viewCardAdapter.modelList.indices) {
                    subtotal = subtotal + cardList[i].price.toString().toFloat()

                }
            }
            binding.TvSubTotal.text = "₹ " + String.format("% .2f", subtotal).toString()
            totalfee = totalfee + subtotal
            binding.TvTotalAmt.text = "₹ " + String.format("% .2f", totalfee).toString()
            UserObject.finalAmount=totalfee
            UserObject.grandtotalamount=totalfee
            updateToCart(
                cartDetailList.productid.toInt(),
                cartDetailList.variation_id.toInt(),
                qty,
                cartDetailList.mrp,
                cartDetailList.unit,
                price,
                cartDetailList.discount.toInt(),
                cartDetailList.varqty)



        }

        if (status == "Minus") {
            if ((cartbinding.qty.text.toString().toInt() > 1)) {
                qty = cartCountViewModel.minusCount(cartbinding.qty.text.toString().toInt())
                price =
                    cartCountViewModel.setPrice(qty, cartDetailList.Pricecal.toString().toFloat())
                cartbinding.qty.text = qty.toString()
                cartbinding.productPrice.text = "₹ " + String.format("% .2f", price).toString()

                cartDetailList.qty=qty
                cartDetailList.price=price
                viewCardAdapter.modelList[position]= cartDetailList
                subtotal = 0.0f
                totalfee = 0.0f

                if (viewCardAdapter.modelList.size > 0) {
                    for (i in viewCardAdapter.modelList.indices) {
                        subtotal = subtotal + cardList[i].price.toString().toFloat()

                    }
                }
                binding.TvSubTotal.text = "₹ " + String.format("% .2f", subtotal).toString()
                totalfee = totalfee + subtotal
                binding.TvTotalAmt.text = "₹ " + String.format("% .2f", totalfee).toString()
                UserObject.finalAmount=totalfee
                UserObject.grandtotalamount=totalfee
                updateToCart(
                    cartDetailList.productid.toInt(),
                    cartDetailList.variation_id.toInt(),
                    qty,
                    cartDetailList.mrp,
                    cartDetailList.unit,
                    price,
                    cartDetailList.discount.toInt(),
                    cartDetailList.varqty
                )

            }


        }


        if (status == "Delete") {


            val call = RetrofitClient.apiservice.deleteToCart(
                cartDetailList.productid.toInt(),
                cartDetailList.variation_id.toInt(),
                userid
            )

            call.enqueue(object : Callback<AddToCartResponse> {
                override fun onResponse(
                    call: Call<AddToCartResponse>,
                    response: Response<AddToCartResponse>
                ) {
                    if (response.isSuccessful) {
                        if (viewCardAdapter.modelList.size > 0) {

                            if (viewCardAdapter.modelList.size == 1) {
                                viewCardAdapter.modelList.removeAt(position)
                                viewCardAdapter.notifyDataSetChanged()
                                requireActivity().popFragment()
                            } else {
                                viewCardAdapter.modelList.removeAt(position)
                                viewCardAdapter.notifyDataSetChanged()
                                subtotal = 0.0f
                                totalfee = 0.0f

                                if (viewCardAdapter.modelList.size > 0) {
                                    for (i in viewCardAdapter.modelList.indices) {
                                        subtotal = subtotal + cardList[i].price.toString().toFloat()

                                    }
                                }
                                binding.TvSubTotal.text = "₹ " + String.format("% .2f", subtotal).toString()
                                totalfee = totalfee + subtotal
                                binding.TvTotalAmt.text = "₹ " + String.format("% .2f", totalfee).toString()
                                 UserObject.finalAmount=totalfee
                                UserObject.grandtotalamount=totalfee

                            }
                        }
                    }

                }

                override fun onFailure(call: Call<AddToCartResponse>, t: Throwable) {

                }

            })
        }


    }

    private fun updateToCart(
        varProductId: Int,
        variationId: Int,
        varQty: Int,
        varProductMrp: Float,
        varProductUnit: String,
        varProductPrice: Float,
        var_productDiscount: Int,
        variationQty: String
    ) {
        updateToCartModel.sucessresponse.observe(this, Observer {

            if (it.status == "true") {

                binding.progressbar.visibility = View.GONE


            } else {

            }
        })
        updateToCartModel.updateToCart(
            varProductId, variationId, varQty, varProductMrp,
            varProductUnit, varProductPrice, var_productDiscount, variationQty,userid
        )


    }


    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnCheckout -> {
                //Toast.makeText(requireContext(), "subtotal : " + subtotal, Toast.LENGTH_SHORT).show()
                var DeliveryCharge =binding.TvDeliveryAmt.text.toString()
                var total =binding.TvTotalAmt.text.toString()

                val userbundle = bundleOf(
                    "subtotal" to subtotal,
                    "DeliveryCharge" to DeliveryCharge,
                    "total" to total)

                var  call = RetrofitClient.apiservice.GetAddressResponse(userid)
                  call.enqueue(object : Callback<GetAddToCardResponse>{
                      override fun onResponse(
                          call: Call<GetAddToCardResponse>,
                          response: Response<GetAddToCardResponse>
                      ) {

                          if(response.isSuccessful)
                          {
                              if(response.body()?.status=="true")
                              {
                                  var addtoAddress = response.body()!!.data.userdata
                                  if (addtoAddress.equals("1")) {

                                      requireActivity().nextFragment(R.id.action_viewBasketFragment_to_checkoutFragment,userbundle)
                                  }
                                  else
                                  {
                                      requireActivity().nextFragment(R.id.action_viewBasketFragment_to_addtoAddressFragment,userbundle)

                                  }




                              }

                          }
                      }

                      override fun onFailure(call: Call<GetAddToCardResponse>, t: Throwable) {

                      }

                  })



            }
            R.id.img_back -> {
                requireActivity().popFragment()
            }
        }
    }


}