package com.example.nikestorefinal.data.repo.order

import com.example.nikestorefinal.data.Checkout
import com.example.nikestorefinal.data.OrderHistoryItem
import com.example.nikestorefinal.data.SubmitOrderResult
import io.reactivex.Single

class OrderRepositoryImpl(val orderDataSource: OrderDataSource) : OrderRepository {
    override fun submit(
        firstName: String,
        lastName: String,
        postalCode: String,
        phoneNumber: String,
        address: String,
        paymentMethod: String
    ): Single<SubmitOrderResult> {
        return orderDataSource.submit(
            firstName,
            lastName,
            postalCode,
            phoneNumber,
            address,
            paymentMethod
        )
    }

    override fun checkout(orderId: Int): Single<Checkout> {
        return orderDataSource.checkout(orderId)
    }

    override fun list(): Single<List<OrderHistoryItem>> = orderDataSource.list()
}