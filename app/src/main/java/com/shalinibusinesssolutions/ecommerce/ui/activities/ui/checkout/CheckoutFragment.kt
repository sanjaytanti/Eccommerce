package com.shalinibusinesssolutions.ecommerce.ui.activities.ui.checkout

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.razorpay.Checkout
import com.shalinibusinesssolutions.ecommerce.R
import com.shalinibusinesssolutions.ecommerce.databinding.AddresslayoutBinding
import com.shalinibusinesssolutions.ecommerce.databinding.CouponsinglerowlauoutBinding
import com.shalinibusinesssolutions.ecommerce.databinding.FragmentCheckoutBinding
import com.shalinibusinesssolutions.ecommerce.ui.adapter.AddressAdapter
import com.shalinibusinesssolutions.ecommerce.ui.api.RetrofitClient
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.*
import com.shalinibusinesssolutions.ecommerce.ui.utility.Fragmentinterface.CheckoutInterface
import com.shalinibusinesssolutions.ecommerce.ui.utility.Fragmentinterface.CouponInterface
import com.shalinibusinesssolutions.ecommerce.ui.utility.SharedPreferencesUtil
import com.shalinibusinesssolutions.ecommerce.ui.utility.UserObject
import com.shalinibusinesssolutions.ecommerce.ui.utility.nextFragment
import com.shalinibusinesssolutions.ecommerce.ui.utility.popFragment
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class CheckoutFragment : Fragment(), View.OnClickListener, CheckoutInterface {

    lateinit var binding: FragmentCheckoutBinding
    private lateinit var addressAdapter: AddressAdapter
    private var addresslist: ArrayList<AddressList> = ArrayList()
    var row_index = 0
    var DeliveryCharge = ""

    val TAG = "check Razor pay status"
    var userid = 0
    var buynow = ""
    var advanceBooking=""
    var GrandTotal = ""
    var subtotal = 0.0f
    var variationid = 0
    var productid = 0
    var qty = 0
    var unit = ""
    var mrp = 0.0f
    var price = 0.0f
    var discount = 0
    var variationQty = ""
    var varproductpricecal = 0.0f
    var finalAmount = 0.0f

    var coupon = ""
    var couponid = 0
    var couponcode = ""
    var couponDiscountAmount = 0.0f
    var couponDiscountpercentage = 0
    var couponAmount = 0.0f
    var couponterms = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        try {
            userid = SharedPreferencesUtil().getUserId(requireContext()).toString().toInt()
            binding = FragmentCheckoutBinding.inflate(layoutInflater, container, false)

            subtotal = UserObject.finalAmount//arguments?.getFloat("subtotal")!!
            DeliveryCharge = arguments?.getString("DeliveryCharge").toString()
            GrandTotal = subtotal.toString()
            finalAmount = subtotal


            binding.TvSubTotal.text = "₹ " + String.format("% .2f", UserObject.grandtotalamount).toString()
            binding.TvDeliveryAmt.text = ""//DeliveryCharge
            binding.TvTotalAmt.text = UserObject.finalAmount.toString()

            binding.reAddress.layoutManager = LinearLayoutManager(requireContext())
            addressAdapter = AddressAdapter(this)
            binding.progressbar.visibility = View.VISIBLE

            binding.imgBack.setOnClickListener(this)
            binding.fabAddAddress.setOnClickListener(this)
            binding.RbCash.setOnClickListener(this)
            binding.RbCard.setOnClickListener(this)
            binding.btnPlaceOrder.setOnClickListener(this)
            binding.applyNow?.setOnClickListener(this)
            binding.couponData?.setOnClickListener(this)
            getAddressData()

            buynow = arguments?.getString("buynow").toString()
            advanceBooking = arguments?.getString("advanceBooking").toString()

            UserObject.buynow = buynow
            UserObject.advanceBooking = advanceBooking
            if (buynow.equals("buynow")) {
                variationid = arguments?.getInt("Variationid")!!
                productid = arguments?.getInt("productid")!!
                qty = arguments?.getInt("qty")!!
                unit = arguments?.getString("unit").toString()
                mrp = arguments?.getFloat("mrp")!!
                price = arguments?.getFloat("price")!!
                discount = arguments?.getInt("discount")!!
                variationQty = arguments?.getString("variationqty").toString()
                varproductpricecal = arguments?.getFloat("varproductpricecal")!!

                UserObject.variationid = variationid
                UserObject.productid = productid
                UserObject.qty = qty
                UserObject.unit = unit
                UserObject.mrp = mrp
                UserObject.price = price
                UserObject.discount = discount
                UserObject.variationQty = variationQty
                UserObject.varproductpricecal = varproductpricecal


            }

            if (advanceBooking.equals("advanceBooking")) {

                try {
                    binding.tvAdvanceTit?.visibility=View.VISIBLE
                    binding.TvAdvanceAmt?.visibility=View.VISIBLE
                    binding.couponData.visibility=View.GONE
                    binding.RbCash.isChecked=false
                    binding.RbCash.isEnabled=false
                    binding.RbCash.visibility=View.GONE
                    variationid = arguments?.getInt("Variationid")!!
                    productid = arguments?.getInt("productid")!!
                    qty = arguments?.getInt("qty")!!
                    unit = arguments?.getString("unit").toString()
                    mrp = arguments?.getFloat("mrp")!!
                    price = arguments?.getFloat("price")!!
                    discount = arguments?.getInt("discount")!!
                    variationQty = arguments?.getString("variationqty").toString()
                    varproductpricecal = arguments?.getFloat("varproductpricecal")!!
                    UserObject.variationid = variationid
                    UserObject.productid = productid
                    UserObject.qty = qty
                    UserObject.unit = unit
                    UserObject.mrp = mrp
                    UserObject.price = price
                    UserObject.discount = discount
                    UserObject.variationQty = variationQty
                    UserObject.varproductpricecal = varproductpricecal


                }
                catch (ex: Exception)
                {
                    Toast.makeText(requireContext(), "" + ex.printStackTrace(), Toast.LENGTH_SHORT).show()
                 }

            }

            if ( UserObject.advanceBookingFragment.equals("yes")) {
                binding.RbCash.visibility = View.GONE
                binding.fabAddAddress.visibility=View.GONE


            }

            coupon = arguments?.getString("coupon").toString()

            if (coupon.equals("bycoupon")) {

                try {
                    couponid = arguments?.getInt("couponid")!!
                    couponcode = arguments?.getString("couponcode").toString()
                    couponDiscountAmount = arguments?.getFloat("couponDiscountAmount")!!
                    couponDiscountpercentage = arguments?.getInt("couponDiscountpercentage")!!
                    couponAmount = arguments?.getFloat("couponAmount")!!
                    couponterms = arguments?.getString("couponterms").toString()

                }

                catch (ex: Exception)
                {
                    ex.printStackTrace()
                }

                if ( UserObject.advanceBookingFragment.equals("yes"))
                {
                    Toast.makeText(requireContext(), "Hi advance booking fragment", Toast.LENGTH_SHORT).show()
                    binding.RbCash.visibility=View.GONE
                    if ((UserObject.grandtotalamount >= couponAmount) && (couponDiscountAmount >0))
                    {
                        if( UserObject.finalAmount>couponDiscountAmount) {

                            var balanceAmount = UserObject.finalAmount - couponDiscountAmount
                            UserObject.finalAmount = balanceAmount
                            binding.TvTotalAmt.text = UserObject.finalAmount.toString()
                        }
                    }
                    else if ((UserObject.grandtotalamount >= couponAmount) && (couponDiscountpercentage > 0)  ){


                        try {
                            var currentdiscountamt =
                                ((UserObject.grandtotalamount) * (couponDiscountpercentage)) / 100
                            var balanceAmount = UserObject.finalAmount - currentdiscountamt
                            UserObject.finalAmount = balanceAmount
                            binding.TvTotalAmt.text = UserObject.finalAmount.toString()

                        }
                        catch (ex: Exception)
                        {
                            Toast.makeText(requireContext(), "" + ex.printStackTrace(), Toast.LENGTH_SHORT).show()
                        }



                    }
                    else
                    {
                        binding.TvTotalAmt.text =UserObject.finalAmount.toString()

                    }



                }

                else if (couponDiscountAmount > 0) {


                    UserObject.finalAmount=UserObject.grandtotalamount

                    if (UserObject.grandtotalamount >= couponAmount) {
                        var balanceAmount = UserObject.grandtotalamount - couponDiscountAmount
                        finalAmount = balanceAmount
                        UserObject.finalAmount = finalAmount
                        binding.TvTotalAmt.text =UserObject.finalAmount.toString()

                    }
                    else
                    {
                        binding.TvTotalAmt.text =UserObject.finalAmount.toString()

                    }
                }
            }


            else if (couponDiscountpercentage > 0) {
                UserObject.finalAmount=UserObject.grandtotalamount

                if (UserObject.grandtotalamount >= couponAmount) {

                    var currentdiscountamt =
                        ((UserObject.grandtotalamount) * (couponDiscountpercentage)) / 100
                    var balanceAmount = UserObject.finalAmount - currentdiscountamt
                    finalAmount = balanceAmount
                    UserObject.finalAmount = finalAmount
                    binding.TvTotalAmt.text = UserObject.finalAmount.toString()


                }
                else
                {
                    binding.TvTotalAmt.text = UserObject.finalAmount.toString()

                }

            }


            binding.couponId?.addTextChangedListener(object :TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if(binding.couponId!!.text.trim().toString().isNullOrEmpty())
                    {
                        binding.TvTotalAmt.text=subtotal.toString()
                        finalAmount=subtotal
                        UserObject.finalAmount=subtotal
                    }
                }

                override fun afterTextChanged(p0: Editable?) {

                }

            })


            binding.TvAdvanceAmt?.addTextChangedListener(object :TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    try {
                        var advanceamt = binding.TvAdvanceAmt!!.text.toString().toInt()
                        var totalamt=binding.TvTotalAmt.text.toString().toFloat().toInt()

                        if(advanceamt>totalamt)
                        {
                            binding.btnPlaceOrder.isEnabled=false
                            binding.TvAdvanceAmt!!.setError("Advance Amount should less then Total Amount")
                            binding.TvAdvanceAmt!!.requestFocus()

                        }
                        else
                        {
                            binding.btnPlaceOrder.isEnabled=true
                        }

                    }
                    catch (ex: Exception)
                    {
                        Toast.makeText(requireContext(), "" + ex.printStackTrace(), Toast.LENGTH_SHORT).show()

                    }


                }

                override fun afterTextChanged(p0: Editable?) {

                }

            })

        }
        catch (ex: Exception)
        {
            Toast.makeText(requireContext(), "" + ex.printStackTrace(), Toast.LENGTH_SHORT).show()
        }



        return binding.root


    }


    private fun getAddressData() {

        var call = RetrofitClient.apiservice.getUserAddress(userid)
        call.enqueue(object : Callback<AddressResponse> {
            override fun onResponse(
                call: Call<AddressResponse>,
                response: Response<AddressResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body()?.status == "true") {
                        addresslist = response.body()?.data!!


                        if ( UserObject.advanceBookingFragment.equals("yes")) {

                            if(addresslist.size>0) {
                                for (i in 0 until addresslist.size) {
                                     if(addresslist[i].ID!=UserObject.address_id)
                                     {
                                        addresslist.removeAt(i)
                                     }

                                }
                            }
                        }


                        addressAdapter.Populistitem(addresslist)
                        binding.reAddress.adapter = addressAdapter
                        binding.progressbar.visibility = View.GONE


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

    override fun onClick(p0: View?) {

        when (p0?.id) {
            R.id.img_back -> {
                requireActivity().popFragment()
            }
            R.id.fab_addAddress -> {
                requireActivity().nextFragment(R.id.action_checkoutFragment_to_addtoAddressFragment)
            }

            R.id.Rb_cash -> {
                binding.apply {
                    RbCash.isChecked = true
                    RbCard.isChecked = false
                }
            }

            R.id.Rb_card -> {
                binding.apply {

                    RbCard.isChecked = true
                    RbCash.isChecked = false
                }
            }
            R.id.btn_PlaceOrder -> {


                when {

                    binding.RbCash.isChecked -> startNormalPayment()
                    binding.RbCard.isChecked -> startPayment()
                    else -> Toast.makeText(
                        requireContext(),
                        "Select a payment method first",
                        Toast.LENGTH_SHORT
                    ).show()

                }


            }

            R.id.apply_now -> {
                binding.progressbar.visibility = View.VISIBLE
                var coupanid = binding.couponId?.text.toString()
                if (TextUtils.isEmpty(coupanid)) {
                    binding.TvTotalAmt.text = subtotal.toString()

                    binding.couponId?.setError("Enter Coupon Id")
                    binding.couponId?.requestFocus()
                    binding.progressbar.visibility = View.GONE
                } else {
                    couponProcess()
                }


            }
            R.id.coupon_data->
            {
                requireActivity().nextFragment(R.id.action_checkoutFragment_to_couponFragment)
            }

        }
    }

    private fun couponProcess() {
        var call = RetrofitClient.apiservice.getExistingCustomerResponse(userid)
        call.enqueue(object : Callback<GetAddToCardResponse> {
            override fun onResponse(
                call: Call<GetAddToCardResponse>,
                response: Response<GetAddToCardResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body()?.status == "true") {
                        var dataresponse = response.body()?.data?.userdata
                        if (dataresponse == "1") {
                            binding.TvTotalAmt.text = subtotal.toString()

                            Toast.makeText(
                                requireContext(),
                                "sorry!! this Scheme for new customer",
                                Toast.LENGTH_SHORT
                            ).show()
                            binding.progressbar.visibility = View.GONE
                        } else {
                //            binding.TvTotalAmt.text = subtotal.toString()

                            UserDiscountData()

                        }
                    }
                }

            }

            override fun onFailure(call: Call<GetAddToCardResponse>, t: Throwable) {

            }

        })
    }

    private fun startPayment() {



        val activity: Activity = requireActivity()
        val checkout = Checkout()
        checkout.setKeyID("rzp_test_dAFyr1t4hJ3HZ4")


      //  subtotal = arguments?.getFloat("subtotal")!!

        if((UserObject.advanceBooking.equals("advanceBooking")) )
        {
            if(TextUtils.isEmpty(binding.TvAdvanceAmt?.text))
            {
                binding.TvAdvanceAmt.setError("Enter Advance Payment")
                binding.TvAdvanceAmt.requestFocus()

            }
            else {
                var advanceamt = binding.TvAdvanceAmt?.text?.trim().toString().toFloat()

                if (advanceamt > 0) {
                    UserObject.advancepayment = advanceamt
                    var amount = Math.round(advanceamt * 100)
                    try {
                        val options = JSONObject()
                        options.put("name", getString(R.string.app_name))
                        options.put("description", "Order Placement")
                        options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
                        options.put("currency", "INR")
                        options.put("amount", amount)
                        val preFill = JSONObject()
                        preFill.put("email", "sanjaytanti1981@gmail.com")
                        preFill.put("contact", "8961198331")
                        options.put("prefill", preFill)
                        checkout.open(activity, options)


                    } catch (e: Exception) {
                        Log.e(TAG, "Error in starting Razorpay Checkout", e)
                    }
                }
            }

        }

        else
        {
            var amount = Math.round(binding.TvTotalAmt.text.toString().toFloat() * 100)
            try {
                val options = JSONObject()
                options.put("name", getString(R.string.app_name))
                options.put("description", "Order Placement")
                options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
                options.put("currency", "INR")
                options.put("amount", amount)
                val preFill = JSONObject()
                preFill.put("email", "sanjaytanti1981@gmail.com")
                preFill.put("contact", "8961198331")
                options.put("prefill", preFill)
                checkout.open(activity, options)


            } catch (e: Exception) {
                Log.e(TAG, "Error in starting Razorpay Checkout", e)
            }
        }

    }

    private fun startNormalPayment() {
        if (buynow.equals("buynow")) {
            cashBuyNowNormalPayment()

        } else {
            cashCartNormalPayment()
        }

    }

    private fun transferDatacarttoProductorder(orderid: Int?) {

        val transfercall =
            RetrofitClient.apiservice.transferDataAddtocarttoProductorder(userid, orderid)
        transfercall.enqueue(object : Callback<AddToCartResponse> {
            override fun onResponse(
                call: Call<AddToCartResponse>,
                response: Response<AddToCartResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body()?.status == "true") {
                        delteaddtocartbyuserid()


                    }
                }
            }

            override fun onFailure(
                call: Call<AddToCartResponse>,
                t: Throwable
            ) {
                Toast.makeText(requireContext(), " " + t.message, Toast.LENGTH_SHORT)
                    .show()
                binding.progressbar.visibility = View.GONE

            }

        })


    }

    fun delteaddtocartbyuserid() {
        var deletecall = RetrofitClient.apiservice.deleteAddtoCartbyuserid(userid)
        deletecall.enqueue(object : Callback<AddToCartResponse> {
            override fun onResponse(
                call: Call<AddToCartResponse>,
                response: Response<AddToCartResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body()?.status == "true") {
                        UserObject.address_id = 0
                        UserObject.variationid = 0
                        UserObject.productid = 0
                        UserObject.qty = 0
                        UserObject.unit = ""
                        UserObject.mrp = 0.0f
                        UserObject.price = 0.0f
                        UserObject.discount = 0
                        UserObject.variationQty = ""
                        UserObject.varproductpricecal = 0.0f
                        UserObject.buynow = ""
                        UserObject.finalAmount = 0.0f;

                        binding.progressbar.visibility = View.GONE
                        requireActivity().nextFragment(R.id.action_checkoutFragment_to_thankyouFragment)

                    } else {
                        //   Toast.makeText(requireContext(), "some thing went wrong" , Toast.LENGTH_SHORT).show()
                        requireActivity().nextFragment(R.id.action_checkoutFragment_to_errorFragment)

                        binding.progressbar.visibility = View.GONE


                    }
                }
            }

            override fun onFailure(call: Call<AddToCartResponse>, t: Throwable) {

//                Toast.makeText(requireContext(), "" + t?.message.toString(), Toast.LENGTH_SHORT).show()
                requireActivity().nextFragment(R.id.action_checkoutFragment_to_errorFragment)

                binding.progressbar.visibility = View.GONE

            }

        })

    }

    private fun cashBuyNowNormalPayment() {
        binding.progressbar.visibility = View.VISIBLE
        userid = SharedPreferencesUtil().getUserId(requireContext()).toString().toInt()
        var addressid = UserObject.address_id

        val simpleFormat0 = SimpleDateFormat("dd-MM-yyyy", Locale.US)
        val date0 = Date(System.currentTimeMillis())
        var dtestr = simpleFormat0.format(date0)
        val currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())


        var call = RetrofitClient.apiservice.addtoorderbyBuyNow(
            userid, addressid, productid, variationid,
            "cash", "cash", mrp, price, varproductpricecal,
            qty, variationQty, unit, discount, dtestr, currentTime, UserObject.finalAmount,UserObject.finalAmount
        )

        call.enqueue(object : Callback<AddToCartResponse> {
            override fun onResponse(
                call: Call<AddToCartResponse>,
                response: Response<AddToCartResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body()?.status == "true") {
                        binding.progressbar.visibility = View.GONE
                        UserObject.address_id = 0
                        UserObject.variationid = 0
                        UserObject.productid = 0
                        UserObject.qty = 0
                        UserObject.unit = ""
                        UserObject.mrp = 0.0f
                        UserObject.price = 0.0f
                        UserObject.discount = 0
                        UserObject.variationQty = ""
                        UserObject.varproductpricecal = 0.0f
                        UserObject.buynow = ""
                        UserObject.finalAmount = 0.0f;

                        requireActivity().nextFragment(R.id.action_checkoutFragment_to_thankyouFragment)

                    } else {
                        binding.progressbar.visibility = View.GONE
                        requireActivity().nextFragment(R.id.action_checkoutFragment_to_errorFragment)

                    }
                }
            }

            override fun onFailure(call: Call<AddToCartResponse>, t: Throwable) {
                //Toast.makeText(requireContext(), "" + t.message, Toast.LENGTH_SHORT).show()
                requireActivity().nextFragment(R.id.action_checkoutFragment_to_errorFragment)
                binding.progressbar.visibility = View.GONE

            }

        })

    }

    private fun cashCartNormalPayment() {
        binding.progressbar.visibility = View.VISIBLE
        userid = SharedPreferencesUtil().getUserId(requireContext()).toString().toInt()
        var addressid = UserObject.address_id
        val simpleFormat0 = SimpleDateFormat("dd-MM-yyyy", Locale.US)
        val date0 = Date(System.currentTimeMillis())
        var dtestr = simpleFormat0.format(date0)
        val currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())

        //*************************** test************************
        val call = RetrofitClient.apiservice.addorderbycart(
            userid,
            addressid,
            "COD",
            "cash",
            dtestr,
            currentTime,
            UserObject.finalAmount,
            UserObject.finalAmount
        )
        call.enqueue(object : Callback<OrderResponse> {
            override fun onResponse(
                call: Call<OrderResponse>,
                response: Response<OrderResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body()?.status == "true") {
                        var orderid = response.body()?.data?.orderid?.toInt()
                        transferDatacarttoProductorder(orderid)

                    } else {
                        Toast.makeText(requireContext(), "something went wrong", Toast.LENGTH_SHORT)
                            .show()

                        binding.progressbar.visibility = View.GONE

                    }
                }
            }

            override fun onFailure(call: Call<OrderResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "" + t.message, Toast.LENGTH_SHORT).show()
                binding.progressbar.visibility = View.GONE

            }

        })

    }



    override fun gotoCheckoutFragmentPage(
        userbinding: AddresslayoutBinding,
        position: Int,
        addressList: AddressList,
        flag: String
    ) {

        binding.progressbar.visibility = View.VISIBLE

        if (flag == "Delete") {
            var call =
                RetrofitClient.apiservice.deleteUserAddress(addressList.ID, addressList.userid)
            call.enqueue(object : Callback<AddToCartResponse> {
                override fun onResponse(
                    call: Call<AddToCartResponse>,
                    response: Response<AddToCartResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.status == "true") {
                            if (addressAdapter.modelList.size > 1) {
                                addressAdapter.modelList.removeAt(position)
                                addressAdapter.notifyDataSetChanged()
                                binding.progressbar.visibility = View.GONE
                            } else {
                                requireActivity().popFragment()
                            }
                        }
                    }

                }

                override fun onFailure(call: Call<AddToCartResponse>, t: Throwable) {

                }


            })

        }

    }


    fun UserDiscountData() {
        binding.TvTotalAmt.text = subtotal.toString()

        var copouncode = binding.couponId?.text.toString().trim()
        var call = RetrofitClient.apiservice.getCouponResponse(copouncode)
        call.enqueue(object : Callback<getCouponResponse> {
            override fun onResponse(
                call: Call<getCouponResponse>,
                response: Response<getCouponResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body()?.status == "true") {
                        var amount=0.0f
                        var  Disamount=0.0f
                        var TotalAmt=0.0f
                        var balanceAmount=0.0f
                        amount = response.body()?.data?.Amount!!
                         Disamount = response.body()?.data?.DiscountAmount!!
                         TotalAmt = subtotal
                         balanceAmount = TotalAmt - Disamount!!

                        if (TotalAmt >= amount!!) {
                            binding.TvTotalAmt.text = balanceAmount.toString()
                            finalAmount = balanceAmount
                            UserObject.finalAmount = finalAmount

                        }
                        else
                        {

                            binding.TvTotalAmt.text = TotalAmt.toString()
                            finalAmount = TotalAmt
                            UserObject.finalAmount = finalAmount

                        }
                        binding.progressbar.visibility = View.GONE


                    } else {
                        binding.progressbar.visibility = View.GONE
                    }
                }
            }

            override fun onFailure(call: Call<getCouponResponse>, t: Throwable) {
                binding.progressbar.visibility = View.GONE
            }

        })

    }



}