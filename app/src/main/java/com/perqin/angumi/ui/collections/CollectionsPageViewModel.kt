package com.perqin.angumi.ui.collections

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.perqin.angumi.data.cache.paging.CollectionPagingQuery
import com.perqin.angumi.data.collection.CollectionRepo
import com.perqin.angumi.data.models.CollectionType
import com.perqin.angumi.data.models.SubjectType
import com.perqin.angumi.data.settings.SettingsRepo
import com.perqin.angumi.data.settings.isSignedIn
import com.perqin.angumi.data.user.UserRepo
import com.perqin.angumi.utils.logE
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

// TODO: Support loading status
class CollectionsPageViewModel(
    private val subjectType: SubjectType,
    private val collectionRepo: CollectionRepo,
    private val userRepo: UserRepo,
    settingsRepo: SettingsRepo,
) : ViewModel() {
    companion object {
        private const val TAG = "CollectionsPageVM"
    }

    val session = settingsRepo.session

    private val _collectionType = MutableStateFlow(CollectionType.DO)
    val collectionType: StateFlow<CollectionType> = _collectionType

    private val usernameFlow = session.map {
        if (it.isSignedIn()) try {
            userRepo.ensureMe().username
        } catch (e: Exception) {
            // TODO: Handle exception (show error and retry, etc.)
            logE(TAG, "Failed to ensure me", e)
            null
        } else null
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val collectionPagingFlow = usernameFlow.combine(_collectionType) { u, ct ->
        if (u != null) CollectionPagingQuery(u, subjectType, ct)
        else null
    }
        .flatMapLatest {
            it?.run {
                collectionRepo.pagingFlowOf(username, subjectType, collectionType)
                    .cachedIn(viewModelScope)
            } ?: flow {
                emit(PagingData.empty())
            }
        }

    suspend fun changeCollectionType(collectionType: CollectionType) {
        // To avoid triggering collectionList's emission
        if (_collectionType.value == collectionType) {
            return
        }
        _collectionType.emit(collectionType)
        reloadCollectionListSilently()
    }

    suspend fun reloadCollectionListSilently() {
        if (!session.first().isSignedIn()) {
            return
        }
        // TODO: Implement me
    }
}
