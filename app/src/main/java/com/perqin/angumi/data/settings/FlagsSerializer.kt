package com.perqin.angumi.data.settings

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object FlagsSerializer : Serializer<Flags> {
    override val defaultValue: Flags = Flags.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): Flags {
        try {
            return Flags.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: Flags, output: OutputStream) = t.writeTo(output)
}

val Context.flagsDataStore: DataStore<Flags> by dataStore(
    fileName = "flags.pb",
    serializer = FlagsSerializer
)
