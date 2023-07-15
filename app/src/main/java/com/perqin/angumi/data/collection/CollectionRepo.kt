package com.perqin.angumi.data.collection

import com.perqin.angumi.data.models.CollectionType
import com.perqin.angumi.data.models.Paged
import com.perqin.angumi.data.models.SubjectType
import com.perqin.angumi.data.models.emptyPage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CollectionRepo(
    private val localSource: CollectionLocalSource,
    private val remoteSource: CollectionRemoteSource
) {
    private val _doAnimeCollectionList = MutableStateFlow(emptyPage<Collection>())

    // TODO: SubjectType X CollectionType, we have 25 flows to populate
    fun collectionListOf(
        subjectType: SubjectType,
        collectionType: CollectionType
    ): StateFlow<Paged<Collection>> = _doAnimeCollectionList

    suspend fun loadCollections(
        username: String,
        subjectType: SubjectType,
        collectionType: CollectionType
    ): Paged<Collection> {
        return remoteSource.getCollections(username, subjectType, collectionType)
    }
}
