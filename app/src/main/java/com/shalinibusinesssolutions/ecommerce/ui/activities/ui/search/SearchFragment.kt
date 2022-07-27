package com.shalinibusinesssolutions.ecommerce.ui.activities.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.shalinibusinesssolutions.ecommerce.R
import com.shalinibusinesssolutions.ecommerce.databinding.CartlayoutBinding
import com.shalinibusinesssolutions.ecommerce.databinding.FragmentSearchBinding
import com.shalinibusinesssolutions.ecommerce.ui.adapter.SearchProductAdapter
import com.shalinibusinesssolutions.ecommerce.ui.api.RetrofitClient
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.ProductList
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.ProductResponse
import com.shalinibusinesssolutions.ecommerce.ui.utility.Fragmentinterface.ProductInterFace
import com.shalinibusinesssolutions.ecommerce.ui.utility.popFragment
import retrofit2.Call
import retrofit2.Response

class SearchFragment : Fragment() ,ProductInterFace,View.OnClickListener{

    private lateinit var binding: FragmentSearchBinding
    private var productList = ArrayList<ProductList>()
    private lateinit var searchproductAdapter: SearchProductAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)

      binding.recProductSearch?.layoutManager = GridLayoutManager(requireContext(),2)
        searchproductAdapter=SearchProductAdapter(this)
        getProductList()

        binding.SearchView?.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                  try {
                      searchproductAdapter.getFilter().filter(newText)

                }
                catch (ex: Exception)
                {
                    ex.printStackTrace()
                }
                return false
            }

        })
            //.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            //android.widget.SearchView.OnQueryTextListener {

            return binding.root

    }

    private fun getProductList() {
        binding.progressbar?.visibility=View.VISIBLE
        val call=RetrofitClient.apiservice.getProductData()
         call.enqueue(object :retrofit2.Callback<ProductResponse>{
             override fun onResponse(
                 call: Call<ProductResponse>,
                 response: Response<ProductResponse>
             ) {
                 if(response.isSuccessful)
                 {
                     if(response.body()?.status=="true")
                     {
                         productList=response.body()?.data!!
                         searchproductAdapter.backupdata.addAll(productList)
                //         searchproductAdapter.Populistitem(productList)
                         binding.recProductSearch?.adapter=searchproductAdapter
                         binding.progressbar?.visibility=View.GONE
                     }
                 }

             }

             override fun onFailure(call: Call<ProductResponse>, t: Throwable) {

             }
         })
    }


    override fun gotoProductFragmentPage(
        productList: ProductList,
        status: String,
        binding: CartlayoutBinding
    ) {
        TODO("Not yet implemented")
    }

    override fun gotoProductPage(productList: ProductList) {
        TODO("Not yet implemented")
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