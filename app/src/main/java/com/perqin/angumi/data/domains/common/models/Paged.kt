package com.perqin.angumi.data.domains.common.models

import kotlinx.serialization.Serializable

@Serializable
data class Paged<T>(
    val total: Int,
    val limit: Int,
    val offset: Int,
    val data: List<T>,
)

fun <T> emptyPage() = Paged<T>(0, 0, 0, emptyList())
