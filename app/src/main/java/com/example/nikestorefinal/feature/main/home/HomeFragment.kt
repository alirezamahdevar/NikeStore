package com.example.nikestorefinal.feature.main.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestorefinal.EXTRA_KEY_DATA
import com.example.nikestorefinal.R
import com.example.nikestorefinal.common.NikeFragment
import com.example.nikestorefinal.common.convertDpToPixel
import com.example.nikestorefinal.data.Product
import com.example.nikestorefinal.data.SORT_LATEST
import com.example.nikestorefinal.feature.main.common.ProductListAdapter
import com.example.nikestorefinal.feature.main.common.VIEW_TYPE_ROUND
import com.example.nikestorefinal.feature.main.list.ProductListActivity
import com.example.nikestorefinal.feature.main.main.BannerSliderAdapter
import com.example.nikestorefinal.feature.main.product.ProductDetailActivity
import kotlinx.android.synthetic.main.fargment_home.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class HomeFragment : NikeFragment(), ProductListAdapter.ProductEventListener {
    val homeViewModel: HomeViewModel by viewModel()
    val productListAdapter: ProductListAdapter by inject { parametersOf(VIEW_TYPE_ROUND) }




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fargment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        latestProduct_rv.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        latestProduct_rv.adapter = productListAdapter
        productListAdapter.productEvenListener = this


        homeViewModel.productsLiveData.observe(viewLifecycleOwner) {
            productListAdapter.products = it as ArrayList<Product>
        }
        viewLatestProductsBtn.setOnClickListener {
            startActivity(Intent(requireContext(), ProductListActivity::class.java).apply {
                putExtra(EXTRA_KEY_DATA, SORT_LATEST)
            })
        }

        homeViewModel.progressBarLiveData.observe(viewLifecycleOwner) {
            setProgressIndicator(it)
        }
        homeViewModel.bannersLiveData.observe(viewLifecycleOwner) {
            Timber.i(it.toString())

            bannerSliderViewPager.post {
                val bannerSliderAdapter = BannerSliderAdapter(this, it)
                bannerSliderViewPager.adapter = bannerSliderAdapter
                val viewPagerHeight = (((bannerSliderViewPager.measuredWidth - convertDpToPixel(
                    32f,
                    requireContext()
                )) * 173) / 328).toInt()

                val layoutParams = bannerSliderViewPager.layoutParams
                layoutParams.height = viewPagerHeight
                bannerSliderViewPager.layoutParams = layoutParams
                sliderIndicator.setViewPager2(bannerSliderViewPager)

            }
        }

    }


    override fun onProductClick(product: Product) {
        startActivity(Intent(requireContext(), ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA, product)


        })

    }

    override fun onFavoriteBtnClick(product: Product) {
        homeViewModel.addProductToFavorites(product)
    }
}