package com.perqin.angumi.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.perqin.angumi.data.cache.daos.CollectionDao
import com.perqin.angumi.data.cache.models.Collection
import com.perqin.angumi.data.cache.models.SlimSubject
import com.perqin.angumi.data.user.User
import com.perqin.angumi.data.user.UserDao

@Database(entities = [User::class, SlimSubject::class, Collection::class], version = 1)
@TypeConverters(ProtobufTypeConverters::class, EnumTypeConverters::class, DatetimeConverters::class)
abstract class CacheDatabase : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val collectionDao: CollectionDao
}
