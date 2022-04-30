package com.example.nikestorefinal.data.repo.source

import com.example.nikestorefinal.data.Banner
import io.reactivex.Single

interface BannerDataSource {
    fun getBanners():Single<List<Banner>>

}