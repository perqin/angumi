package com.perqin.angumi.ui.collections

import androidx.lifecycle.ViewModel
import com.perqin.angumi.data.collection.Collection
import com.perqin.angumi.data.collection.CollectionRepo
import com.perqin.angumi.data.models.CollectionType
import com.perqin.angumi.data.models.SubjectType
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

    private val _collectionsMap = MutableStateFlow(emptyMap<SubjectType, List<Collection>>())
    val collectionsMap: StateFlow<Map<SubjectType, List<Collection>>> = _collectionsMap

    // TODO: Load only once on created
    // TODO: Load by collection type
    // TODO: Support loading status
    // TODO: Support pagination
    suspend fun loadCollections(subjectType: SubjectType) {
        if (!session.first().isSignedIn()) {
            return
        }
        val username = userRepo.loadMe().username
        val collections = collectionRepo.loadCollections(username, subjectType, CollectionType.DO)
        _collectionsMap.emit(_collectionsMap.value + (subjectType to collections.data))
    }
}
