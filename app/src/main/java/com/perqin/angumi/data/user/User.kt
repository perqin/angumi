package com.perqin.angumi.data.user

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val username: String,
    val nickname: String,
    val sign: String,
    val avatar: UserAvatar,
)
