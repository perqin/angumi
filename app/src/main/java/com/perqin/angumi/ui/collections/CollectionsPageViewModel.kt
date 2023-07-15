package com.perqin.angumi.ui.collections

import androidx.lifecycle.ViewModel
import com.perqin.angumi.data.collection.CollectionRepo
import com.perqin.angumi.data.models.CollectionType
import com.perqin.angumi.data.models.SubjectType
import com.perqin.angumi.data.settings.SettingsRepo
import com.perqin.angumi.data.settings.isSignedIn
import com.perqin.angumi.data.user.UserRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest

class CollectionsPageViewModel(
    private val subjectType: SubjectType,
    private val collectionRepo: CollectionRepo,
    private val userRepo: UserRepo,
    settingsRepo: SettingsRepo,
) : ViewModel() {
    val session = settingsRepo.session

    private val _collectionType = MutableStateFlow(CollectionType.DO)
    val collectionType: StateFlow<CollectionType> = _collectionType

    @OptIn(ExperimentalCoroutinesApi::class)
    val collectionList = _collectionType.flatMapLatest {
        collectionRepo.collectionListOf(subjectType, it)
    }

    suspend fun changeCollectionType(collectionType: CollectionType) {
        // To avoid triggering collectionList's emission
        if (_collectionType.value == collectionType) {
            return
        }
        _collectionType.emit(collectionType)
        reloadCollectionList()
    }

    // TODO: Load only once on created
    // TODO: Support loading status
    // TODO: Support pagination
    suspend fun reloadCollectionList() {
        if (!session.first().isSignedIn()) {
            return
        }
        val username = userRepo.loadMe().username
        collectionRepo.loadCollections(username, subjectType, _collectionType.value)
    }
}
