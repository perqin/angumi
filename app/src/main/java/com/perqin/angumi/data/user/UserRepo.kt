package com.perqin.angumi.data.user

class UserRepo(private val localSource: UserLocalSource, private val remoteSource: UserRemoteSource) {
    suspend fun loadMe() {
        val me = remoteSource.getMe()
        localSource.updateMe(me)
    }
}
