package com.perqin.angumi.data.room

import androidx.room.TypeConverter
import kotlinx.datetime.Instant

class DatetimeConverters {
    @TypeConverter
    fun instantToLong(instant: Instant) = instant.toEpochMilliseconds()

    @TypeConverter
    fun longToInstant(long: Long) = Instant.fromEpochMilliseconds(long)
}
