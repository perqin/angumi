package com.perqin.angumi.data.domains.subjectcollection

import androidx.room.Embedded
import androidx.room.Relation
import kotlinx.datetime.Instant

data class CollectionWithSlimSubject(
    @Embedded
    val collection: SubjectCollection,
    @Relation(parentColumn = "subjectId", entityColumn = "id")
    val subject: SlimSubject,
) {
    companion object {
        fun fromNetworkModel(data: com.perqin.angumi.data.api.bangumi.models.Collection): CollectionWithSlimSubject {
            return data.run {
                CollectionWithSlimSubject(
                    SubjectCollection(
                        subjectId,
                        subjectType,
                        rate,
                        type,
                        comment,
                        tags,
                        epStatus,
                        volStatus,
                        Instant.parse(updatedAt),
                        private,
                    ),
                    subject,
                )
            }
        }
    }
}
