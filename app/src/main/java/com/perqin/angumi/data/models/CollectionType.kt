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
 * example: 3
 * 1: 想看
 * 2: 看过
 * 3: 在看
 * 4: 搁置
 * 5: 抛弃
 */
@Serializable(with = CollectionTypeSerializer::class)
enum class CollectionType(val value: Int, @StringRes val titleRes: Int) {
    WISH(1, R.string.title_wish),
    COLLECT(2, R.string.title_collect),
    DO(3, R.string.title_do),
    ON_HOLD(4, R.string.title_on_hold),
    DROPPED(5, R.string.title_dropped);

    companion object {
        fun fromValue(value: Int): CollectionType = when (value) {
            1 -> WISH
            2 -> COLLECT
            3 -> DO
            4 -> ON_HOLD
            6 -> DROPPED
            else -> throw ShouldNotReachException()
        }
    }

    override fun toString(): String {
        return value.toString()
    }
}

object CollectionTypeSerializer : KSerializer<CollectionType> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("CollectionType", PrimitiveKind.INT)

    override fun deserialize(decoder: Decoder): CollectionType {
        return CollectionType.fromValue(decoder.decodeInt())
    }

    override fun serialize(encoder: Encoder, value: CollectionType) {
        encoder.encodeInt(value.value)
    }
}