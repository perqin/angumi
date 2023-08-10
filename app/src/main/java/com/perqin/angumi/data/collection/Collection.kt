package com.perqin.angumi.data.collection

import com.perqin.angumi.data.models.CollectionType
import com.perqin.angumi.data.models.SlimSubject
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
    // TODO: What's this?
    val epStatus: Int,
    // TODO: What's this?
    val volStatus: Int,
    val updatedAt: String,
    val private: Boolean,
    val subject: SlimSubject,
)
