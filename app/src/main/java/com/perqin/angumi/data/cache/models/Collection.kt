package com.perqin.angumi.data.cache.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.perqin.angumi.data.models.CollectionType
import com.perqin.angumi.data.models.SubjectType
import kotlinx.datetime.Instant

@Entity
data class Collection(
    @PrimaryKey
    val subjectId: Int,
    val subjectType: SubjectType,
    val rate: Int,
    val type: CollectionType,
    val comment: String?,
    val tags: List<String>,
    // Progress of the episode
    val epStatus: Int,
    // Progress of the volume (for books)
    val volStatus: Int,
    val updatedAt: Instant,
    val private: Boolean,
)
