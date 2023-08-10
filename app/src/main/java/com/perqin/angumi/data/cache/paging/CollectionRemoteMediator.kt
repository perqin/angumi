package com.perqin.angumi.data.cache.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.perqin.angumi.data.api.bangumi.BangumiClient
import com.perqin.angumi.data.cache.models.CollectionWithSlimSubject
import com.perqin.angumi.data.models.CollectionType
import com.perqin.angumi.data.models.PagedQuery
import com.perqin.angumi.data.models.SubjectType
import com.perqin.angumi.data.room.CacheDatabase
import com.perqin.angumi.utils.logE
import com.perqin.angumi.utils.logI

/**
 * Key: Item offset
 */
@OptIn(ExperimentalPagingApi::class)
class CollectionRemoteMediator(
    private val username: String,
    private val subjectType: SubjectType,
    private val collectionType: CollectionType,
    private val database: CacheDatabase,
    private val client: BangumiClient,
) : RemoteMediator<Int, CollectionWithSlimSubject>() {
    companion object {
        private const val TAG = "CollectionRemoteMediato"
    }

    private val collectionDao = database.collectionDao

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CollectionWithSlimSubject>
    ): MediatorResult {
        val logPrefix = "[$username,$subjectType,$collectionType]load($loadType)"
        logI(TAG, logPrefix)
        return try {
            // loadKey: offset
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    if (state.lastItemOrNull() == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                    // Maybe its better to count all data?
                    state.pages.count { it.data.isNotEmpty() } * state.config.pageSize
                }
            }
            logI(TAG, "$logPrefix: loadKey=$loadKey")

            val response = client.collection.getCollections(
                username,
                PagedQuery(state.config.pageSize, loadKey ?: 0),
                subjectType,
                collectionType
            )
            logI(
                TAG,
                "$logPrefix: response.data.size=${response.data.size},response.offset=${response.offset},response.limit=${response.limit},response.total=${response.total}"
            )

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    logI(TAG, "$logPrefix: deleteCollectionsByTypes")
                    collectionDao.deleteCollectionsByTypes(subjectType, collectionType)
                }

                val cacheCollections =
                    response.data.map { CollectionWithSlimSubject.fromNetworkModel(it) }
                logI(TAG, "$logPrefix: upsertAllSlimSubject")
                collectionDao.upsertAllSlimSubject(cacheCollections.map { it.subject })
                logI(TAG, "$logPrefix: upsertAllCollections")
                collectionDao.upsertAllCollections(cacheCollections.map { it.collection })
            }

            // next_offset = response.offset + response.limit, and valid offset is [0, total), so
            // next_offset should be less than total
            val endReached = response.offset + response.limit >= response.total
            logI(TAG, "$logPrefix: Succeeded! endReached=$endReached")
            MediatorResult.Success(endOfPaginationReached = endReached)
        } catch (e: Exception) {
            logE(TAG, "$logPrefix: Failed!", e)
            MediatorResult.Error(e)
        }
    }
}
