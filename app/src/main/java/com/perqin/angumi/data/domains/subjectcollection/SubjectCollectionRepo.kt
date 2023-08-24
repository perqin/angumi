package com.perqin.angumi.data.domains.subjectcollection

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.perqin.angumi.data.api.bangumi.BangumiClient
import com.perqin.angumi.data.domains.common.models.CollectionType
import com.perqin.angumi.data.domains.common.models.SubjectType
import com.perqin.angumi.data.room.CacheDatabase

class SubjectCollectionRepo(
    private val database: CacheDatabase,
    private val dao: SubjectCollectionDao,
    private val client: BangumiClient,
) {
    @OptIn(ExperimentalPagingApi::class)
    fun pagingFlowOf(username: String, subjectType: SubjectType, collectionType: CollectionType) =
        Pager(
            PagingConfig(pageSize = 30),
            remoteMediator = SubjectCollectionRemoteMediator(
                username, subjectType, collectionType, database, client
            ),
        ) { dao.queryPagedCollectionsByTypes(subjectType, collectionType) }.flow
}
