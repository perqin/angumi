package com.perqin.angumi.data.domains.common.models

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.parameter

class PagedQuery(val limit: Int, val offset: Int)

fun HttpRequestBuilder.pagedQueryParameters(pagedQuery: PagedQuery) {
    parameter("limit", pagedQuery.limit)
    parameter("offset", pagedQuery.offset)
}
