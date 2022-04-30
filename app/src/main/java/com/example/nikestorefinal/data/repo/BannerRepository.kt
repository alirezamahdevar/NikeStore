package com.example.nikestorefinal.data.repo

import com.example.nikestorefinal.data.Banner
import io.reactivex.Single

interface BannerRepository {
    fun getBanners():Single<List<Banner>>

}