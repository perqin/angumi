package com.perqin.angumi.data.cache.paging

import com.perqin.angumi.data.models.CollectionType
import com.perqin.angumi.data.models.SubjectType

data class CollectionPagingQuery(
    val username: String,
    val subjectType: SubjectType,
    val collectionType: CollectionType,
)
