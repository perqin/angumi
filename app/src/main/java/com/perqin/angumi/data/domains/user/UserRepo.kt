package com.perqin.angumi.data.domains.user

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest

class UserRepo(
    private val localSource: UserLocalSource,
    private val remoteSource: UserRemoteSource
) {
    private val meId = MutableStateFlow(0)

    @OptIn(ExperimentalCoroutinesApi::class)
    val me = meId.flatMapLatest {
        localSource.flowById(it)
    }

    // Me ID should be set as soon as possible.
    // - For new sign-in, set it once signed in successfully
    // - For future app starts, set it once the app starts
    suspend fun setMeId(id: Int) {
        meId.emit(id)
    }

    suspend fun reloadMe() {
        localSource.save(remoteSource.getMe())
    }

    // Normally, the User data of me should be already saved to the cache. But the user may clear
    // the cache. In such case, we have to reload it first, so that other API depending on it works
    // as expected.
    // TODO: parallel calls issue
    suspend fun ensureMe(): User {
        return me.first() ?: run {
            val me = remoteSource.getMe()
            localSource.save(me)
            me
        }
    }
}
