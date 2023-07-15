package com.perqin.angumi.ui.collections

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.perqin.angumi.data.models.SubjectType

class CollectionsPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    val pages = listOf(
        SubjectType.BOOK,
        SubjectType.ANIME,
        SubjectType.MUSIC,
        SubjectType.GAME,
        SubjectType.SANJIGEN,
    )

    override fun getItemCount() = pages.size

    override fun createFragment(position: Int): Fragment {
        return CollectionsPageFragment.newInstance(pages[position])
    }
}
