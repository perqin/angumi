package com.perqin.angumi.data.api.angumi.models

import kotlinx.serialization.Serializable

@Serializable
data class PostAccessTokenReq(
    val grantType: String,
    val code: String? = null,
    val refreshToken: String? = null,
)
