package com.example.nikestorefinal.services.http

import com.example.nikestorefinal.view.NikeImageView
import com.facebook.drawee.view.SimpleDraweeView
import java.lang.IllegalStateException

class FrescoImageLoadingService:ImageLoadingService {
    override fun load(imageView: NikeImageView, imageUrl: String) {
        if (imageView is SimpleDraweeView)
            imageView.setImageURI(imageUrl)
        else
            throw  IllegalStateException("ImageView must be instance of SimpleDraweeView")
    }
}