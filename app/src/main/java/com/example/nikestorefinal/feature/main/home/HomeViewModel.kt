package com.example.nikestorefinal.feature.main.home

import androidx.lifecycle.MutableLiveData
import com.example.nikestorefinal.common.NikeCompletableObserver
import com.example.nikestorefinal.common.NikeSingleObserver
import com.example.nikestorefinal.common.NikeViewModel
import com.example.nikestorefinal.data.Banner
import com.example.nikestorefinal.data.Product
import com.example.nikestorefinal.data.SORT_LATEST
import com.example.nikestorefinal.data.repo.BannerRepository
import com.example.nikestorefinal.data.repo.ProductRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel(
    private val productRepository: ProductRepository,
    bannerRepository: BannerRepository
) :
    NikeViewModel() {
    val productsLiveData = MutableLiveData<List<Product>>()
    var bannersLiveData = MutableLiveData<List<Banner>>()


    init {
        progressBarLiveData.value = true
        productRepository.getProducts(SORT_LATEST)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t: List<Product>) {
                    productsLiveData.value = t
                }

            })


        bannerRepository.getBanners()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : NikeSingleObserver<List<Banner>>(compositeDisposable) {
                override fun onSuccess(t: List<Banner>) {
                    bannersLiveData.value = t
                }
            })
    }

    fun addProductToFavorites(product: Product) {
        if (product.isFavorite)
            productRepository.deleteFromFavorites(product)
                .subscribeOn(Schedulers.io())
                .subscribe(object : NikeCompletableObserver(compositeDisposable) {
                    override fun onComplete() {
                        product.isFavorite = false
                    }

                })
        productRepository.addToFavorites(product)
            .subscribeOn(Schedulers.io())
            .subscribe(object : NikeCompletableObserver(compositeDisposable) {
                override fun onComplete() {
                    product.isFavorite = true
                }
            })


    }
}

