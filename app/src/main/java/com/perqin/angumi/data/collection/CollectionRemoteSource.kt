package com.perqin.angumi.data.collection

import com.perqin.angumi.data.api.bangumi.BangumiClient
import com.perqin.angumi.data.models.PagedQuery
import com.perqin.angumi.data.models.CollectionType
import com.perqin.angumi.data.models.SubjectType

class CollectionRemoteSource(private val client: BangumiClient) {
    suspend fun getCollections(
        username: String,
        subjectType: SubjectType,
        collectionType: CollectionType
    ) =
        client.collection.getCollections(username, PagedQuery(30, 0), subjectType, collectionType)
}
