package com.perqin.angumi.ui.collections

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.perqin.angumi.data.models.SubjectType
import com.perqin.angumi.databinding.CollectionsTabContentItemBinding

class PageData(
    val type: SubjectType,
    var layoutManager: LayoutManager? = null,
    var adapter: CollectionsAdapter? = null,
)

class CollectionsPagerAdapter(private val holderFragment: Fragment) :
    RecyclerView.Adapter<CollectionsPagerAdapter.ViewHolder>() {
    val pages = listOf(
        PageData(SubjectType.BOOK),
        PageData(SubjectType.ANIME),
        PageData(SubjectType.MUSIC),
        PageData(SubjectType.GAME),
        PageData(SubjectType.SANJIGEN),
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CollectionsTabContentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = pages.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = pages[position]
        holder.recyclerView.layoutManager =
            data.layoutManager ?: LinearLayoutManager(holder.recyclerView.context).also {
                data.layoutManager = it
            }
        holder.recyclerView.adapter = data.adapter ?: CollectionsAdapter(holderFragment).also {
            data.adapter = it
        }
    }

    class ViewHolder(binding: CollectionsTabContentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val recyclerView = binding.recyclerView
    }
}
