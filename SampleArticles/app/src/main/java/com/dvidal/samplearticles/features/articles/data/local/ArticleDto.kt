package com.dvidal.samplearticles.features.articles.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author diegovidal on 2019-12-18.
 */

@Entity
data class ArticleDto(
    @PrimaryKey val sku: String = "",
    val title: String = "",
    val description: String? = "",
    val imageUrl: String = "",

    val isReview: Boolean = false,
    val isFavorite: Boolean = false
)