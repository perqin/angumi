package com.perqin.angumi.ui.collections

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.perqin.angumi.data.collection.Collection
import com.perqin.angumi.databinding.CollectionItemBinding

class CollectionsAdapter(private val holderFragment: Fragment) :
    RecyclerView.Adapter<CollectionsAdapter.ViewHolder>() {
    var dataset = listOf<Collection>()
        set(value) {
            if (field == value) {
                // To avoid unnecessary update from the view model
                return
            }
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        CollectionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = dataset.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataset[position]
        Glide.with(holderFragment).load(data.subject.images.common).into(holder.coverImage)
        holder.nameText.text = data.subject.nameCn
    }

    class ViewHolder(binding: CollectionItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val coverImage = binding.coverImage
        val nameText = binding.nameText
    }
}