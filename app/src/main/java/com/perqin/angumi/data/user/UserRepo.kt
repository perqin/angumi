package com.perqin.angumi.data.user

class UserRepo(private val localSource: UserLocalSource, private val remoteSource: UserRemoteSource) {
    // TODO: Refactor to hold state with flow
    suspend fun loadMe(): User {
        val me = remoteSource.getMe()
        localSource.updateMe(me)
        return me
    }
}
