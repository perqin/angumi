package com.perqin.angumi.data.domains.common.models

import kotlinx.serialization.Serializable

@Serializable
data class SlimSubjectTag(
    val name: String,
    val count: Int,
)
