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
    // TODO: What's this?
    val epStatus: Int,
    // TODO: What's this?
    val volStatus: Int,
    val updatedAt: Instant,
    val private: Boolean,
)
