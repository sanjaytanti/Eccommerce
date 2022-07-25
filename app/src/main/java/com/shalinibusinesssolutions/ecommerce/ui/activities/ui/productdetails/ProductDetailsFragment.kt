package com.shalinibusinesssolutions.ecommerce.ui.activities.ui.productdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.shalinibusinesssolutions.ecommerce.R
import com.shalinibusinesssolutions.ecommerce.databinding.FragmentProductDetailsBinding
import com.shalinibusinesssolutions.ecommerce.ui.api.RetrofitClient
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.*
import com.shalinibusinesssolutions.ecommerce.ui.repository.AppRepository
import com.shalinibusinesssolutions.ecommerce.ui.utility.SharedPreferencesUtil
import com.shalinibusinesssolutions.ecommerce.ui.utility.UserObject
import com.shalinibusinesssolutions.ecommerce.ui.utility.nextFragment
import com.shalinibusinesssolutions.ecommerce.ui.utility.popFragment
import com.shalinibusinesssolutions.ecommerce.ui.viewmodel.AddToCartViewModel
import com.shalinibusinesssolutions.ecommerce.ui.viewmodel.CartCountViewModel
import com.shalinibusinesssolutions.ecommerce.ui.viewmodel.UpdateToCartModel
import com.shalinibusinesssolutions.ecommerce.ui.viewmodel.ViewModelFactory
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProductDetailsFragment : Fragment(), View.OnClickListener {

    lateinit var binding: FragmentProductDetailsBinding
    var productId: Int = 0
    private var productList: ArrayList<ProductDetailsList> = ArrayList()
    lateinit var cartCountViewModel: CartCountViewModel
    lateinit var addToCartViewModel: AddToCartViewModel
    lateinit var updateToCartModel: UpdateToCartModel

    var productMrp = 0.0f
    var productPrice = 0.0f
    var productDiscount = 0.0f
    var productDiscountper = 0
    var productDescription = ""
    var variationid = 0
    var varproductid = 0
    var varproductUnit = ""
    var userid = 0
    var producvariationqty = ""
    var varproductpricecal = 0.0f
   var  productvaritionid=0
    var producctvariationstock =0
    var productvariationclosingstock=0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentProductDetailsBinding.inflate(layoutInflater, container, false)
        productId = arguments?.getInt("Id")!!

        //Toast.makeText(requireContext(), "Product id" + productId, Toast.LENGTH_SHORT).show()
        cartCountViewModel = ViewModelProvider(requireActivity())[CartCountViewModel::class.java]
        userid = SharedPreferencesUtil().getUserId(requireContext()).toString().toInt()
        initViewModel()
        getProductDetailsList()

        binding.imgBack.setOnClickListener(this)
        binding.llAdd.setOnClickListener(this)
        binding.llMinus.setOnClickListener(this)
        binding.layoutAddToCart.setOnClickListener(this)
        binding.llBuy.setOnClickListener(this)
        binding.uncheckWish.setOnClickListener(this)
        binding.btnAdvanceBooking?.setOnClickListener(this)

        return binding.root

    }


    private fun initViewModel() {

        val appRepository = AppRepository(RetrofitClient.apiservice)

        addToCartViewModel = ViewModelProvider(
            requireActivity(),
            ViewModelFactory(appRepository, requireContext())
        )[AddToCartViewModel::class.java]

        updateToCartModel = ViewModelProvider(
            requireActivity(),
            ViewModelFactory(appRepository, requireContext())
        )[UpdateToCartModel::class.java]

    }

    private fun wishListData( productvaritionid:Int )
    {
       try {
           var call = RetrofitClient.apiservice.getwishlistresponse(userid,productId,productvaritionid)
           call.enqueue(object : Callback<GetAddToCardResponse>{
               override fun onResponse(
                   call: Call<GetAddToCardResponse>,
                   response: Response<GetAddToCardResponse>
               ) {
                   if (response.isSuccessful)
                   {
                       if( response.body()?.status=="true")
                       {
                           var userflag=response.body()?.data?.userdata
                           if(userflag.equals("1"))
                           {
                               binding.uncheckWish.visibility=View.GONE
                               binding.checkedWish.visibility=View.VISIBLE
                           }
                           else
                           {
                               binding.uncheckWish.visibility=View.VISIBLE
                               binding.checkedWish.visibility=View.GONE


                           }

                       }
                   }

               }

               override fun onFailure(call: Call<GetAddToCardResponse>, t: Throwable) {

               }

           })

       }

     catch (ex: Exception)
     {

     }

    }


    private fun getProductDetailsList() {
        try {
            var call =
                RetrofitClient.apiservice.getProductdetailsByProductId(productId)
            call.enqueue(object : Callback<ProductDetailsListResponse> {
                override fun onResponse(
                    call: Call<ProductDetailsListResponse>,
                    response: Response<ProductDetailsListResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()!!.status == "true") {
                            if (response.body()!!.productdata != null) {
                                var data = response.body()!!.data
                                productList = response.body()!!.productdata
                                binding.productName.text = data.name.toString()
                                Picasso.with(requireContext()).load(data.Image)
                                    .into(binding.productImage)

                                binding.productDescription.text =
                                    data.description.toString()

                                var prductarray = ArrayList<String>()
                                for (i in productList.indices) {
                                    prductarray.add(productList[i].qty.toString() + " " + productList[i].unit.toString())
                                }

                                val dataAdapter: ArrayAdapter<String> =
                                    ArrayAdapter<String>(
                                        requireActivity(),
                                        android.R.layout.simple_spinner_item,
                                        prductarray
                                    )
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                binding.spinner.setAdapter(dataAdapter)

                                binding.spinner.onItemSelectedListener = object :
                                    AdapterView.OnItemSelectedListener {
                                    override fun onItemSelected(
                                        p0: AdapterView<*>?,
                                        p1: View?,
                                        p2: Int,
                                        p3: Long
                                    ) {

                                        for (i in productList.indices) {
                                            var Productqtymatch =

                                                productList[i].qty.toString() + " " + productList[i].unit.toString()

                                            if (Productqtymatch == prductarray[p2].toString()) {
                                                UserObject.variationid=productList[i].ID
                                                productMrp = productList[i].mrp
                                                productPrice = productList[i].price
                                                productDiscount =
                                                    productList[i].DiscountAmt
                                                productDiscountper =
                                                    productList[i].DiscountPer
                                                variationid = productList[i].ID
                                                varproductid =
                                                    productList[i].productid
                                                productvaritionid=variationid
                                                varproductUnit = productList[i].unit
                                                producvariationqty =
                                                    productList[i].qty.toString() + " " + productList[i].unit.toString()

                                                producctvariationstock= productList[i].stock
                                                productvariationclosingstock=productList[i].closingstock
                                                break
                                            }
                                        }
                                        wishListData(variationid)
                                        if (productMrp > 0) {
                                            binding.productMrp.text =
                                                "₹ " + String.format(
                                                    "% .2f",
                                                    productMrp
                                                ).toString()

                                        }

                                        if (productPrice > 0) {
                                            var price = 0.0f
                                            binding.productPrice.text =
                                                "₹ " + String.format(
                                                    "% .2f",
                                                    productPrice
                                                ).toString()

                                            binding.productPriceCal.text =
                                                productPrice.toString()

                                            price = ((binding.qty.text.toString()
                                                .toFloat()) * (binding.productPriceCal.text.toString()
                                                .toFloat()))

                                            binding.cartPrice.text =
                                                "₹ " + String.format("% .2f", price)
                                                    .toString()

                                            binding.cartQty.text =
                                                binding.qty.text.toString()
                                            if (productvariationclosingstock>0) {
                                                binding.stock.text =
                                                    "in stock"
                                                binding.layoutAddToCart.visibility=View.VISIBLE
                                                binding.llBuy.visibility=View.VISIBLE
                                                binding.btnAdvanceBooking?.visibility=View.GONE
                                            }
                                            else
                                            {
                                                binding.layoutAddToCart.visibility=View.GONE
                                                binding.llBuy.visibility=View.GONE
                                                binding.btnAdvanceBooking?.visibility=View.VISIBLE
                                                binding.stock.text = "out stock"
                                            }
                                        }
                                        if (productDiscount > 0) {
                                            binding.llDiscount.visibility =
                                                View.VISIBLE
                                            binding.off.text =
                                                "₹ " + String.format(
                                                    "% .2f",
                                                    productDiscount
                                                )
                                                    .toString()
                                        } else {
                                            binding.llDiscount.visibility =
                                                View.GONE
                                        }

                                    }

                                    override fun onNothingSelected(p0: AdapterView<*>?) {

                                    }

                                }
                            }

                        } else {
                            Toast.makeText(
                                requireContext(),
                                "" + response.body()!!.message.toString(),
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }
                }


                override fun onFailure(
                    call: Call<ProductDetailsListResponse>,
                    t: Throwable
                ) {
                    Toast.makeText(
                        requireContext(),
                        "please check your internet connection",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            })

        }
        catch (ex: Exception)
        {

        }


    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.img_back -> {
                requireActivity().popFragment()

            }
            R.id.ll_add -> {
                addInProduct()

            }

            R.id.ll_minus -> {
                subtractInProduct()
            }
            R.id.layout_addTo_cart -> {
                binding.progressbar.visibility = View.VISIBLE
                addTOCart()
            }
            R.id.ll_buy -> {
                binding.progressbar.visibility=View.VISIBLE
                buyNow()
            }
            R.id.uncheckWish -> {
                binding.progressbar.visibility = View.VISIBLE
                addTOWishlist()

            }
            R.id.btn_advanceBooking->
            {
                binding.progressbar.visibility=View.VISIBLE
                advanceBooking()
            }
        }
    }

    private fun addInProduct()
    {
        try {
            var qty = 0
            var price = 0.0f
            qty = cartCountViewModel.addCount(binding.qty.text.toString().toInt())
            price = cartCountViewModel.setPrice(
                qty,
                binding.productPriceCal.text.toString().toFloat()
            )
            binding.qty.text = qty.toString()
            binding.cartQty.text = qty.toString()
            binding.cartPrice.text = "₹ " + String.format("% .2f", price).toString()

            var call = RetrofitClient.apiservice.getVariationDetails(UserObject.variationid)
            call.enqueue( object : Callback<getVariationResponse>{
                override fun onResponse(
                    call: Call<getVariationResponse>,
                    response: Response<getVariationResponse>
                ) {
                    if (response.isSuccessful)
                    {
                        if (response.body()?.status=="true")
                        {
                            var closingstock =response.body()?.data?.closingstock

                            var variationqty=binding.qty.text.toString().toInt()

                            if (closingstock != null) {
                                UserObject.variationclosingstock=closingstock
                                //  Toast.makeText(requireContext(), "stock :" + UserObject.variationclosingstock, Toast.LENGTH_SHORT).show()


                                if(variationqty>closingstock)
                                {
                                    binding.stock.text="out stock"
                                    binding.layoutAddToCart.visibility=View.GONE
                                    binding.llBuy.visibility=View.GONE
                                    binding.btnAdvanceBooking?.visibility=View.VISIBLE

                                }

                            }


                        }
                    }

                }

                override fun onFailure(call: Call<getVariationResponse>, t: Throwable) {

                }

            })

        }
        catch (ex: Exception)
        {

        }
    }

  private fun  subtractInProduct()
  {
      try {
          var qty = 0
          var price = 0.0f

          if ((binding.qty.text.toString().toInt() == 1)) {
              binding.qty.text = "1"
              binding.cartQty.text = "1"
              price = cartCountViewModel.setPrice(
                  1,
                  binding.productPriceCal.text.toString().toFloat()
              )
              binding.cartPrice.text = "₹ " + String.format("% .2f", price).toString()
              binding.cartQty.text = qty.toString()

          } else {
              qty = cartCountViewModel.minusCount(binding.qty.text.toString().toInt())
              price = cartCountViewModel.setPrice(
                  qty,
                  binding.productPriceCal.text.toString().toFloat()
              )
              binding.qty.text = qty.toString()
              binding.cartQty.text = qty.toString()
              binding.cartPrice.text = "₹ " + String.format("% .2f", price).toString()


          }

          var call = RetrofitClient.apiservice.getVariationDetails(UserObject.variationid)
          call.enqueue( object : Callback<getVariationResponse>{
              override fun onResponse(
                  call: Call<getVariationResponse>,
                  response: Response<getVariationResponse>
              ) {
                  if (response.isSuccessful)
                  {
                      if (response.body()?.status=="true")
                      {
                          var closingstock =response.body()?.data?.closingstock

                          var variationqty=binding.qty.text.toString().toInt()

                          if (closingstock != null) {
                              UserObject.variationclosingstock=closingstock
                              //  Toast.makeText(requireContext(), "stock :" + UserObject.variationclosingstock, Toast.LENGTH_SHORT).show()


                              if(variationqty>closingstock)
                              {
                                  binding.stock.text="out stock"
                                  binding.layoutAddToCart.visibility=View.GONE
                                  binding.llBuy.visibility=View.GONE
                                  binding.btnAdvanceBooking?.visibility=View.VISIBLE

                              }
                              else
                              {
                                  binding.stock.text="in stock"
                                  binding.layoutAddToCart.visibility=View.VISIBLE
                                  binding.llBuy.visibility=View.VISIBLE
                                  binding.btnAdvanceBooking?.visibility=View.GONE

                              }
                          }


                      }
                  }

              }

              override fun onFailure(call: Call<getVariationResponse>, t: Throwable) {

              }

          })

      }
       catch (ex: Exception)
       {

       }
  }

    private fun buyNow()
    {
        try {
            var var_productMrp = productMrp
            var price = ((binding.qty.text.toString()
                .toFloat()) * (binding.productPriceCal.text.toString().toFloat()))
            var var_productPrice = price
            var var_productDiscount = productDiscount
            var var_productDiscountper = productDiscountper
            var var_productDescription = productDescription
            var var_variationid = variationid
            var var_varproductid = varproductid
            var var_qty = binding.qty.text.toString().toInt()
            var variationQty = binding.spinner.selectedItem.toString()
            varproductpricecal = var_productPrice//binding.productPriceCal.text.toString().toFloat()
            //  Toast.makeText(requireContext(), "price" + varproductpricecal, Toast.LENGTH_SHORT).show()
            checkAddress( var_variationid, var_varproductid,var_qty,
                var_productMrp,
                varproductUnit,
                var_productPrice,
                var_productDiscount.toInt(),
                userid,
                variationQty,
                varproductpricecal
            )
        }
        catch (ex : Exception)
        {

        }




    }


    private fun advanceBooking()
    {
        try {
            var var_productMrp = productMrp
            var price = ((binding.qty.text.toString()
                .toFloat()) * (binding.productPriceCal.text.toString().toFloat()))
            var var_productPrice = price
            var var_productDiscount = productDiscount
            var var_productDiscountper = productDiscountper
            var var_productDescription = productDescription
            var var_variationid = variationid
            var var_varproductid = varproductid
            var var_qty = binding.qty.text.toString().toInt()
            var variationQty = binding.spinner.selectedItem.toString()
            varproductpricecal = var_productPrice//binding.productPriceCal.text.toString().toFloat()
            //  Toast.makeText(requireContext(), "price" + varproductpricecal, Toast.LENGTH_SHORT).show()
            advanceBookingAddress( var_variationid, var_varproductid,var_qty,
                var_productMrp,
                varproductUnit,
                var_productPrice,
                var_productDiscount.toInt(),
                userid,
                variationQty,
                varproductpricecal
            )


        }
        catch (ex: Exception)
        {

        }


    }


    private fun checkAddress( varVariationid: Int,
                              var_varproductid: Int,
                              varQty: Int,
                              varProductmrp: Float,
                              varproductUnit: String,
                              varProductprice: Float,
                              var_productDiscount: Int,
                              userid: Int,
                              variationQty: String,
                              varproductpricecal: Float)
    {

        try {
            var call = RetrofitClient.apiservice.GetAddressResponse(userid)
            call.enqueue(object : Callback<GetAddToCardResponse> {
                override fun onResponse(
                    call: Call<GetAddToCardResponse>,
                    response: Response<GetAddToCardResponse>
                ) {

                    if (response.isSuccessful) {

                        var  subtotal =varproductpricecal
                        var DeliveryCharge =""
                        var total= varproductpricecal//binding.productPrice.text.toString()
                        UserObject.finalAmount=varproductpricecal
                        UserObject.grandtotalamount=varproductpricecal


                        val userbundle = bundleOf(
                            "Variationid"  to   varVariationid,
                            "productid" to  var_varproductid,
                            "qty" to  varQty,
                            "unit" to  varproductUnit,
                            "mrp"  to  varProductmrp,
                            "price"  to  varProductprice,
                            "discount"  to  var_productDiscount,
                            "variationqty" to variationQty,
                            "varproductpricecal" to varproductpricecal,
                            "buynow" to "buynow",
                            "subtotal" to subtotal,
                            "DeliveryCharge" to DeliveryCharge,
                            "total" to total)


                        if (response.body()?.status == "true") {


                            var addtoAddress = response.body()!!.data.userdata

                            CoroutineScope(Dispatchers.Main).launch {
                                if (addtoAddress.equals("1")) {
                                    try {
                                        requireActivity().nextFragment(R.id.action_productDetailsFragment_to_checkoutFragment,userbundle)

                                    }
                                    catch (ex: Exception)
                                    {

                                    }

                                } else {
                                    requireActivity().nextFragment(R.id.action_productDetailsFragment_to_addtoAddressFragment,userbundle)

                                }

                            }


                        }

                    }
                }

                override fun onFailure(call: Call<GetAddToCardResponse>, t: Throwable) {

                }

            })

        }
         catch (ex: Exception)
         {

         }

    }

    private fun advanceBookingAddress( varVariationid: Int,
                              var_varproductid: Int,
                              varQty: Int,
                              varProductmrp: Float,
                              varproductUnit: String,
                              varProductprice: Float,
                              var_productDiscount: Int,
                              userid: Int,
                              variationQty: String,
                              varproductpricecal: Float)
    {

        try {
            var call = RetrofitClient.apiservice.GetAddressResponse(userid)
            call.enqueue(object : Callback<GetAddToCardResponse> {
                override fun onResponse(
                    call: Call<GetAddToCardResponse>,
                    response: Response<GetAddToCardResponse>
                ) {

                    if (response.isSuccessful) {

                        var  subtotal =varproductpricecal
                        var DeliveryCharge =""
                        var total= varproductpricecal//binding.productPrice.text.toString()
                        UserObject.finalAmount=varproductpricecal
                        UserObject.grandtotalamount=varproductpricecal


                        val userbundle = bundleOf(
                            "Variationid"  to   varVariationid,
                            "productid" to  var_varproductid,
                            "qty" to  varQty,
                            "unit" to  varproductUnit,
                            "mrp"  to  varProductmrp,
                            "price"  to  varProductprice,
                            "discount"  to  var_productDiscount,
                            "variationqty" to variationQty,
                            "varproductpricecal" to varproductpricecal,
                            "advanceBooking" to "advanceBooking",
                            "subtotal" to subtotal,
                            "DeliveryCharge" to DeliveryCharge,
                            "total" to total)


                        if (response.body()?.status == "true") {


                            var addtoAddress = response.body()!!.data.userdata

                            CoroutineScope(Dispatchers.Main).launch {
                                if (addtoAddress.equals("1")) {

                                    requireActivity().nextFragment(R.id.action_productDetailsFragment_to_checkoutFragment,userbundle)

                                } else {
                                    requireActivity().nextFragment(R.id.action_productDetailsFragment_to_addtoAddressFragment,userbundle)

                                }

                            }


                        }

                    }
                }

                override fun onFailure(call: Call<GetAddToCardResponse>, t: Throwable) {

                }

            })

        }
        catch (ex : Exception)
        {

        }

    }


    private fun addTOWishlist()
    {
        try {
            var call = RetrofitClient.apiservice.addWishlist(productId, variationid, userid)
            call.enqueue(object : Callback<AddToCartResponse> {
                override fun onResponse(
                    call: Call<AddToCartResponse>,
                    response: Response<AddToCartResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.status == "true") {
                            binding.checkedWish.visibility=View.VISIBLE
                            binding.progressbar.visibility = View.GONE
                        }
                        else
                        {
                            binding.checkedWish.visibility=View.GONE

                        }
                    }

                }

                override fun onFailure(call: Call<AddToCartResponse>, t: Throwable) {
                    Toast.makeText(requireContext(), "" + t.message, Toast.LENGTH_SHORT).show()
                }

            })
        }
        catch (ex: Exception)
        {

        }

    }

    private fun addTOCart() {

        try {
            var call = RetrofitClient.apiservice.getAddToCardResponse(userid, productId, variationid)
            call.enqueue(object : Callback<GetAddToCardResponse> {
                override fun onResponse(
                    call: Call<GetAddToCardResponse>,
                    response: Response<GetAddToCardResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.status == "true") {
                            var var_productMrp = productMrp
                            var price = ((binding.qty.text.toString()
                                .toFloat()) * (binding.productPriceCal.text.toString().toFloat()))

                            var var_productPrice = price
                            var var_productDiscount = productDiscount
                            var var_productDiscountper = productDiscountper
                            var var_productDescription = productDescription
                            var var_variationid = variationid
                            var var_varproductid = varproductid
                            var var_qty = binding.qty.text.toString().toInt()
                            var variationQty = binding.spinner.selectedItem.toString()
                            varproductpricecal =price


                            var addtocartstatus = response.body()!!.data.userdata

                            if (addtocartstatus.equals("1")) {
                                updateToCart(
                                    var_varproductid, var_variationid, var_qty, var_productMrp,
                                    varproductUnit, var_productPrice, var_productDiscount.toInt(),
                                    variationQty

                                )

                            } else {


                                insertDatatoCart(
                                    var_varproductid,
                                    var_variationid,
                                    var_qty,
                                    var_productMrp,
                                    varproductUnit,
                                    var_productPrice,
                                    var_productDiscount.toInt(),
                                    userid,
                                    variationQty,
                                    varproductpricecal

                                )


                            }
                        }
                    }
                }

                override fun onFailure(call: Call<GetAddToCardResponse>, t: Throwable) {
                    Toast.makeText(requireContext(), "something went wrong", Toast.LENGTH_SHORT).show()

                }

            })

        }
        catch (ex:Exception)
        {

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
      try {
          updateToCartModel.sucessresponse.observe(this, Observer {
              if (it.status == "true") {
                  binding.progressbar.visibility = View.GONE
                  // Toast.makeText(requireContext(), "" + it.message, Toast.LENGTH_SHORT).show()
                  requireActivity().popFragment()


              } else {

              }
          })
          updateToCartModel.updateToCart(
              varProductId, variationId, varQty, varProductMrp,
              varProductUnit, varProductPrice, var_productDiscount, variationQty, userid
          )
      }
      catch (ex: Exception)
      {

      }

    }

    private fun insertDatatoCart(
        varVarproductid: Int,
        varVariationid: Int,
        varQty: Int,
        varProductmrp: Float,
        varproductUnit: String,
        varProductprice: Float,
        var_productDiscount: Int,
        userid: Int,
        variationQty: String,
        varproductpricecal: Float

    ) {

        try {
            addToCartViewModel.sucessresponse.observe(this, Observer {

                if (it.status == "true") {
                    binding.progressbar.visibility = View.GONE
                    //Toast.makeText(requireContext(), "" + it.message, Toast.LENGTH_SHORT).show()
                    requireActivity().popFragment()


                } else {

                }
            })

            addToCartViewModel.addToCart(
                varVarproductid, varVariationid, varQty, varProductmrp,
                varproductUnit, varProductprice, var_productDiscount, userid, variationQty,
                varproductpricecal
            )

        }
        catch (ex: Exception)
        {

        }


    }


}



