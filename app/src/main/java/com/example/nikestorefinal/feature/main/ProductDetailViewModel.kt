package com.example.nikestorefinal.feature.main

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.example.nikestorefinal.EXTRA_KEY_DATA
import com.example.nikestorefinal.common.NikeCompletableObserver
import com.example.nikestorefinal.common.NikeSingleObserver
import com.example.nikestorefinal.common.NikeViewModel
import com.example.nikestorefinal.common.asyncNetWorkRequest
import com.example.nikestorefinal.data.Comment
import com.example.nikestorefinal.data.Product
import com.example.nikestorefinal.data.repo.CartRepository
import com.example.nikestorefinal.data.repo.CommentRepository
import com.example.nikestorefinal.data.repo.ProductRepository
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers

class ProductDetailViewModel(
    bundle: Bundle,
    commentRepository: CommentRepository,
    val cartRepository: CartRepository
) :
    NikeViewModel() {
    val productLiveData = MutableLiveData<Product>()
    val commentsLiveData = MutableLiveData<List<Comment>>()

    init {
        productLiveData.value = bundle.getParcelable(EXTRA_KEY_DATA)
        progressBarLiveData.value = true
        commentRepository.getAll(productLiveData.value!!.id)
            .asyncNetWorkRequest()
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : NikeSingleObserver<List<Comment>>(compositeDisposable) {
                override fun onSuccess(t: List<Comment>) {
                    commentsLiveData.value = t
                }
            })
    }


    fun onAddToCartBtn():Completable = cartRepository.addToCart(productLiveData.value!!.id).ignoreElement()

}

