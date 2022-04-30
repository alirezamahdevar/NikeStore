package com.example.nikestorefinal.feature.main.profile

import com.example.nikestorefinal.common.NikeViewModel
import com.example.nikestorefinal.data.TokenContainer
import com.example.nikestorefinal.data.repo.UserRepository

class ProfileViewModel(private val userRepository: UserRepository) : NikeViewModel() {
    val username: String
        get() = userRepository.getUserName()

    val isSignedIn: Boolean
        get() = TokenContainer.token !== null

    fun signOut() = userRepository.signOut()

}