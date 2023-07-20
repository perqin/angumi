package com.perqin.angumi.data.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class User(
    @PrimaryKey
    val id: Int,
    val username: String,
    val nickname: String,
    val sign: String,
    val avatar: UserAvatar,
)
