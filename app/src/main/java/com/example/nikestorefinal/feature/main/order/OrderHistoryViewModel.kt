package com.example.nikestorefinal.feature.main.order

import androidx.lifecycle.MutableLiveData
import com.example.nikestorefinal.common.NikeSingleObserver
import com.example.nikestorefinal.common.NikeViewModel
import com.example.nikestorefinal.common.asyncNetWorkRequest
import com.example.nikestorefinal.data.OrderHistoryItem
import com.example.nikestorefinal.data.repo.order.OrderRepository

class OrderHistoryViewModel(orderRepository: OrderRepository) : NikeViewModel() {

    val orders = MutableLiveData<List<OrderHistoryItem>>()


    init {
        orderRepository.list().asyncNetWorkRequest().doFinally { progressBarLiveData.value = false }
            .subscribe(object : NikeSingleObserver<List<OrderHistoryItem>>(compositeDisposable) {
                override fun onSuccess(t: List<OrderHistoryItem>) {
                    orders.value=t
                }


            })
    }
}