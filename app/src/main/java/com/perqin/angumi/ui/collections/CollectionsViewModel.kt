package com.perqin.angumi.ui.collections

import androidx.lifecycle.ViewModel
import com.perqin.angumi.data.collection.Collection
import com.perqin.angumi.data.collection.CollectionRepo
import com.perqin.angumi.data.models.CollectionType
import com.perqin.angumi.data.models.Paged
import com.perqin.angumi.data.models.SubjectType
import com.perqin.angumi.data.user.UserRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CollectionsViewModel(
    private val collectionRepo: CollectionRepo,
    private val userRepo: UserRepo
) : ViewModel() {
    private val _collections = MutableStateFlow<Paged<Collection>>(Paged(0, 0, 0, emptyList()))
    val collections: StateFlow<Paged<Collection>> = _collections

    suspend fun loadCollections() {
        val username = userRepo.loadMe().username
        _collections.emit(
            collectionRepo.loadCollections(
                username,
                SubjectType.ANIME,
                CollectionType.DO
            )
        )
    }
}
