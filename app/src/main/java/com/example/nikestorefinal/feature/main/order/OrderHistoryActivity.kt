package com.example.nikestorefinal.feature.main.order

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestorefinal.R
import com.example.nikestorefinal.common.NikeActivity
import kotlinx.android.synthetic.main.activity_order_history.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class OrderHistoryActivity : NikeActivity() {
    val viewModel: OrderHistoryViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_history)

        viewModel.progressBarLiveData.observe(this) {
            setProgressIndicator(it)
        }

        orderHistoryRv.layoutManager=LinearLayoutManager(this, RecyclerView.VERTICAL,false)

        viewModel.orders.observe(this){
            orderHistoryRv.adapter=OrderHistoryItemAdapter(this,it)
        }
        toolbarView.onBackButtonClickListener=View.OnClickListener {
            finish()
        }
    }
}