package com.perqin.angumi.data.api.bangumi

import com.perqin.angumi.data.api.bangumi.models.Collection
import com.perqin.angumi.data.domains.common.models.CollectionType
import com.perqin.angumi.data.domains.common.models.Paged
import com.perqin.angumi.data.domains.common.models.PagedQuery
import com.perqin.angumi.data.domains.common.models.SubjectType
import com.perqin.angumi.data.domains.common.models.pagedQueryParameters
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class CollectionApi(private val client: HttpClient) {
    suspend fun getCollections(
        username: String,
        pagedQuery: PagedQuery,
        subjectType: SubjectType,
        type: CollectionType,
    ): Paged<Collection> {
        return client.get("$ENDPOINT_V0/users/$username/collections") {
            pagedQueryParameters(pagedQuery)
            parameter("subject_type", subjectType)
            parameter("type", type)
        }.body()
    }
}
