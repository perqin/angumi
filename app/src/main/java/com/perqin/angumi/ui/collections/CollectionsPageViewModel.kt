package com.perqin.angumi.ui.collections

import androidx.lifecycle.ViewModel
import com.perqin.angumi.data.collection.CollectionRepo
import com.perqin.angumi.data.models.CollectionType
import com.perqin.angumi.data.models.SubjectType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest

class CollectionsPageViewModel(
    private val subjectType: SubjectType,
    private val collectionRepo: CollectionRepo
) : ViewModel() {
    private val _collectionType = MutableStateFlow(CollectionType.DO)
    val collectionType: StateFlow<CollectionType> = _collectionType

    @OptIn(ExperimentalCoroutinesApi::class)
    val collectionList = _collectionType.flatMapLatest {
        collectionRepo.collectionListOf(subjectType, it)
    }
}
