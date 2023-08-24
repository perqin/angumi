package com.perqin.angumi.data.domains.user

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM User WHERE id = :id")
    suspend fun getById(id: Int): User?

    @Query("SELECT * FROM User WHERE id = :id")
    fun flowById(id: Int): Flow<User?>

    @Upsert
    suspend fun upsert(user: User)
}
