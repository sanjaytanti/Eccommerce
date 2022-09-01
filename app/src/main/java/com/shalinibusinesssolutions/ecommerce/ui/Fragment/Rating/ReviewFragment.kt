package com.shalinibusinesssolutions.ecommerce.ui.Fragment.Rating

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.shalinibusinesssolutions.ecommerce.R
import com.shalinibusinesssolutions.ecommerce.databinding.FragmentReviewBinding
import com.shalinibusinesssolutions.ecommerce.ui.adapter.RatingAdapter
import com.shalinibusinesssolutions.ecommerce.ui.adapter.ReviewDetailsAdapter
import com.shalinibusinesssolutions.ecommerce.ui.api.RetrofitClient
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.*
import com.shalinibusinesssolutions.ecommerce.ui.utility.InternetAccess
import com.shalinibusinesssolutions.ecommerce.ui.utility.SharedPreferencesUtil
import com.shalinibusinesssolutions.ecommerce.ui.utility.popFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewFragment : Fragment(),View.OnClickListener {

    lateinit var  binding: FragmentReviewBinding
    lateinit var prductName : String
    var product_id : Int=0
    var rating:Int=0
    var mSnackbar: Snackbar? = null
    var listdata=ArrayList<RatingDetailsList>()
    var rivewdata=ArrayList<RatingDetailsList>()
    lateinit var reviewDetailsAdapter: ReviewDetailsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {


        binding= FragmentReviewBinding.inflate(layoutInflater,container,false)
        prductName= arguments?.getString("ReviewProductName") !!
        binding.productName.text=prductName
        var  userID = SharedPreferencesUtil().getUserId(requireContext()).toString().toInt()
        product_id= arguments?.getInt("ReviewProductID") !!


        if (InternetAccess.isConnected(requireContext()!!)) {
            geReviewlist()
            getSingleProduct( userID,product_id )
        } else {
            mSnackbar = Snackbar
                .make(binding.llMain, "No Internet Connection", Snackbar.LENGTH_INDEFINITE).setAction(
                    "Ok"
                ) { mSnackbar!!.dismiss() }
            mSnackbar!!.show()
        }


        binding.recReview.layoutManager=LinearLayoutManager(requireContext())
        reviewDetailsAdapter= ReviewDetailsAdapter()
        binding.progressbar.visibility=View.VISIBLE
        binding.imgBack.setOnClickListener(this)

        binding.starOne.setOnClickListener(this)
        binding.starTwo.setOnClickListener(this)
        binding.starThree.setOnClickListener(this)
        binding.starFour.setOnClickListener(this)
        binding.starFive.setOnClickListener(this)
        binding.llSubmit.setOnClickListener(this)
        return  binding.root

    }

    fun getSingleProduct(userID : Int,product_id: Int )
    {
        var call = RetrofitClient.apiservice.getRatingData(userID,product_id)
        call.enqueue(object : Callback<GetRatingDetailsResponse>{
            override fun onResponse(
                call: Call<GetRatingDetailsResponse>,
                response: Response<GetRatingDetailsResponse>
            ) {
                if(response.isSuccessful)
                {
                  if (response.body()?.status=="true")
                  {
                        listdata  = response.body()!!.data

                    //  Toast.makeText(requireContext(), "Rating " + listdata[0].rating, Toast.LENGTH_SHORT).show()
                        if(listdata[0].rating==1)
                        {
                            binding.One.setImageResource(R.drawable.fillstar)
                            binding.Two.setImageResource(R.drawable.star)
                            binding.Three.setImageResource(R.drawable.star)
                            binding.Four.setImageResource(R.drawable.star)
                            binding.Five.setImageResource(R.drawable.star)

                        }
                      else if(listdata[0].rating==2)
                        {
                            binding.One.setImageResource(R.drawable.fillstar)
                            binding.Two.setImageResource(R.drawable.fillstar)
                            binding.Three.setImageResource(R.drawable.star)
                            binding.Four.setImageResource(R.drawable.star)
                            binding.Five.setImageResource(R.drawable.star)

                        }

                        else if(listdata[0].rating==3)
                        {
                            binding.One.setImageResource(R.drawable.fillstar)
                            binding.Two.setImageResource(R.drawable.fillstar)
                            binding.Three.setImageResource(R.drawable.fillstar)
                            binding.Four.setImageResource(R.drawable.star)
                            binding.Five.setImageResource(R.drawable.star)

                        }

                        else if(listdata[0].rating==4)
                        {
                            binding.One.setImageResource(R.drawable.fillstar)
                            binding.Two.setImageResource(R.drawable.fillstar)
                            binding.Three.setImageResource(R.drawable.fillstar)
                            binding.Four.setImageResource(R.drawable.fillstar)
                            binding.Five.setImageResource(R.drawable.star)

                        }
                        else if(listdata[0].rating==5)
                        {
                            binding.One.setImageResource(R.drawable.fillstar)
                            binding.Two.setImageResource(R.drawable.fillstar)
                            binding.Three.setImageResource(R.drawable.fillstar)
                            binding.Four.setImageResource(R.drawable.fillstar)
                            binding.Five.setImageResource(R.drawable.fillstar)

                        }
                      else
                        {
                            binding.One.setImageResource(R.drawable.star)
                            binding.Two.setImageResource(R.drawable.star)
                            binding.Three.setImageResource(R.drawable.star)
                            binding.Four.setImageResource(R.drawable.star)
                            binding.Five.setImageResource(R.drawable.star)

                        }

                  }
                }
            }

            override fun onFailure(call: Call<GetRatingDetailsResponse>, t: Throwable) {

            }

        })

    }

    override fun onClick(p0: View?) {
        when(p0?.id)
        {
            R.id.starOne->
            {
                binding.starOne.setImageResource(R.drawable.fillstar)
                binding.starTwo.setImageResource(R.drawable.rstar)
                binding.starThree.setImageResource(R.drawable.rstar)
                binding.starFour.setImageResource(R.drawable.rstar)
                binding.starFive.setImageResource(R.drawable.rstar)
                rating = 1
                if (InternetAccess.isConnected(requireActivity())) {
                    var  userID = SharedPreferencesUtil().getUserId(requireContext()).toString().toInt()
                    product_id= arguments?.getInt("ReviewProductID") !!

                    submitProductRating( userID, rating, product_id)
                } else {
                    mSnackbar = Snackbar
                        .make(binding.llMain, "No Internet Connection", Snackbar.LENGTH_INDEFINITE)
                        .setAction(
                            "Ok"
                        ) { mSnackbar!!.dismiss() }
                    mSnackbar!!.show()
                }


            }

            R.id.starTwo->
            {
                binding.starOne.setImageResource(R.drawable.fillstar)
                binding.starTwo.setImageResource(R.drawable.fillstar)
                binding.starThree.setImageResource(R.drawable.rstar)
                binding.starFour.setImageResource(R.drawable.rstar)
                binding.starFive.setImageResource(R.drawable.rstar)
                rating = 2
                if (InternetAccess.isConnected(requireActivity())) {
                    var  userID = SharedPreferencesUtil().getUserId(requireContext()).toString().toInt()
                    product_id= arguments?.getInt("ReviewProductID") !!

                    submitProductRating( userID, rating, product_id)
                } else {
                    mSnackbar = Snackbar
                        .make(binding.llMain, "No Internet Connection", Snackbar.LENGTH_INDEFINITE)
                        .setAction(
                            "Ok"
                        ) { mSnackbar!!.dismiss() }
                    mSnackbar!!.show()
                }

            }

            R.id.starThree->
            {
                binding.starOne.setImageResource(R.drawable.fillstar)
                binding.starTwo.setImageResource(R.drawable.fillstar)
                binding.starThree.setImageResource(R.drawable.fillstar)
                binding.starFour.setImageResource(R.drawable.rstar)
                binding.starFive.setImageResource(R.drawable.rstar)
                rating = 3
                if (InternetAccess.isConnected(requireActivity())) {
                    var  userID = SharedPreferencesUtil().getUserId(requireContext()).toString().toInt()
                    product_id= arguments?.getInt("ReviewProductID") !!

                    submitProductRating( userID, rating, product_id)
                } else {
                    mSnackbar = Snackbar
                        .make(binding.llMain, "No Internet Connection", Snackbar.LENGTH_INDEFINITE)
                        .setAction(
                            "Ok"
                        ) { mSnackbar!!.dismiss() }
                    mSnackbar!!.show()
                }

            }
            R.id.starFour->
            {
                binding.starOne.setImageResource(R.drawable.fillstar)
                binding.starTwo.setImageResource(R.drawable.fillstar)
                binding.starThree.setImageResource(R.drawable.fillstar)
                binding.starFour.setImageResource(R.drawable.fillstar)
                binding.starFive.setImageResource(R.drawable.rstar)
                rating = 4
                if (InternetAccess.isConnected(requireActivity())) {
                    var  userID = SharedPreferencesUtil().getUserId(requireContext()).toString().toInt()
                    product_id= arguments?.getInt("ReviewProductID") !!

                    submitProductRating( userID, rating, product_id)
                } else {
                    mSnackbar = Snackbar
                        .make(binding.llMain, "No Internet Connection", Snackbar.LENGTH_INDEFINITE)
                        .setAction(
                            "Ok"
                        ) { mSnackbar!!.dismiss() }
                    mSnackbar!!.show()
                }

            }
            R.id.starFive ->
            {
                binding.starOne.setImageResource(R.drawable.fillstar)
                binding.starTwo.setImageResource(R.drawable.fillstar)
                binding.starThree.setImageResource(R.drawable.fillstar)
                binding.starFour.setImageResource(R.drawable.fillstar)
                binding.starFive.setImageResource(R.drawable.fillstar)
                rating = 5

                if (InternetAccess.isConnected(requireActivity())) {
                    var  userID = SharedPreferencesUtil().getUserId(requireContext()).toString().toInt()
                    product_id= arguments?.getInt("ReviewProductID") !!

                    submitProductRating( userID, rating, product_id)
                } else {
                    mSnackbar = Snackbar
                        .make(binding.llMain, "No Internet Connection", Snackbar.LENGTH_INDEFINITE)
                        .setAction(
                            "Ok"
                        ) { mSnackbar!!.dismiss() }
                    mSnackbar!!.show()
                }

            }
            R.id.ll_submit-> {
                if (TextUtils.isEmpty(binding.edReview.text.toString())) {
                    binding.edReview.setError("Please share your review ")
                    binding.edReview.requestFocus()

                } else {
                    var review = binding.edReview.text.toString()
                    if (InternetAccess.isConnected(requireActivity())) {
                        var userID =
                            SharedPreferencesUtil().getUserId(requireContext()).toString().toInt()
                        product_id = arguments?.getInt("ReviewProductID")!!

                        submitProductReview(userID, review, product_id)
                        findNavController().navigate(R.id.action_reviewFragment_to_ratingDetailsFragment)

                    } else {
                        mSnackbar = Snackbar
                            .make(
                                binding.llMain,
                                "No Internet Connection",
                                Snackbar.LENGTH_INDEFINITE
                            )
                            .setAction(
                                "Ok"
                            ) { mSnackbar!!.dismiss() }
                        mSnackbar!!.show()
                    }

                }


            }
            R.id.img_back->
            {
                requireActivity().popFragment()
            }
        }
    }


    fun geReviewlist()
    {
        var  userid = SharedPreferencesUtil().getUserId(requireContext()).toString().toInt()
        product_id= arguments?.getInt("ReviewProductID") !!

        val call= RetrofitClient.apiservice.getReviewDetails(product_id)
        call.enqueue(object : Callback<GetRatingDetailsResponse> {
            override fun onResponse(
                call: Call<GetRatingDetailsResponse>,
                response: Response<GetRatingDetailsResponse>
            ) {

                if (response.isSuccessful)
                {

                    if (response.body()?.status=="true")
                    {
                        rivewdata=response.body()?.data!!
                        reviewDetailsAdapter.Populistitem(rivewdata)
                        binding.recReview?.adapter=reviewDetailsAdapter
                        binding.progressbar?.visibility=View.GONE
                        binding.shimmerViewContainer.stopShimmer()
                        binding.shimmerViewContainer.visibility=View.GONE

                    }
                }

            }

            override fun onFailure(call: Call<GetRatingDetailsResponse>, t: Throwable) {
            }

        })

    }


    fun submitProductRating( userID : Int, rating:Int, product_id: Int)
    {
        try {


            val call =RetrofitClient.apiservice.getExistProductResponse(userID,product_id)
            call.enqueue(object :Callback<GetAddToCardResponse>{
                override fun onResponse(
                    call: Call<GetAddToCardResponse>,
                    response: Response<GetAddToCardResponse>
                ) {

                    if(response.isSuccessful)
                    {
                        if(response.body()?.status=="true")
                        {
                            var dataresponse = response.body()?.data?.userdata

                            if (dataresponse == "1") {

                                updateRating(userID , rating, product_id)

                            }
                            else {
                                addRating(userID , rating, product_id)
                            }

                        }
                    }
                }

                override fun onFailure(call: Call<GetAddToCardResponse>, t: Throwable) {
                    //Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                }

            })
        }
        catch (ex: Exception)
        {

        }

    }

    fun addRating(userID : Int, rating:Int, product_id: Int)
    {
        val call = RetrofitClient.apiservice.addToRating(userID,product_id,rating)
        call.enqueue(object : Callback<AddToCartResponse>{
            override fun onResponse(
                call: Call<AddToCartResponse>,
                response: Response<AddToCartResponse>
            ) {
                if (response.isSuccessful)
                {
                    if (response.body()?.status=="true")
                    {
                    }
                    else
                    {
                    }

                }
            }

            override fun onFailure(call: Call<AddToCartResponse>, t: Throwable) {

            }

        })

    }
    fun updateRating(userID : Int, rating:Int, product_id: Int)
    {
        val call = RetrofitClient.apiservice.updateToRating(userID,product_id,rating)
        call.enqueue(object : Callback<AddToCartResponse>{
            override fun onResponse(
                call: Call<AddToCartResponse>,
                response: Response<AddToCartResponse>
            ) {
                if (response.isSuccessful)
                {
                    if (response.body()?.status=="true")
                    {
                 //       Toast.makeText(requireContext(), "" + response.body()?.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                   //     Toast.makeText(requireContext(), "" + response.body()?.message.toString(), Toast.LENGTH_SHORT).show()
                    }

                }
            }

            override fun onFailure(call: Call<AddToCartResponse>, t: Throwable) {

            }

        })

    }

    fun submitProductReview( userID : Int, review:String, product_id: Int)
    {
        try {


            val call =RetrofitClient.apiservice.getExistProductResponse(userID,product_id)
            call.enqueue(object :Callback<GetAddToCardResponse>{
                override fun onResponse(
                    call: Call<GetAddToCardResponse>,
                    response: Response<GetAddToCardResponse>
                ) {

                    if(response.isSuccessful)
                    {
                        if(response.body()?.status=="true")
                        {
                            var dataresponse = response.body()?.data?.userdata

                            if (dataresponse == "1") {

                                updateReview(userID , review, product_id)

                            }
                            else {
                                addReview(userID , review, product_id)
                            }

                        }
                    }
                }

                override fun onFailure(call: Call<GetAddToCardResponse>, t: Throwable) {
                    //Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                }

            })
        }
        catch (ex: Exception)
        {

        }

    }

    fun addReview(userID : Int, review:String, product_id: Int)
    {
        val call = RetrofitClient.apiservice.addToReview(userID,product_id,review)
        call.enqueue(object : Callback<AddToCartResponse>{
            override fun onResponse(
                call: Call<AddToCartResponse>,
                response: Response<AddToCartResponse>
            ) {
                if (response.isSuccessful)
                {
                    if (response.body()?.status=="true")
                    {
                    }
                    else
                    {
                    }

                }
            }

            override fun onFailure(call: Call<AddToCartResponse>, t: Throwable) {

            }

        })

    }
    fun updateReview(userID : Int, review:String, product_id: Int)
    {
        val call = RetrofitClient.apiservice.updateToReview(userID,product_id,review)
        call.enqueue(object : Callback<AddToCartResponse>{
            override fun onResponse(
                call: Call<AddToCartResponse>,
                response: Response<AddToCartResponse>
            ) {
                if (response.isSuccessful)
                {
                    if (response.body()?.status=="true")
                    {
                        //       Toast.makeText(requireContext(), "" + response.body()?.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        //     Toast.makeText(requireContext(), "" + response.body()?.message.toString(), Toast.LENGTH_SHORT).show()
                    }

                }
            }

            override fun onFailure(call: Call<AddToCartResponse>, t: Throwable) {

            }

        })

    }

}