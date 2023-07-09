package com.perqin.angumi.data.models

import kotlinx.serialization.Serializable

@Serializable
data class SlimSubjectImages(
    val large: String,
    val medium: String,
    val small: String,
    val grid: String,
    val common: String,
)
