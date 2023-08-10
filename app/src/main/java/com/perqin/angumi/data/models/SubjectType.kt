package com.perqin.angumi.data.models

import androidx.annotation.StringRes
import com.perqin.angumi.R
import com.perqin.angumi.utils.ShouldNotReachException
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * 条目类型
 *
 * 1 为 书籍
 * 2 为 动画
 * 3 为 音乐
 * 4 为 游戏
 * 6 为 三次元
 * 没有 5
 */
@Serializable(with = SubjectTypeSerializer::class)
enum class SubjectType(val value: Int, @StringRes val titleRes: Int) {
    BOOK(1, R.string.title_book),
    ANIME(2, R.string.title_anime),
    MUSIC(3, R.string.title_music),
    GAME(4, R.string.title_game),
    SANJIGEN(6, R.string.title_sanjigen);

    companion object {
        fun fromValue(value: Int): SubjectType = when (value) {
            1 -> BOOK
            2 -> ANIME
            3 -> MUSIC
            4 -> GAME
            6 -> SANJIGEN
            else -> throw ShouldNotReachException()
        }
    }

    override fun toString(): String {
        return value.toString()
    }
}

object SubjectTypeSerializer : KSerializer<SubjectType> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("SubjectType", PrimitiveKind.INT)

    override fun deserialize(decoder: Decoder): SubjectType {
        return SubjectType.fromValue(decoder.decodeInt())
    }

    override fun serialize(encoder: Encoder, value: SubjectType) {
        encoder.encodeInt(value.value)
    }
}
