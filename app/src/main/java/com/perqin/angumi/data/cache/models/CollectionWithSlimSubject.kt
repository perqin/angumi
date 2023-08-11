package com.perqin.angumi.data.cache.models

import androidx.room.Embedded
import androidx.room.Relation
import kotlinx.datetime.Instant

data class CollectionWithSlimSubject(
    @Embedded
    val collection: Collection,
    @Relation(parentColumn = "subjectId", entityColumn = "id")
    val subject: SlimSubject,
) {
    companion object {
        fun fromNetworkModel(data: com.perqin.angumi.data.collection.Collection): CollectionWithSlimSubject {
            return data.run {
                CollectionWithSlimSubject(
                    Collection(
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
                    SlimSubject(
                        subject.id,
                        subject.name,
                        subject.nameCn,
                        subject.images
                    )
                )
            }
        }
    }
}
