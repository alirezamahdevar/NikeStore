package com.example.nikestorefinal.feature.main.product.comment

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestorefinal.EXTRA_KEY_ID
import com.example.nikestorefinal.R
import com.example.nikestorefinal.common.NikeActivity
import com.example.nikestorefinal.data.Comment
import com.example.nikestorefinal.feature.main.product.CommentAdapter
import kotlinx.android.synthetic.main.activity_comment_list.*
import kotlinx.android.synthetic.main.activity_product_detail.commentsRv
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CommentListActivity : NikeActivity() {
    val viewModel: CommentListViewModel by viewModel {
        parametersOf(
            intent.extras!!.getInt(
                EXTRA_KEY_ID
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment_list)
        viewModel.progressBarLiveData.observe(this){
            setProgressIndicator(it)
        }
        viewModel.commentsLiveData.observe(this) {
            val adapter = CommentAdapter(true)
            commentsRv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            adapter.comments = it as ArrayList<Comment>
            commentsRv.adapter = adapter

        }
        commentListToolbar.onBackButtonClickListener = View.OnClickListener { finish() }


    }
}