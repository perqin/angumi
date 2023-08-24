package com.perqin.angumi.data.room

import androidx.room.TypeConverter
import com.perqin.angumi.data.domains.common.models.SlimSubjectImages
import com.perqin.angumi.data.domains.common.models.SlimSubjectTag
import com.perqin.angumi.data.domains.user.UserAvatar
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

    @TypeConverter
    fun stringListToBytes(stringList: List<String>): ByteArray {
        return ProtoBuf.encodeToByteArray(stringList)
    }

    @TypeConverter
    fun bytesToStringList(byteArray: ByteArray): List<String> {
        return ProtoBuf.decodeFromByteArray(byteArray)
    }

    @TypeConverter
    fun slimSubjectImagesToBytes(slimSubjectImages: SlimSubjectImages): ByteArray {
        return ProtoBuf.encodeToByteArray(slimSubjectImages)
    }

    @TypeConverter
    fun bytesToSlimSubjectImages(byteArray: ByteArray): SlimSubjectImages {
        return ProtoBuf.decodeFromByteArray(byteArray)
    }

    @TypeConverter
    fun slimSubjectTagsToBytes(slimSubjectTags: List<SlimSubjectTag>): ByteArray {
        return ProtoBuf.encodeToByteArray(slimSubjectTags)
    }

    @TypeConverter
    fun bytesToSlimSubjectTags(byteArray: ByteArray): List<SlimSubjectTag> {
        return ProtoBuf.decodeFromByteArray(byteArray)
    }
}
