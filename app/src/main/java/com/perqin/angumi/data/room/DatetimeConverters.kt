package com.perqin.angumi.data.room

import androidx.room.TypeConverter
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

class DatetimeConverters {
    @TypeConverter
    fun instantToLong(instant: Instant) = instant.toEpochMilliseconds()

    @TypeConverter
    fun longToInstant(long: Long) = Instant.fromEpochMilliseconds(long)

    @TypeConverter
    fun localDateToString(localDate: LocalDate) = localDate.toString()

    @TypeConverter
    fun stringToLocalDate(string: String) = LocalDate.parse(string)
}
