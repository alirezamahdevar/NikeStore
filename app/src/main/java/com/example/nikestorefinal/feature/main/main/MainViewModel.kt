package com.example.nikestorefinal.feature.main.main

import com.example.nikestorefinal.common.NikeSingleObserver
import com.example.nikestorefinal.common.NikeViewModel
import com.example.nikestorefinal.data.CartItemCount
import com.example.nikestorefinal.data.TokenContainer
import com.example.nikestorefinal.data.repo.CartRepository
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus

class MainViewModel(private val cartRepository: CartRepository) : NikeViewModel() {
    fun getCartItemsCount() {
        if (!TokenContainer.token.isNullOrEmpty()) {
            cartRepository.getCartItemsCount()
                .subscribeOn(Schedulers.io())
                .subscribe(object :NikeSingleObserver<CartItemCount>(compositeDisposable){
                    override fun onSuccess(t: CartItemCount) {
                        EventBus.getDefault().postSticky(t)
                    }
            })
        }
    }
}