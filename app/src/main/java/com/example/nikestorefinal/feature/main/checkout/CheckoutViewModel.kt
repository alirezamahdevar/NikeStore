package com.example.nikestorefinal.feature.main.checkout

import androidx.lifecycle.MutableLiveData
import com.example.nikestorefinal.common.NikeSingleObserver
import com.example.nikestorefinal.common.NikeViewModel
import com.example.nikestorefinal.common.asyncNetWorkRequest
import com.example.nikestorefinal.data.Checkout
import com.example.nikestorefinal.data.repo.order.OrderRepository

class CheckoutViewModel(orderId: Int, orderRepository: OrderRepository) :
    NikeViewModel() {
    val checkoutLiveData = MutableLiveData<Checkout>()

    init {

        orderRepository.checkout(orderId)
            .asyncNetWorkRequest()
            .subscribe(object : NikeSingleObserver<Checkout>(compositeDisposable) {
                override fun onSuccess(t: Checkout) {
                    checkoutLiveData.value = t
                }

            })
    }
}