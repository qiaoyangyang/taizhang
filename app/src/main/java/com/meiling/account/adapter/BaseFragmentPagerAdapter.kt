package com.meiling.account.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class BaseFragmentPagerAdapter(
    fm: FragmentManager, lifecycle: Lifecycle,
    private val fragments: MutableList<Fragment>
) : FragmentStateAdapter(fm, lifecycle) {


    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

}
