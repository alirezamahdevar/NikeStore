package com.example.nikestorefinal.data.repo.source

import com.example.nikestorefinal.data.Comment
import io.reactivex.Single

interface CommentDataSource {

    fun getAll(productId:Int): Single<List<Comment>>

    fun insert(): Single<Comment>
}