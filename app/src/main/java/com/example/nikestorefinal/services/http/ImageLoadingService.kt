package com.example.nikestorefinal.services.http

import com.example.nikestorefinal.view.NikeImageView

interface ImageLoadingService {
    fun load(imageView: NikeImageView,imageUrl:String)
}