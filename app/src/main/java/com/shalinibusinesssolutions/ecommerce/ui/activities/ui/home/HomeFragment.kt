package com.shalinibusinesssolutions.ecommerce.ui.activities.ui.home

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.shalinibusinesssolutions.ecommerce.R
import com.shalinibusinesssolutions.ecommerce.databinding.FragmentHomeBinding
import com.shalinibusinesssolutions.ecommerce.ui.adapter.BannerAdapterNew
import com.shalinibusinesssolutions.ecommerce.ui.adapter.CategoryAdapter
import com.shalinibusinesssolutions.ecommerce.ui.api.RetrofitClient
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.CategoryList
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.DashBoardResponse
import com.shalinibusinesssolutions.ecommerce.ui.repository.AppRepository
import com.shalinibusinesssolutions.ecommerce.ui.utility.Fragmentinterface.CategoryInterface
import com.shalinibusinesssolutions.ecommerce.ui.utility.Resource
import com.shalinibusinesssolutions.ecommerce.ui.utility.nextFragment
import com.shalinibusinesssolutions.ecommerce.ui.viewmodel.GetBannerViewModel
import com.shalinibusinesssolutions.ecommerce.ui.viewmodel.GetDasBoardViewModel
import com.shalinibusinesssolutions.ecommerce.ui.viewmodel.ViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.abs


class HomeFragment : Fragment(), CategoryInterface {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var getBannerViewModel: GetBannerViewModel
    private lateinit var getDasBoardViewModel: GetDasBoardViewModel
    private val sliderHandler: Handler = Handler()
    private lateinit var bannerAdapterNew: BannerAdapterNew
    private lateinit var categoryAdapter: CategoryAdapter
    private var categoryList: ArrayList<CategoryList> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        initViewModel()
//        getBannerData()

