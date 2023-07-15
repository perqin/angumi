package com.perqin.angumi.data.collection

import com.perqin.angumi.data.models.CollectionType
import com.perqin.angumi.data.models.Paged
import com.perqin.angumi.data.models.SubjectType
import kotlinx.coroutines.flow.StateFlow

class CollectionRepo(
    private val localSource: CollectionLocalSource,
    private val remoteSource: CollectionRemoteSource
) {
    fun collectionListOf(
        subjectType: SubjectType,
        collectionType: CollectionType
    ): StateFlow<Paged<Collection>> = localSource.collectionListOf(subjectType, collectionType)

    suspend fun loadCollections(
        username: String,
        subjectType: SubjectType,
        collectionType: CollectionType
    ) {
        val data = remoteSource.getCollections(username, subjectType, collectionType)
        localSource.updateCollectionListOf(username, subjectType, collectionType, data)
    }
}
