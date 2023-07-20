package com.perqin.angumi.data.user

import kotlinx.coroutines.flow.Flow

class UserLocalSource(private val dao: UserDao) {
    suspend fun save(user: User) {
        dao.upsert(user)
    }

    fun flowById(id: Int): Flow<User?> = dao.flowById(id)
}