        return binding.root

    }


    val sliderRunnable =
        Runnable { binding.viewpager.currentItem = binding.viewpager.currentItem + 1 }


    override fun onPause() {
        super.onPause()
        sliderHandler.removeCallbacks(sliderRunnable)
    }

    override fun onResume() {
        super.onResume()

        sliderHandler.postDelayed(sliderRunnable, 3000)
        getProductBannerData()
        //getBannerData()

    }


    private fun initViewModel() {

        val appRepository = AppRepository(RetrofitClient.apiservice)

        getBannerViewModel = ViewModelProvider(
            requireActivity(),
            ViewModelFactory(appRepository, requireContext())
        )[GetBannerViewModel::class.java]

        getDasBoardViewModel = ViewModelProvider(
            requireActivity(),
            ViewModelFactory(appRepository, requireContext())
        )[GetDasBoardViewModel::class.java]


    }

    private fun getProductBannerData() {

        var call=RetrofitClient.apiservice.getProductDehBoard()
            call.enqueue(object : Callback<DashBoardResponse>{
                override fun onResponse(
                    call: Call<DashBoardResponse>,
                    response: Response<DashBoardResponse>
                ) {
                    if(response.isSuccessful) {
                        if (response.body()?.status == "true") {
                                    categoryList = response.body()!!.categorydata
                                    categoryAdapter =
                                        CategoryAdapter(requireContext(), categoryList, this@HomeFragment)
                                    val mLayoutManager: RecyclerView.LayoutManager =
                                        GridLayoutManager(requireContext(), 2)
                                    binding.recCategory.layoutManager = mLayoutManager
                                    binding.recCategory.adapter = categoryAdapter
                                }
                                bannerAdapterNew = BannerAdapterNew(
                                    requireContext(),
                                    response.body()!!.bannerdata,
                                    binding.viewpager
                                )

                                binding.viewpager.adapter = bannerAdapterNew
                                // bannerAdapterNew.Populistitem(BannerResponse.data)
                                binding.viewpager.clipToPadding = false
                                binding.viewpager.clipChildren = false
                                binding.viewpager.offscreenPageLimit = 3
                                binding.viewpager.getChildAt(0).overScrollMode =
                                    RecyclerView.OVER_SCROLL_NEVER

                                val compositePageTransformer = CompositePageTransformer()
                                compositePageTransformer.addTransformer(MarginPageTransformer(40))
                                compositePageTransformer.addTransformer { page, position ->
                                    val r = 1 - abs(position)
                                    page.scaleY = 0.85f + r * 0.15f
                                }
                                binding.viewpager.setPageTransformer(compositePageTransformer)

                                binding.viewpager.registerOnPageChangeCallback(object :
                                    OnPageChangeCallback() {
                                    override fun onPageSelected(position: Int) {
                                        super.onPageSelected(position)
                                        sliderHandler.removeCallbacks(sliderRunnable)
                                        sliderHandler.postDelayed(sliderRunnable, 3000)
                                    }
                                })

                                binding.pagerIndicator2
                                    .attachToPager(binding.viewpager)


                            }



                }

                override fun onFailure(call: Call<DashBoardResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })

        getDasBoardViewModel.getDasBoard()
        getDasBoardViewModel.response.observe(requireActivity(), Observer { event ->

            event.getContentIfNotHandled().let { resource ->

                when (resource) {
                    is Resource.Success -> {

                        resource.data.let { BannerResponse ->

                            if (BannerResponse!!.status == "true") {
                                categoryList = BannerResponse!!.categorydata
                                categoryAdapter =
                                    CategoryAdapter(requireContext(), categoryList, this)
                                val mLayoutManager: RecyclerView.LayoutManager =
                                    GridLayoutManager(requireContext(), 2)
                                binding.recCategory.layoutManager = mLayoutManager
                                binding.recCategory.adapter = categoryAdapter
                            }
                            bannerAdapterNew = BannerAdapterNew(
                                requireContext(),
                                BannerResponse!!.bannerdata,
                                binding.viewpager
                            )

                            binding.viewpager.adapter = bannerAdapterNew
                            // bannerAdapterNew.Populistitem(BannerResponse.data)
                            binding.viewpager.clipToPadding = false
                            binding.viewpager.clipChildren = false
                            binding.viewpager.offscreenPageLimit = 3
                            binding.viewpager.getChildAt(0).overScrollMode =
                                RecyclerView.OVER_SCROLL_NEVER

                            val compositePageTransformer = CompositePageTransformer()
                            compositePageTransformer.addTransformer(MarginPageTransformer(40))
                            compositePageTransformer.addTransformer { page, position ->
                                val r = 1 - abs(position)
                                page.scaleY = 0.85f + r * 0.15f
                            }
                            binding.viewpager.setPageTransformer(compositePageTransformer)

                            binding.viewpager.registerOnPageChangeCallback(object :
                                OnPageChangeCallback() {
                                override fun onPageSelected(position: Int) {
                                    super.onPageSelected(position)
                                    sliderHandler.removeCallbacks(sliderRunnable)
                                    sliderHandler.postDelayed(sliderRunnable, 3000)
                                }
                            })

                            binding.pagerIndicator2
                                .attachToPager(binding.viewpager)


                        }

                    }
                    is Resource.Error -> {
                        //                  Toast.makeText(requireContext(), "" + resource.message.toString(), Toast.LENGTH_SHORT).show()
                        //binding.progressbar.visibility=View.GONE
                    }
                    is Resource.Loading -> {
                        //binding.progressbar.visibility=View.VISIBLE
                    }

                }
            }


        })

    }


    private fun getBannerData() {

        getDasBoardViewModel.getDasBoard()
        getDasBoardViewModel.response.observe(requireActivity(), Observer { event ->

            event.getContentIfNotHandled().let { resource ->

                when (resource) {
                    is Resource.Success -> {

                        resource.data.let { BannerResponse ->

                            if (BannerResponse!!.status == "true") {
                                categoryList = BannerResponse!!.categorydata
                                categoryAdapter =
                                    CategoryAdapter(requireContext(), categoryList, this)
                                val mLayoutManager: RecyclerView.LayoutManager =
                                    GridLayoutManager(requireContext(), 2)
                                binding.recCategory.layoutManager = mLayoutManager
                                binding.recCategory.adapter = categoryAdapter
                            }
                            bannerAdapterNew = BannerAdapterNew(
                                requireContext(),
                                BannerResponse!!.bannerdata,
                                binding.viewpager
                            )

                            binding.viewpager.adapter = bannerAdapterNew
                            // bannerAdapterNew.Populistitem(BannerResponse.data)
                            binding.viewpager.clipToPadding = false
                            binding.viewpager.clipChildren = false
                            binding.viewpager.offscreenPageLimit = 3
                            binding.viewpager.getChildAt(0).overScrollMode =
                                RecyclerView.OVER_SCROLL_NEVER

                            val compositePageTransformer = CompositePageTransformer()
                            compositePageTransformer.addTransformer(MarginPageTransformer(40))
                            compositePageTransformer.addTransformer { page, position ->
                                val r = 1 - abs(position)
                                page.scaleY = 0.85f + r * 0.15f
                            }
                            binding.viewpager.setPageTransformer(compositePageTransformer)

                            binding.viewpager.registerOnPageChangeCallback(object :
                                OnPageChangeCallback() {
                                override fun onPageSelected(position: Int) {
                                    super.onPageSelected(position)
                                    sliderHandler.removeCallbacks(sliderRunnable)
                                    sliderHandler.postDelayed(sliderRunnable, 3000)
                                }
                            })

                            binding.pagerIndicator2
                                .attachToPager(binding.viewpager)


                        }

                    }
                    is Resource.Error -> {
                        //                  Toast.makeText(requireContext(), "" + resource.message.toString(), Toast.LENGTH_SHORT).show()
                        //binding.progressbar.visibility=View.GONE
                    }
                    is Resource.Loading -> {
                        //binding.progressbar.visibility=View.VISIBLE
                    }

                }
            }


        })

    }

    override fun gotoCategoryFragmentPage(categoryList: CategoryList) {
        val bundle = bundleOf(
            "Id" to categoryList.ID
        )

        // findNavController().navigate(R.id.action_homeFragment_to_productFragment,bundle)
        requireActivity().nextFragment(R.id.action_homeFragment_to_productFragment, bundle)

    }


}