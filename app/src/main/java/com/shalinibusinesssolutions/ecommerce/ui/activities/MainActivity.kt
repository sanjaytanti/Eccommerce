package com.shalinibusinesssolutions.ecommerce.ui.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupWithNavController
import com.razorpay.PaymentResultListener
import com.shalinibusinesssolutions.ecommerce.R
import com.shalinibusinesssolutions.ecommerce.databinding.ActivityMainBinding
import com.shalinibusinesssolutions.ecommerce.ui.activities.ui.ErrorActivity
import com.shalinibusinesssolutions.ecommerce.ui.activities.ui.thankyouActivity
import com.shalinibusinesssolutions.ecommerce.ui.api.RetrofitClient
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.AddToCartResponse
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.OrderResponse
import com.shalinibusinesssolutions.ecommerce.ui.utility.AppController
import com.shalinibusinesssolutions.ecommerce.ui.utility.SharedPreferencesUtil
import com.shalinibusinesssolutions.ecommerce.ui.utility.UserObject
import com.shalinibusinesssolutions.ecommerce.ui.utility.nextFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), PaymentResultListener {
    var doubleBackToExitPressedOnce = false
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setSupportActionBar(binding.toolbar)


        initNavigation()
//        val navHostFragment =
//            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment?
//        val navController = navHostFragment!!.navController
//
//        setupWithNavController(binding.navView, navController)

        setContentView(binding.root)

    }


    private fun initNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment?
        val navController = navHostFragment!!.navController

        setupWithNavController(binding.navView, navController)
        binding.navView.setupWithNavController(navController)
        binding.navView.setOnNavigationItemReselectedListener {
            "Reselect blocked."
        }


            binding.toolbar.setNavigationOnClickListener {
            when (navController.currentDestination?.id) {

                R.id.addtoAddressFragment,R.id.cartFragment,R.id.checkoutFragment,
                R.id.homeFragment, R.id.productFragment, R.id.productDetailsFragment,
                R.id.profileFragment,R.id.searchFragment , R.id.viewBasketFragment,
                R.id.wishlistFragment -> {
                    if (onBackPressedDispatcher.hasEnabledCallbacks())
                        onBackPressedDispatcher.onBackPressed()
                    else
                        navController.navigateUp()
                }
                else -> navController.navigateUp()
            }
          }

    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()
        Handler(Looper.getMainLooper()).postDelayed({
            doubleBackToExitPressedOnce = false
        }, 2000)
    }

    override fun onPaymentSuccess(p0: String?) {

    //    Toast.makeText(this, " Settlement : "+ UserObject.advanceBookingFragment, Toast.LENGTH_SHORT).show()

        if(UserObject.buynow.equals("buynow"))
        {
            BuyNowPayment(p0)

        }
        else if(UserObject.advanceBookingFragment.equals("yes"))
        {
            advanceSettlementPayment(p0)

        }

        else if(UserObject.advanceBooking.equals("advanceBooking"))
        {

            advanceBookingPayment(p0)
        }
        else
        {

            CartPayment(p0)
        }

    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this,  p1  , Toast.LENGTH_SHORT).show()

    }

    private fun BuyNowPayment(p0 : String?) {
         binding.progressbar?.visibility= View.VISIBLE
        var userid = SharedPreferencesUtil().getUserId(this).toString().toInt()
        var addressid= UserObject.address_id
        var productid=UserObject.productid
        var variationid=UserObject.variationid
        var  mrp=UserObject.mrp
        var price=UserObject.price
        var  varproductpricecal=UserObject.varproductpricecal
        var  qty=UserObject.qty
        var  variationQty=UserObject.variationQty
        var  unit=UserObject.unit
        var  discount=UserObject.discount

        val simpleFormat0 = SimpleDateFormat("dd-MM-yyyy", Locale.US)
        val date0 = Date(System.currentTimeMillis())
        var dtestr=simpleFormat0.format(date0)
        val currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())


        binding.progressbar?.visibility= View.VISIBLE
        var call =
            RetrofitClient.apiservice.addtoorderbycardBuyNow(userid,addressid,productid,variationid,
            p0,"Cart",mrp,price, varproductpricecal,
            qty,variationQty,unit,discount,dtestr,currentTime,UserObject.finalAmount,UserObject.finalAmount)

        call.enqueue(object : Callback<AddToCartResponse> {
            override fun onResponse(
                call: Call<AddToCartResponse>,
                response: Response<AddToCartResponse>
            ) {
                if(response.isSuccessful)
                {
                    if(response.body()?.status=="true")
                    {
                        UserObject.address_id=0
                        UserObject.variationid=0
                        UserObject.productid=0
                        UserObject.qty=0
                        UserObject.variationQty=""
                        UserObject.unit =""
                        UserObject.mrp=0.0f
                        UserObject.price=0.0f
                        UserObject.varproductpricecal=0.0f
                        UserObject.discount=0
                        UserObject.variationclosingstock=0
                        UserObject.buynow=""
                        UserObject.advanceBooking=""
                        UserObject.finalAmount =0.0f
                        UserObject.grandtotalamount=0.0f
                        UserObject.advancepayment=0.0f
                        UserObject.orderid=0
                        UserObject.transactionno =""
                        UserObject.Ordervalue=0.0f
                        UserObject.advanceBookingFragment=""

                        binding.progressbar?.visibility= View.GONE
                        val intent =Intent(this@MainActivity,thankyouActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else
                    {//
                        binding.progressbar?.visibility= View.GONE
                        val intent =Intent(this@MainActivity,ErrorActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }

            override fun onFailure(call: Call<AddToCartResponse>, t: Throwable) {
                binding.progressbar?.visibility= View.GONE
                Toast.makeText(this@MainActivity, "" + t.message, Toast.LENGTH_SHORT).show()

            }

        })

    }

    private fun CartPayment(p0 : String?)
    {
        binding.progressbar?.visibility= View.VISIBLE

        // binding.progressbar.visibility= View.VISIBLE
        var userid = SharedPreferencesUtil().getUserId(this).toString().toInt()
        var addressid= UserObject.address_id
        val simpleFormat0 = SimpleDateFormat("dd-MM-yyyy", Locale.US)
        val date0 = Date(System.currentTimeMillis())
        var dtestr=simpleFormat0.format(date0)
        val currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())

        //*************************** test************************
        val call = RetrofitClient.apiservice.addorbycartcarttransaction(userid,addressid,p0,"Cart",dtestr,currentTime,UserObject.finalAmount,UserObject.finalAmount)
        call.enqueue(object : Callback<OrderResponse> {
            override fun onResponse(
                call: Call<OrderResponse>,
                response: Response<OrderResponse>
            ) {
                if(response.isSuccessful)
                {
                    if(response.body()?.status=="true")
                    {
                        var orderid=response.body()?.data?.orderid?.toInt()
                        transferDatacarttoProductorder(orderid)

                    }
                    else
                    {
                        Toast.makeText(this@MainActivity, "something went wrong", Toast.LENGTH_SHORT).show()
                        binding.progressbar?.visibility= View.GONE

                    }
                }
            }

            override fun onFailure(call: Call<OrderResponse>, t: Throwable) {

                Toast.makeText(this@MainActivity, "" + t.message, Toast.LENGTH_SHORT).show()
                binding.progressbar?.visibility= View.GONE

            }

        })

    }

    private fun transferDatacarttoProductorder(orderid: Int?) {
        var userid = SharedPreferencesUtil().getUserId(this).toString().toInt()
        var addressid= UserObject.address_id

        val transfercall =RetrofitClient.apiservice.transferDataAddtocarttoProductorder(userid,orderid)
        transfercall.enqueue(object : Callback<AddToCartResponse>{
            override fun onResponse(
                call: Call<AddToCartResponse>,
                response: Response<AddToCartResponse>
            ) {
                if(response.isSuccessful)
                {
                    if (response.body()?.status=="true")
                    {
                        delteaddtocartbyuserid()


                    }
                }
            }

            override fun onFailure(
                call: Call<AddToCartResponse>,
                t: Throwable
            ) {
                Toast.makeText(this@MainActivity, " " + t.message, Toast.LENGTH_SHORT)
                    .show()
                binding.progressbar?.visibility=View.GONE

            }

        })



    }

    fun delteaddtocartbyuserid()
    {
        var userid = SharedPreferencesUtil().getUserId(this).toString().toInt()

        var deletecall=RetrofitClient.apiservice.deleteAddtoCartbyuserid(userid)
        deletecall.enqueue(object : Callback<AddToCartResponse>{
            override fun onResponse(
                call: Call<AddToCartResponse>,
                response: Response<AddToCartResponse>
            ) {
                if (response.isSuccessful)
                {
                    if(response.body()?.status=="true")
                    {
                        UserObject.address_id=0
                        UserObject.variationid=0
                        UserObject.productid=0
                        UserObject.qty=0
                        UserObject.variationQty=""
                        UserObject.unit =""
                        UserObject.mrp=0.0f
                        UserObject.price=0.0f
                        UserObject.varproductpricecal=0.0f
                        UserObject.discount=0
                        UserObject.variationclosingstock=0
                        UserObject.buynow=""
                        UserObject.advanceBooking=""
                        UserObject.finalAmount =0.0f
                        UserObject.grandtotalamount=0.0f
                        UserObject.advancepayment=0.0f
                        UserObject.orderid=0
                        UserObject.transactionno =""
                        UserObject.Ordervalue=0.0f
                        UserObject.advanceBookingFragment=""

                        binding.progressbar?.visibility=View.GONE
                        val intent =Intent(this@MainActivity,thankyouActivity::class.java)
                        startActivity(intent)
                        finish()

                    }
                    else
                    {

                        binding.progressbar?.visibility=View.GONE
                        val intent =Intent(this@MainActivity,ErrorActivity::class.java)
                        startActivity(intent)
                        finish()

                    }
                }
            }

            override fun onFailure(call: Call<AddToCartResponse>, t: Throwable) {

                binding.progressbar?.visibility=View.GONE
                val intent =Intent(this@MainActivity,ErrorActivity::class.java)
                startActivity(intent)
                finish()

            }

        })

    }


    private fun advanceBookingPayment(p0 : String?) {
        binding.progressbar?.visibility= View.VISIBLE
        var userid = SharedPreferencesUtil().getUserId(this).toString().toInt()
        var addressid= UserObject.address_id
        var productid=UserObject.productid
        var variationid=UserObject.variationid
        var  mrp=UserObject.mrp
        var price=UserObject.price
        var  varproductpricecal=UserObject.varproductpricecal
        var  qty=UserObject.qty
        var  variationQty=UserObject.variationQty
        var  unit=UserObject.unit
        var  discount=UserObject.discount

        val simpleFormat0 = SimpleDateFormat("dd-MM-yyyy", Locale.US)
        val date0 = Date(System.currentTimeMillis())
        var dtestr=simpleFormat0.format(date0)
        val currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())


        binding.progressbar?.visibility= View.VISIBLE
        var call =
            RetrofitClient.apiservice.advanceBooking(userid,addressid,productid,variationid,
                p0,"Cart",mrp,price, varproductpricecal,
                qty,variationQty,unit,discount,dtestr,currentTime,UserObject.finalAmount,UserObject.advancepayment)

        call.enqueue(object : Callback<AddToCartResponse> {
            override fun onResponse(
                call: Call<AddToCartResponse>,
                response: Response<AddToCartResponse>
            ) {
                if(response.isSuccessful)
                {
                    if(response.body()?.status=="true")
                    {
                        UserObject.address_id=0
                        UserObject.variationid=0
                        UserObject.productid=0
                        UserObject.qty=0
                        UserObject.variationQty=""
                        UserObject.unit =""
                        UserObject.mrp=0.0f
                        UserObject.price=0.0f
                        UserObject.varproductpricecal=0.0f
                        UserObject.discount=0
                        UserObject.variationclosingstock=0
                        UserObject.buynow=""
                        UserObject.advanceBooking=""
                        UserObject.finalAmount =0.0f
                        UserObject.grandtotalamount=0.0f
                        UserObject.advancepayment=0.0f
                        UserObject.orderid=0
                        UserObject.transactionno =""
                        UserObject.Ordervalue=0.0f
                        UserObject.advanceBookingFragment=""

                        binding.progressbar?.visibility= View.GONE
                        val intent =Intent(this@MainActivity,thankyouActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else
                    {//
                        binding.progressbar?.visibility= View.GONE
                        val intent =Intent(this@MainActivity,ErrorActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }

            override fun onFailure(call: Call<AddToCartResponse>, t: Throwable) {
                binding.progressbar?.visibility= View.GONE
                Toast.makeText(this@MainActivity, "" + t.message, Toast.LENGTH_SHORT).show()

            }

        })

    }

    private fun advanceSettlementPayment(p0 : String?) {
        binding.progressbar?.visibility= View.VISIBLE
        var userid = SharedPreferencesUtil().getUserId(this).toString().toInt()
        var addressid= UserObject.address_id
        var productid=UserObject.productid
        var variationid=UserObject.variationid
        var  mrp=UserObject.mrp
        var price=UserObject.price
        var  varproductpricecal=UserObject.varproductpricecal
        var  qty=UserObject.qty
        var  variationQty=UserObject.variationQty
        var  unit=UserObject.unit
        var  discount=UserObject.discount

        val simpleFormat0 = SimpleDateFormat("dd-MM-yyyy", Locale.US)
        val date0 = Date(System.currentTimeMillis())
        var dtestr=simpleFormat0.format(date0)
        val currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())


        binding.progressbar?.visibility= View.VISIBLE
        var call =
            RetrofitClient.apiservice.advanceSettlement(userid,addressid,productid,variationid,
                p0,"Cart",mrp,price, varproductpricecal,
                qty,variationQty,unit,discount,dtestr,currentTime,UserObject.grandtotalamount,UserObject.finalAmount,UserObject.orderid)

        call.enqueue(object : Callback<AddToCartResponse> {
            override fun onResponse(
                call: Call<AddToCartResponse>,
                response: Response<AddToCartResponse>
            ) {
                if(response.isSuccessful)
                {
                    if(response.body()?.status=="true")
                    {
                        UserObject.address_id=0
                        UserObject.variationid=0
                        UserObject.productid=0
                        UserObject.qty=0
                        UserObject.variationQty=""
                        UserObject.unit =""
                        UserObject.mrp=0.0f
                        UserObject.price=0.0f
                        UserObject.varproductpricecal=0.0f
                        UserObject.discount=0
                        UserObject.variationclosingstock=0
                        UserObject.buynow=""
                        UserObject.advanceBooking=""
                        UserObject.finalAmount =0.0f
                        UserObject.grandtotalamount=0.0f
                        UserObject.advancepayment=0.0f
                        UserObject.orderid=0
                        UserObject.transactionno =""
                        UserObject.Ordervalue=0.0f
                        UserObject.advanceBookingFragment=""

                        binding.progressbar?.visibility= View.GONE
                        val intent =Intent(this@MainActivity,thankyouActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else
                    {//
                        binding.progressbar?.visibility= View.GONE
                        val intent =Intent(this@MainActivity,ErrorActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }

            override fun onFailure(call: Call<AddToCartResponse>, t: Throwable) {
                binding.progressbar?.visibility= View.GONE
                Toast.makeText(this@MainActivity, "" + t.message, Toast.LENGTH_SHORT).show()

            }

        })

    }

}

