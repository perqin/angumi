package com.perqin.angumi.ui.collections

import androidx.lifecycle.ViewModel
import com.perqin.angumi.data.collection.Collection
import com.perqin.angumi.data.collection.CollectionRepo
import com.perqin.angumi.data.models.CollectionType
import com.perqin.angumi.data.models.Paged
import com.perqin.angumi.data.models.SubjectType
import com.perqin.angumi.data.models.emptyPage
import com.perqin.angumi.data.settings.SettingsRepo
import com.perqin.angumi.data.settings.isSignedIn
import com.perqin.angumi.data.user.UserRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first

class CollectionsViewModel(
    private val collectionRepo: CollectionRepo,
    private val userRepo: UserRepo,
    settingsRepo: SettingsRepo,
) : ViewModel() {
    val session = settingsRepo.session
    private val _collectionType = MutableStateFlow(CollectionType.DO)
    val collectionType: StateFlow<CollectionType> = _collectionType
    private val _collections = MutableStateFlow<Paged<Collection>>(emptyPage())
    val collections: StateFlow<Paged<Collection>> = _collections

    suspend fun loadCollections() {
        if (!session.first().isSignedIn()) {
            return
        }
        val username = userRepo.loadMe().username
        val type = _collectionType.value
        _collections.emit(collectionRepo.loadCollections(username, SubjectType.ANIME, type))
    }

    suspend fun selectCollectionType(type: CollectionType) {
        _collectionType.emit(type)
        _collections.emit(emptyPage())
        loadCollections()
    }
}
