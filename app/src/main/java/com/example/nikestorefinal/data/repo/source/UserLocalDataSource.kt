package com.example.nikestorefinal.data.repo.source

import android.content.SharedPreferences
import com.example.nikestorefinal.data.MessageResponse
import com.example.nikestorefinal.data.TokenContainer
import com.example.nikestorefinal.data.TokenResponse
import io.reactivex.Single

class UserLocalDataSource(val sharedPreference: SharedPreferences) : UserDataSource {
    override fun login(userName: String, password: String): Single<TokenResponse> {
        TODO("Not yet implemented")
    }

    override fun signUp(userName: String, password: String): Single<MessageResponse> {
        TODO("Not yet implemented")
    }

    override fun loadToken() {
        TokenContainer.update(
            sharedPreference.getString("access_token", null),
            sharedPreference.getString("refresh_token", null)
        )

    }

    override fun saveToken(token: String, refreshToken: String) {
        sharedPreference.edit().apply {
            putString("access_token", token)
            putString("refresh_token", refreshToken)
        }.apply()
    }

    override fun saveUsername(userName: String) {
        sharedPreference.edit().apply {
            putString("username", userName)
        }.apply()
    }

    override fun getUserName(): String = sharedPreference.getString("username", "") ?: ""
    override fun signOut() = sharedPreference.edit().apply {
        clear()
    }.apply()
}