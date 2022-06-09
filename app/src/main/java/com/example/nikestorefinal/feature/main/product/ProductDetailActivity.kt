package com.example.nikestorefinal.feature.main.product

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestorefinal.EXTRA_KEY_ID
import com.example.nikestorefinal.R
import com.example.nikestorefinal.common.NikeActivity
import com.example.nikestorefinal.common.NikeCompletableObserver
import com.example.nikestorefinal.common.formatPrice
import com.example.nikestorefinal.data.Comment
import com.example.nikestorefinal.data.Product
import com.example.nikestorefinal.feature.main.ProductDetailViewModel
import com.example.nikestorefinal.feature.main.product.comment.CommentListActivity
import com.example.nikestorefinal.services.http.ImageLoadingService
import com.example.nikestorefinal.view.scroll.ObservableScrollViewCallbacks
import com.example.nikestorefinal.view.scroll.ScrollState
import com.google.android.material.snackbar.Snackbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_product_detail.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class ProductDetailActivity : NikeActivity() {
    val productDetailViewModel: ProductDetailViewModel by viewModel { parametersOf(intent.extras) }
    val imageLoadingService: ImageLoadingService by inject()
    val commentAdapter = CommentAdapter()
    val composDisposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        productDetailViewModel.productLiveData.observe(this) {
            imageLoadingService.load(productIvDetail, it.image)
            titleTv.text = it.title
            previosPriceTvDetail.text = formatPrice(it.previous_price)
            previosPriceTvDetail.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            currentPriceTvDetail.text = formatPrice(it.price)
            toolBarTitleTv.text = it.title
        }
        productDetailViewModel.progressBarLiveData.observe(this) {
            setProgressIndicator(it)
        }

        productDetailViewModel.commentsLiveData.observe(this) {
            Timber.i(it.toString())
            commentAdapter.comments = it as ArrayList<Comment>
            if (it.size > 3) {
                viewAllCommentBtn.visibility = View.VISIBLE
                viewAllCommentBtn.setOnClickListener {
                    startActivity(Intent(this, CommentListActivity::class.java).apply {
                        putExtra(EXTRA_KEY_ID, productDetailViewModel.productLiveData.value!!.id)
                    })
                }
            }
        }
        initViews()


    }

    fun initViews() {
        commentsRv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        commentsRv.adapter = commentAdapter
        commentsRv.isNestedScrollingEnabled = false


        productIvDetail.post {
            val productIvHeight = productIvDetail.height
            val toolbar = toolBarView
            val productImageView = productIvDetail
            observableScrollView.addScrollViewCallbacks(object : ObservableScrollViewCallbacks {
                override fun onScrollChanged(
                    scrollY: Int,
                    firstScroll: Boolean,
                    dragging: Boolean
                ) {
                    Timber.i("height is -> $productIvHeight ")
                    toolbar.alpha = scrollY.toFloat() / productIvHeight.toFloat()
                    productImageView.translationY = scrollY.toFloat() / 1.5f
                }

                override fun onDownMotionEvent() {

                }

                override fun onUpOrCancelMotionEvent(scrollState: ScrollState?) {

                }

            })
        }
        addToCatBtn.setOnClickListener {
            productDetailViewModel.onAddToCartBtn()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NikeCompletableObserver(composDisposable) {
                    override fun onComplete() {
                        showSnackBar(getString(R.string.succsses_add_to_cart))
                    }
                })

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        composDisposable.clear()
    }
}