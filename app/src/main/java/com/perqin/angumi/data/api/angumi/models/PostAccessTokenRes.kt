package com.perqin.angumi.data.api.angumi.models

import kotlinx.serialization.Serializable

@Serializable
data class PostAccessTokenRes(
    val accessToken: String,
    val expiresIn: Long,
    val tokenType: String,
    val scope: String?,
    val refreshToken: String,
    val userId: Int?,
)
