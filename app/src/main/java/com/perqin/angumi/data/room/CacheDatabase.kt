package com.perqin.angumi.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.perqin.angumi.data.user.User
import com.perqin.angumi.data.user.UserDao

@Database(entities = [User::class], version = 1)
@TypeConverters(ProtobufTypeConverters::class)
abstract class CacheDatabase : RoomDatabase() {
    abstract val userDao: UserDao
}
