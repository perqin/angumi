package com.perqin.angumi.data.room

import androidx.room.TypeConverter
import com.perqin.angumi.data.domains.common.models.CollectionType
import com.perqin.angumi.data.domains.common.models.SubjectType

/**
 * Room could convert enum values by default, but we still define these converters to ensure human-
 * readability for converted values.
 */
class EnumTypeConverters {
    @TypeConverter
    fun subjectTypeToInt(subjectType: SubjectType): Int = subjectType.value

    @TypeConverter
    fun intToSubjectType(int: Int): SubjectType = SubjectType.fromValue(int)

    @TypeConverter
    fun collectionTypeToInt(collectionType: CollectionType): Int = collectionType.value

    @TypeConverter
    fun intToCollectionType(int: Int): CollectionType = CollectionType.fromValue(int)
}
