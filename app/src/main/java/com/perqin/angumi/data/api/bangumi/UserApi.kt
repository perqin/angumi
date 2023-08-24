package com.perqin.angumi.data.api.bangumi

import com.perqin.angumi.data.domains.user.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class UserApi(private val client: HttpClient) {
    suspend fun getMe(): User {
        return client.get("$ENDPOINT_V0/me").body()
    }
}