package com.example.nikestorefinal.data.repo.source

import com.example.nikestorefinal.data.AddToCartResponse
import com.example.nikestorefinal.data.CartItemCount
import com.example.nikestorefinal.data.CartResponse
import com.example.nikestorefinal.data.MessageResponse
import com.example.nikestorefinal.services.http.http.ApiService
import com.google.gson.JsonObject
import io.reactivex.Single

class CartRemoteDataSource(val apiService: ApiService) : CartDataSource {
    override fun addToCart(productId: Int): Single<AddToCartResponse> = apiService.addToCart(
        JsonObject().apply {
            addProperty("product_id", productId)
        }
    )

    override fun get(): Single<CartResponse> =
        apiService.getCart()

    override fun remove(cartItemId: Int): Single<MessageResponse> = apiService.removeItemFromCart(
        JsonObject().apply {
            addProperty("cart_item_id", cartItemId)
        }
    )

    override fun changeCount(cartItemId: Int, count: Int): Single<AddToCartResponse> =
        apiService.changeCount(JsonObject().apply {
            addProperty("cart_item_id", cartItemId)
            addProperty("count", count)

        })

    override fun getCartItemsCount(): Single<CartItemCount> =apiService.getCartItemsCount()
}