package com.perqin.angumi.data.settings

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object SessionSerializer : Serializer<Session> {
    override val defaultValue: Session = Session.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): Session {
        try {
            return Session.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: Session, output: OutputStream) = t.writeTo(output)
}

val Context.sessionDataStore: DataStore<Session> by dataStore(
    fileName = "session.pb",
    serializer = SessionSerializer
)
