package com.lxh.cookcommunity.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class CommonPagerAdapter(
    fm: FragmentManager,
    private val fragmentList: List<Fragment>,
    private val titleList: List<String>? = null
) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        if (titleList == null)
            return ""
        return titleList[position]
    }

}