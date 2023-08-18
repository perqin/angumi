package com.perqin.angumi.data.collection

import com.perqin.angumi.data.cache.models.SlimSubject
import com.perqin.angumi.data.models.CollectionType
import com.perqin.angumi.data.models.SubjectType
import kotlinx.serialization.Serializable

// This is only defined for network API
@Serializable
data class Collection(
    val subjectId: Int,
    val subjectType: SubjectType,
    val rate: Int,
    val type: CollectionType,
    val comment: String?,
    val tags: List<String>,
    val epStatus: Int,
    val volStatus: Int,
    val updatedAt: String,
    val private: Boolean,
    val subject: SlimSubject,
)
