package com.mobileprogramming.myfundamentalapp.adapter

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.mobileprogramming.myfundamentalapp.R
import com.mobileprogramming.myfundamentalapp.actfrag.FollowersFragment
import com.mobileprogramming.myfundamentalapp.actfrag.FollowingFragment

class ViewPagerAdapter(
    private val con: Context,
    fragmentManager: FragmentManager,
    data: Bundle
) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val bundle: Bundle = data

    @StringRes
    private val tabTitle = intArrayOf(R.string.followers, R.string.following)

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
        }
        fragment?.arguments = this.bundle
        return fragment as Fragment
    }

    override fun getCount(): Int = tabTitle.size

    override fun getPageTitle(position: Int): CharSequence {
        return con.resources.getString(tabTitle[position])
    }
}