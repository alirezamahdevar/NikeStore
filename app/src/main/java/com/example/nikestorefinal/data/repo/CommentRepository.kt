package com.example.nikestorefinal.data.repo

import com.example.nikestorefinal.data.Comment
import io.reactivex.Single

interface CommentRepository {

    fun getAll(productId:Int): Single<List<Comment>>

    fun insert(): Single<Comment>
}