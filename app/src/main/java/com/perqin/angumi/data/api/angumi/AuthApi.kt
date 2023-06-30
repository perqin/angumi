package com.perqin.angumi.data.api.angumi

import com.perqin.angumi.data.api.angumi.models.PostAccessTokenReq
import com.perqin.angumi.data.api.angumi.models.PostAccessTokenRes
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class AuthApi(private val client: HttpClient) {
    suspend fun getAccessToken(code: String): PostAccessTokenRes {
        return client.post("${ENDPOINT}/access_token") {
            contentType(ContentType.Application.Json)
            setBody(PostAccessTokenReq(code))
        }.body()
    }
}
