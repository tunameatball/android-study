package com.kkh.webtoon_app

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(private val mainActivity: MainActivity) : FragmentStateAdapter(mainActivity) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> {
                WebViewFragment(position, "https://comic.naver.com/webtoon/detail?titleId=783053&no=75&weekday=mon").apply {
                    listener = mainActivity
                }

            }
            1 -> {
                WebViewFragment(position, "https://comic.naver.com/webtoon/detail?titleId=783053&no=74&weekday=mon").apply {
                    listener = mainActivity
                }
            }
            2 -> {
                WebViewFragment(position, "https://comic.naver.com/webtoon/detail?titleId=783053&no=73&weekday=mon").apply {
                    listener = mainActivity
                }
            }
            else -> {
                WebViewFragment(position, "https://comic.naver.com/webtoon/detail?titleId=783053&no=1&weekday=mon").apply {
                    listener = mainActivity
                }
            }
        }
    }

}