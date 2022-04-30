package com.example.nikestorefinal.data.repo.source

import com.example.nikestorefinal.data.Comment
import com.example.nikestorefinal.services.http.http.ApiService
import io.reactivex.Single
import javax.sql.CommonDataSource

class CommentRemoteDataSource(val apiService: ApiService):CommentDataSource {
    override fun getAll(productId:Int): Single<List<Comment>> =apiService.getComments(productId)

    override fun insert(): Single<Comment> {
        TODO("Not yet implemented")
    }
}