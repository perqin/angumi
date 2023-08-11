package com.perqin.angumi.data.cache.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.perqin.angumi.data.cache.models.Collection
import com.perqin.angumi.data.cache.models.CollectionWithSlimSubject
import com.perqin.angumi.data.cache.models.SlimSubject
import com.perqin.angumi.data.models.CollectionType
import com.perqin.angumi.data.models.SubjectType

@Dao
interface CollectionDao {
    @Upsert
    suspend fun upsertAllCollections(collections: List<Collection>)

    @Upsert
    suspend fun upsertAllSlimSubject(collections: List<SlimSubject>)

    @Transaction
    @Query("SELECT * FROM Collection WHERE subjectType = :subjectType AND type = :collectionType ORDER BY updatedAt DESC")
    fun queryPagedCollectionsByTypes(
        subjectType: SubjectType,
        collectionType: CollectionType
    ): PagingSource<Int, CollectionWithSlimSubject>

    @Query("DELETE FROM Collection WHERE subjectType = :subjectType AND type = :collectionType")
    suspend fun deleteCollectionsByTypes(subjectType: SubjectType, collectionType: CollectionType)
}
