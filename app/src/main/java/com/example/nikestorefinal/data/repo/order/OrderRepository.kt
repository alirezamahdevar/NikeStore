package com.example.nikestorefinal.data.repo.order

import androidx.room.FtsOptions
import com.example.nikestorefinal.data.Checkout
import com.example.nikestorefinal.data.OrderHistoryItem
import com.example.nikestorefinal.data.SubmitOrderResult
import io.reactivex.Single

interface OrderRepository {

    fun submit(
        firstName: String,
        lastName: String,
        postalCode: String,
        phoneNumber: String,
        address: String,
        paymentMethod: String
    ): Single<SubmitOrderResult>

    fun checkout(orderId: Int): Single<Checkout>

    fun list():Single<List<OrderHistoryItem>>
}