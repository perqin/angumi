package com.perqin.angumi.data.user

import kotlinx.serialization.Serializable

@Serializable
data class UserAvatar(
    val large: String,
    val medium: String,
    val small: String,
)
