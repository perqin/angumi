package com.perqin.angumi.data.user

class UserRepo(private val localSource: UserLocalSource, private val remoteSource: UserRemoteSource) {
    val me = localSource.me

    suspend fun ensureMe(): User {
        return localSource.me.value ?: run {
            val me = remoteSource.getMe()
            localSource.updateMe(me)
            me
        }
    }
}
