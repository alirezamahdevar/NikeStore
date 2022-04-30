package com.example.nikestorefinal.data.repo.source

import com.example.nikestorefinal.data.MessageResponse
import com.example.nikestorefinal.data.TokenResponse
import io.reactivex.Single

interface UserDataSource {
    fun login(userName: String, password: String): Single<TokenResponse>
    fun signUp(userName: String, password: String): Single<MessageResponse>
    fun loadToken()
    fun saveToken(token: String, refreshToken: String)

    fun saveUsername(userName: String)
    fun getUserName(): String
    fun signOut()

}