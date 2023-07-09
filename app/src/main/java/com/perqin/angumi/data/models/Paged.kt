package com.perqin.angumi.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Paged<T>(
    val total: Int,
    val limit: Int,
    val offset: Int,
    val data: List<T>,
)
