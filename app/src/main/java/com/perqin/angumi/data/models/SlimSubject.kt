package com.perqin.angumi.data.models

import kotlinx.serialization.Serializable

@Serializable
data class SlimSubject(
    val id: Int,
    val name: String,
    val nameCn: String,
    val images: SlimSubjectImages,
)
