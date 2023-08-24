package com.perqin.angumi.ui.collections

import androidx.lifecycle.ViewModel
import com.perqin.angumi.data.domains.common.models.SubjectType
import com.perqin.angumi.data.settings.SettingsRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CollectionsViewModel(settingsRepo: SettingsRepo) : ViewModel() {
    val session = settingsRepo.session

    private val _tabUpdateRequest = MutableStateFlow<TabUpdateRequest?>(null)
    val tabUpdateRequest: StateFlow<TabUpdateRequest?> = _tabUpdateRequest

    suspend fun sendTabUpdateRequest(subjectType: SubjectType) {
        _tabUpdateRequest.emit(TabUpdateRequest(subjectType))
    }

    suspend fun clearTabUpdateRequest() {
        _tabUpdateRequest.emit(null)
    }
}
