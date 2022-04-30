package com.example.nikestorefinal.data.repo.source

import com.example.nikestorefinal.data.Banner
import com.example.nikestorefinal.services.http.http.ApiService
import io.reactivex.Single

class BannerRemoteDataSource( var apiService: ApiService) : BannerDataSource {
    override fun getBanners(): Single<List<Banner>> = apiService.getBanners()
}