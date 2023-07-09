package com.perqin.angumi.data.collection

import com.perqin.angumi.data.models.Paged
import com.perqin.angumi.data.models.CollectionType
import com.perqin.angumi.data.models.SubjectType

class CollectionRepo(
    private val localSource: CollectionLocalSource,
    private val remoteSource: CollectionRemoteSource
) {
    suspend fun loadCollections(
        username: String,
        subjectType: SubjectType,
        collectionType: CollectionType
    ): Paged<Collection> {
        return remoteSource.getCollections(username, subjectType, collectionType)
    }
}
