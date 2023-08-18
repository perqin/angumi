package com.perqin.angumi.data.cache.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.perqin.angumi.data.models.SlimSubjectImages
import com.perqin.angumi.data.models.SlimSubjectTag
import com.perqin.angumi.data.models.SubjectType
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class SlimSubject(
    @PrimaryKey
    val id: Int,
    val type: SubjectType,
    val name: String,
    val nameCn: String,
    val shortSummary: String,
    // On-air date
    val date: LocalDate?,
    val images: SlimSubjectImages,
    val volumes: Int,
    val eps: Int,
    // Total count that collected this subject
    val collectionTotal: Int,
    val score: Double,
    // Note: first most 10 tags only
    val tags: List<SlimSubjectTag>,
)
