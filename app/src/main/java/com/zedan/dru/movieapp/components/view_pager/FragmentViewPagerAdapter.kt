package com.zedan.dru.movieapp.components.view_pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.DiffUtil
import androidx.viewpager2.adapter.FragmentStateAdapter


class FragmentViewPagerAdapter : FragmentStateAdapter {

    private val fragments by lazy { mutableListOf<Fragment>() }

    constructor(fragment: Fragment) : super(fragment)

    constructor(
        fragmentManager: FragmentManager,
        lifecycle: Lifecycle,
    ) : super(fragmentManager, lifecycle)


    fun submit(fragments: Collection<Fragment>) {
        val diffCallback = FragmentCallback(this.fragments, fragments)
        val diffCourses = DiffUtil.calculateDiff(diffCallback)
        this.fragments.clear()
        this.fragments.addAll(fragments)
        diffCourses.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = fragments.size
    override fun createFragment(position: Int): Fragment = fragments.elementAt(position)
}

private class FragmentCallback(private val oldList: Collection<Fragment>, private val newList: Collection<Fragment>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList.elementAt(oldItemPosition) == newList.elementAt(newItemPosition)
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return areItemsTheSame(oldItemPosition, newItemPosition)
    }
}