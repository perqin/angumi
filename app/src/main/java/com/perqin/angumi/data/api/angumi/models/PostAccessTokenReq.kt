package com.perqin.angumi.data.api.angumi.models

import kotlinx.serialization.Serializable

@Serializable
data class PostAccessTokenReq(val code: String)
