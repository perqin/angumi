package com.perqin.angumi.data.user

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserLocalSource {
    private val _me = MutableStateFlow(null as User?)
    val me: StateFlow<User?> = _me

    suspend fun updateMe(user: User) {
        _me.emit(user)
    }
}
