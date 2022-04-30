package com.example.nikestorefinal.feature.main.product.comment

import androidx.lifecycle.MutableLiveData
import com.example.nikestorefinal.common.NikeSingleObserver
import com.example.nikestorefinal.common.NikeViewModel
import com.example.nikestorefinal.common.asyncNetWorkRequest
import com.example.nikestorefinal.data.Comment
import com.example.nikestorefinal.data.repo.CommentRepository

class CommentListViewModel(productId: Int, commentRepository: CommentRepository) : NikeViewModel() {
    val commentsLiveData = MutableLiveData<List<Comment>>()

    init {
        progressBarLiveData.value=true
        commentRepository.getAll(productId)
            .asyncNetWorkRequest()
            .doFinally { progressBarLiveData.value=false }
            .subscribe(object : NikeSingleObserver<List<Comment>>(compositeDisposable) {
                override fun onSuccess(t: List<Comment>) {
                    commentsLiveData.value = t
                }

            })
    }
}