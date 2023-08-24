package com.perqin.angumi.data.domains.subjectcollection

import com.perqin.angumi.data.domains.common.models.CollectionType
import com.perqin.angumi.data.domains.common.models.SubjectType

data class SubjectCollectionPagingQuery(
    val username: String,
    val subjectType: SubjectType,
    val collectionType: CollectionType,
)
