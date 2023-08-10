package com.perqin.angumi.ui.collections

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.perqin.angumi.data.cache.models.CollectionWithSlimSubject
import com.perqin.angumi.databinding.CollectionItemBinding

class CollectionsAdapter(private val holderFragment: Fragment) :
    PagingDataAdapter<CollectionWithSlimSubject, CollectionsAdapter.ViewHolder>(
        object :
            DiffUtil.ItemCallback<CollectionWithSlimSubject>() {
            override fun areItemsTheSame(
                oldItem: CollectionWithSlimSubject,
                newItem: CollectionWithSlimSubject
            ): Boolean {
                return oldItem.collection.subjectId == newItem.collection.subjectId
            }

            override fun areContentsTheSame(
                oldItem: CollectionWithSlimSubject,
                newItem: CollectionWithSlimSubject
            ): Boolean {
                return oldItem == newItem
            }
        }) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CollectionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position) ?: return
        Glide.with(holderFragment).load(data.subject.images.common).into(holder.coverImage)
        holder.nameText.text = data.subject.nameCn
    }

    class ViewHolder(binding: CollectionItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val coverImage = binding.coverImage
        val nameText = binding.nameText
    }
}
