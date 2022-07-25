package com.shalinibusinesssolutions.ecommerce.ui.activities.ui.Product

import android.os.Bundle
import android.util.Log
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
import com.shalinibusinesssolutions.ecommerce.databinding.CartlayoutBinding
import com.shalinibusinesssolutions.ecommerce.databinding.FragmentProductBinding
import com.shalinibusinesssolutions.ecommerce.ui.adapter.ProductAdapter
import com.shalinibusinesssolutions.ecommerce.ui.api.RetrofitClient
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.ProductList
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.ProductResponse
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.ViewDataResponse
import com.shalinibusinesssolutions.ecommerce.ui.repository.AppRepository
import com.shalinibusinesssolutions.ecommerce.ui.utility.Fragmentinterface.ProductInterFace
import com.shalinibusinesssolutions.ecommerce.ui.utility.Productobject
import com.shalinibusinesssolutions.ecommerce.ui.utility.SharedPreferencesUtil
import com.shalinibusinesssolutions.ecommerce.ui.utility.nextFragment
import com.shalinibusinesssolutions.ecommerce.ui.utility.popFragment
import com.shalinibusinesssolutions.ecommerce.ui.viewmodel.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProductFragment() : Fragment(), ProductInterFace, View.OnClickListener {

    lateinit var binding: FragmentProductBinding
    private var productList: ArrayList<ProductList> = ArrayList()
    private lateinit var getProductViewModel: GetProductViewModel
    private lateinit var getDasBoardViewModel: GetDasBoardViewModel
    private lateinit var getProductListViewModel: GetProductListViewModel
    private lateinit var productAdapter: ProductAdapter
    lateinit var cartCountViewModel: CartCountViewModel
    var productId: Int = 0
    var userid = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        Productobject.userinv = this

        binding = FragmentProductBinding.inflate(layoutInflater, container, false)
        cartCountViewModel = ViewModelProvider(requireActivity())[CartCountViewModel::class.java]
        binding.progressbar.visibility = View.VISIBLE
        productId = arguments?.getInt("Id")!!
        Log.d("productId", productId.toString())
        userid = SharedPreferencesUtil().getUserId(requireContext()).toString().toInt()
        initViewModel()
        binding.recProduct.layoutManager = LinearLayoutManager(requireContext())
        productAdapter = ProductAdapter(this)

        binding.imgBack.setOnClickListener(this)
        binding.layoutitemview.setOnClickListener(this)
        return binding.root
    }


    private fun initViewModel() {
        val appRepository = AppRepository(RetrofitClient.apiservice)

        getProductViewModel = ViewModelProvider(
            requireActivity(),
            ViewModelFactory(appRepository, requireContext())
        )[GetProductViewModel::class.java]


    }

    override fun onResume() {
        super.onResume()
        getProductData()
        getViewData()
//        getDataFromProduct()

    }

    private fun getViewData() {
        // Toast.makeText(requireContext(), "" + userid, Toast.LENGTH_SHORT).show()
        val call = RetrofitClient.apiservice.viewCart(userid)
        call.enqueue(object : Callback<ViewDataResponse> {
            override fun onResponse(
                call: Call<ViewDataResponse>,
                response: Response<ViewDataResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body()?.status == "true") {
                        var viewdata = response.body()?.data
                        binding.itemsInCartqty.text = ""
                        binding.itemsInCartPrice.text = ""

                        binding.itemsInCartqty.text = viewdata?.qty?.toString()
                        binding.itemsInCartPrice.text = viewdata?.price?.toString()
                        // Toast.makeText(requireContext(), "" + response.body()?.data?.price1.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<ViewDataResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "" + t.message.toString(), Toast.LENGTH_SHORT)
                    .show()
            }


        })
    }

    private fun getProductData() {

        val call = RetrofitClient.apiservice.getProductListByCategoryId(productId)
        call.enqueue(object : Callback<ProductResponse> {
            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body()!!.status == "true") {
                        productList = response.body()?.data!!
                        productAdapter.Populistitem(productList)
                        binding.recProduct.adapter = productAdapter
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

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {

                Toast.makeText(requireContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show()

            }

        })
    }


    private fun getDataFromProduct() {
        val viewModel = ViewModelProvider(requireActivity())[GetProductListViewModel::class.java]

        viewModel.productList().observe(requireActivity(), Observer {
            if (it != null) {


                //Toast.makeText(requireContext(), "Data size" + productList.size, Toast.LENGTH_SHORT).show()


                if (it.status == "true") {
                    productList.clear()

                    productList = it.data

                    Log.d("productsize", productList.size.toString())

                    productAdapter.Populistitem(productList)
                    binding.recProduct.adapter = productAdapter
                    binding.progressbar.visibility = View.GONE

                } else {
                    //  Toast.makeText(requireContext(), "Data not Found", Toast.LENGTH_SHORT).show()
                }
            }

        })
        viewModel.makeApiCall(productId)


        /*
            viewModel.ProductResponse.observe(requireActivity(), Observer {
                if(it!=null)
                {
                    if(it.status=="true")
                    {
                        productList=it.data
                        productAdapter.Populistitem(productList)
                        binding.recProduct.adapter = productAdapter
                        binding.progressbar.visibility = View.GONE

                    }
                    else
                    {

                    }

                }

            })

            viewModel.errorResponse.observe(requireActivity(), Observer {
                Log.d("",it.toString())
            })

           viewModel.getProductData(productId)
*/

    }


    override fun gotoProductFragmentPage(
        productList: ProductList,
        status: String,
        binding: CartlayoutBinding
    ) {
        var qty = 0
        var price = 0.0f

        if (status == "Add") {
            qty = cartCountViewModel.addCount(binding.qty.text.toString().toInt())
            price = cartCountViewModel.setPrice(qty, productList.mrp)
            binding.qty.text = qty.toString()
            binding.stock.text = "₹ " + String.format("% .2f", price).toString()


        }

        if (status == "Minus") {
            if ((binding.qty.text.toString().toInt() == 1)) {
                binding.qty.text = "1"
                price = cartCountViewModel.setPrice(1, productList.mrp)
                binding.stock.text = "₹ " + String.format("% .2f", price).toString()

            } else {
                qty = cartCountViewModel.minusCount(binding.qty.text.toString().toInt())
                price = cartCountViewModel.setPrice(qty, productList.mrp)
                binding.qty.text = qty.toString()
                binding.stock.text = "₹ " + String.format("% .2f", price).toString()


            }
        }
    }

    override fun gotoProductPage(productList: ProductList) {

        val bundle = bundleOf("Id" to productList.ID)
        requireActivity().nextFragment(
            R.id.action_productFragment_to_productDetailsFragment,
            bundle
        )
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.img_back -> {
                productList.clear()
                requireActivity().popFragment()

            }

            R.id.layoutitemview -> {
                requireActivity().nextFragment(
                    R.id.action_productFragment_to_viewBasketFragment,
                    bundle = null
                )

            }
        }
    }


}