package com.perqin.angumi.ui.collections

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.perqin.angumi.R
import com.perqin.angumi.data.domains.common.models.SubjectType
import com.perqin.angumi.data.domains.subjectcollection.CollectionWithSlimSubject
import com.perqin.angumi.databinding.CollectionItemBinding
import java.time.format.TextStyle

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
        val context = holder.itemView.context
        val progress =
            if (data.subject.type === SubjectType.BOOK) data.collection.volStatus else data.collection.epStatus
        val max =
            if (data.subject.type === SubjectType.BOOK) data.subject.volumes else data.subject.eps
        Glide.with(holderFragment).load(data.subject.images.common).into(holder.coverImage)
        holder.nameText.text = data.subject.nameCn.ifEmpty { data.subject.name }
        holder.subtitleText.text = getSubtitleText(context, data)
        holder.progressButton.text = context.getString(R.string.title_progress_t, progress, max)
        holder.progressIndicator.max = max
        holder.progressIndicator.progress = progress
    }

    private fun getSubtitleText(context: Context, data: CollectionWithSlimSubject): String {
        val weekday = data.subject.date?.dayOfWeek?.run {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                getDisplayName(
                    TextStyle.SHORT_STANDALONE,
                    context.resources.configuration.locales[0]
                )
            } else
            // TODO: Support API 25
                null
        }
        return if (weekday != null)
            context.getString(R.string.title_collectionSubtitle_t, data.subject.score, weekday)
        else
            context.getString(R.string.title_collectionSubtitleNoDate_t, data.subject.score)
    }

    class ViewHolder(binding: CollectionItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val coverImage = binding.coverImage
        val nameText = binding.nameText
        val subtitleText = binding.subtitleText
        val progressButton = binding.progressButton
        val progressIndicator = binding.progressIndicator
    }
}
