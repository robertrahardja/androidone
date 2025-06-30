package com.rr.adone.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "content_items")
data class ContentItem(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String
)