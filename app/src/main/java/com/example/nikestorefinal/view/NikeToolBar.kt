package com.example.nikestorefinal.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.example.nikestorefinal.R

import kotlinx.android.synthetic.main.view_toolbar.view.*

class NikeToolbar(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {
    var onBackButtonClickListener: View.OnClickListener? = null
        set(value) {
            field=value
            backBtnCustomToolbar.setOnClickListener(onBackButtonClickListener)
        }
    init {
        inflate(context, R.layout.view_toolbar, this)

        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.NikeToolBar)
            val title = a.getString(R.styleable.NikeToolBar_nt_title)
            if (title != null && title.isNotEmpty())
                toolBarTitleTvCustomToolbar.text = title

            a.recycle()

        }
    }
}