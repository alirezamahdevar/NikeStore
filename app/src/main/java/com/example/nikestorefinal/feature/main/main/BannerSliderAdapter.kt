package com.example.nikestorefinal.feature.main.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.nikestorefinal.EXTRA_KEY_DATA
import com.example.nikestorefinal.data.Banner

class BannerSliderAdapter(fragment: Fragment, val banners: List<Banner>) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = banners.size

    override fun createFragment(position: Int): Fragment =BannerFragment.newInstance(banners[position])

}