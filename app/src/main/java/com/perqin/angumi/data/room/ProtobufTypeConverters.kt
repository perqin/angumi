package com.perqin.angumi.data.room

import androidx.room.TypeConverter
import com.perqin.angumi.data.user.UserAvatar
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray
import kotlinx.serialization.protobuf.ProtoBuf

@OptIn(ExperimentalSerializationApi::class)
class ProtobufTypeConverters {
    @TypeConverter
    fun userAvatarToBytes(userAvatar: UserAvatar): ByteArray {
        return ProtoBuf.encodeToByteArray(userAvatar)
    }

    @TypeConverter
    fun bytesToUserAvatar(byteArray: ByteArray): UserAvatar {
        return ProtoBuf.decodeFromByteArray(byteArray)
    }
}
