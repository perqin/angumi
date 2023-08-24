package com.perqin.angumi.data.domains.subjectcollection

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.perqin.angumi.data.domains.common.models.CollectionType
import com.perqin.angumi.data.domains.common.models.SubjectType

@Dao
interface SubjectCollectionDao {
    @Upsert
    suspend fun upsertAllCollections(collections: List<SubjectCollection>)

    @Upsert
    suspend fun upsertAllSlimSubject(collections: List<SlimSubject>)

    @Transaction
    @Query("SELECT * FROM SubjectCollection WHERE subjectType = :subjectType AND type = :collectionType ORDER BY updatedAt DESC")
    fun queryPagedCollectionsByTypes(
        subjectType: SubjectType,
        collectionType: CollectionType
    ): PagingSource<Int, CollectionWithSlimSubject>

    @Query("DELETE FROM SubjectCollection WHERE subjectType = :subjectType AND type = :collectionType")
    suspend fun deleteCollectionsByTypes(subjectType: SubjectType, collectionType: CollectionType)
}
