package com.perqin.angumi.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.perqin.angumi.data.domains.subjectcollection.SlimSubject
import com.perqin.angumi.data.domains.subjectcollection.SubjectCollection
import com.perqin.angumi.data.domains.subjectcollection.SubjectCollectionDao
import com.perqin.angumi.data.domains.user.User
import com.perqin.angumi.data.domains.user.UserDao

@Database(entities = [User::class, SlimSubject::class, SubjectCollection::class], version = 1)
@TypeConverters(ProtobufTypeConverters::class, EnumTypeConverters::class, DatetimeConverters::class)
abstract class CacheDatabase : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val subjectCollectionDao: SubjectCollectionDao
}
