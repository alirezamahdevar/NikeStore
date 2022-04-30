package com.example.nikestorefinal.feature.main.list

import androidx.lifecycle.MutableLiveData
import com.example.nikestorefinal.R
import com.example.nikestorefinal.common.NikeSingleObserver
import com.example.nikestorefinal.common.NikeViewModel
import com.example.nikestorefinal.common.asyncNetWorkRequest
import com.example.nikestorefinal.data.Product
import com.example.nikestorefinal.data.repo.ProductRepository

class ProductListViewModel(var sort: Int, val productRepository: ProductRepository) :
    NikeViewModel() {
    val productsLiveData = MutableLiveData<List<Product>>()
    val selectedSortTitleLiveData = MutableLiveData<Int>()
    val sortTitles = arrayOf(
        R.string.sortLatest,
        R.string.sortPopular,
        R.string.sortPriceHighToLow,
        R.string.sortPriceLowToHigh
    )


    init {

        getProducts()
        selectedSortTitleLiveData.value=sortTitles[sort]

    }

    fun getProducts() {
        progressBarLiveData.value = true
        productRepository.getProducts(sort)
            .asyncNetWorkRequest()
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t: List<Product>) {
                    productsLiveData.value = t
                }

            })

    }
    fun onSelectedSortChangedByUser(sort: Int){
        this.sort=sort
        this.selectedSortTitleLiveData.value=sortTitles[sort]
        getProducts()
    }
}