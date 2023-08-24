package com.perqin.angumi.data.domains.user

import com.perqin.angumi.data.api.bangumi.BangumiClient

class UserRemoteSource(private val client: BangumiClient) {
    suspend fun getMe() = client.user.getMe()
}
