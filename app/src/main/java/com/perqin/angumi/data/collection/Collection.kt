package com.perqin.angumi.data.collection

import com.perqin.angumi.data.models.SlimSubject
import kotlinx.serialization.Serializable

@Serializable
data class Collection(
    val subjectId: Int,
    val subject: SlimSubject,
)
