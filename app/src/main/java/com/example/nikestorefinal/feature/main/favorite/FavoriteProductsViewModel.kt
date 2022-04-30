package com.example.nikestorefinal.feature.main.favorite

import androidx.lifecycle.MutableLiveData
import com.example.nikestorefinal.common.NikeCompletableObserver
import com.example.nikestorefinal.common.NikeSingleObserver
import com.example.nikestorefinal.common.NikeViewModel
import com.example.nikestorefinal.data.Product
import com.example.nikestorefinal.data.repo.ProductRepository
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class FavoriteProductsViewModel(private val productRepository: ProductRepository) :
    NikeViewModel() {
    val productsLiveData=MutableLiveData<List<Product>>()
    init {
        productRepository.getFavoriteProducts()
            .subscribeOn(Schedulers.io())
            .subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t: List<Product>) {

                    productsLiveData.postValue(t)
                }

            })
    }

    fun removeFromFavorites(product: Product) {
        productRepository.deleteFromFavorites(product)
            .subscribeOn(Schedulers.io())
            .subscribe(object : NikeCompletableObserver(compositeDisposable) {
                override fun onComplete() {
                    Timber.i("removeFromFavorites completed")
                }

            })

    }
}