package com.perqin.angumi.data.cache.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.perqin.angumi.data.models.SlimSubjectImages

@Entity
data class SlimSubject(
    @PrimaryKey
    val id: Int,
    val name: String,
    val nameCn: String,
    val images: SlimSubjectImages,
)
