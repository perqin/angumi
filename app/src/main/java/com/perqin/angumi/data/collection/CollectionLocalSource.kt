package com.perqin.angumi.data.collection

import com.perqin.angumi.data.models.CollectionType
import com.perqin.angumi.data.models.Paged
import com.perqin.angumi.data.models.SubjectType
import com.perqin.angumi.data.models.emptyPage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// TODO: Cache to disk
// TODO: Invalidate after username changes
class CollectionLocalSource {
    private val collectionListStore = SubjectType.values().associateWith {
        CollectionType.values().associateWith {
            MutableStateFlow(emptyPage<Collection>())
        }
    }

    fun collectionListOf(
        subjectType: SubjectType,
        collectionType: CollectionType
    ): StateFlow<Paged<Collection>> = collectionListStore[subjectType]!![collectionType]!!

    suspend fun updateCollectionListOf(
        username: String,
        subjectType: SubjectType,
        collectionType: CollectionType,
        data: Paged<Collection>
    ) {
        collectionListStore[subjectType]!![collectionType]!!.emit(data)
    }
}
