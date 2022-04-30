package com.example.nikestorefinal.data.repo

import com.example.nikestorefinal.data.Banner
import com.example.nikestorefinal.data.repo.source.BannerDataSource
import com.example.nikestorefinal.data.repo.source.BannerRemoteDataSource
import io.reactivex.Single

class BannerRepositoryImpl(val bannerRemoteDataSource: BannerDataSource) : BannerRepository{
    override fun getBanners(): Single<List<Banner>> = bannerRemoteDataSource.getBanners()
}