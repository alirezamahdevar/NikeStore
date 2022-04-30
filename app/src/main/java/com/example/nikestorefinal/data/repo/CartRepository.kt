package com.example.nikestorefinal.data.repo

import com.example.nikestorefinal.data.AddToCartResponse
import com.example.nikestorefinal.data.CartItemCount
import com.example.nikestorefinal.data.CartResponse
import com.example.nikestorefinal.data.MessageResponse
import io.reactivex.Single

interface CartRepository {
    fun addToCart(productId:Int): Single<AddToCartResponse>
    fun get():Single<CartResponse>
    fun remove(cartItemId:Int):Single<MessageResponse>
    fun changeCount(cartItemId: Int,count:Int):Single<AddToCartResponse>
    fun getCartItemsCount():Single<CartItemCount>
}