package com.perqin.angumi.data.collection

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.perqin.angumi.data.api.bangumi.BangumiClient
import com.perqin.angumi.data.cache.daos.CollectionDao
import com.perqin.angumi.data.cache.paging.CollectionRemoteMediator
import com.perqin.angumi.data.models.CollectionType
import com.perqin.angumi.data.models.SubjectType
import com.perqin.angumi.data.room.CacheDatabase

class CollectionRepo(
    private val database: CacheDatabase,
    private val dao: CollectionDao,
    private val client: BangumiClient,
) {
    @OptIn(ExperimentalPagingApi::class)
    fun pagingFlowOf(username: String, subjectType: SubjectType, collectionType: CollectionType) =
        Pager(
            PagingConfig(pageSize = 30),
            remoteMediator = CollectionRemoteMediator(
                username, subjectType, collectionType, database, client
            ),
        ) { dao.queryPagedCollectionsByTypes(subjectType, collectionType) }.flow
}
