package com.perqin.angumi.data.api.bangumi.models

import com.perqin.angumi.data.domains.common.models.CollectionType
import com.perqin.angumi.data.domains.common.models.SubjectType
import com.perqin.angumi.data.domains.subjectcollection.SlimSubject
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
