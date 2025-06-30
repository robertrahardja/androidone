package com.rr.adone.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "content_items")
@Serializable
data class ContentItem(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val category: String = "Android Development",
    val difficulty: String = "Beginner",
    val estimatedTime: String = "30 min"
)