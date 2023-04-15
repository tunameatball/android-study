package com.kkh.webtoon_app

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(private val mainActivity: MainActivity) : FragmentStateAdapter(mainActivity) {

    private val urls: List<String> = ArrayList<String>().apply {
        add("https://comic.naver.com/webtoon/detail?titleId=783053&no=75&weekday=mon")
        add("https://comic.naver.com/webtoon/detail?titleId=783053&no=74&weekday=mon")
        add("https://comic.naver.com/webtoon/detail?titleId=783053&no=73&weekday=mon")
    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return WebViewFragment(position, urls[position]).apply {
            listener = mainActivity
        }
    }

}