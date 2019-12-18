package com.dvidal.samplearticles.features.articles.data.local

import androidx.room.Entity

/**
 * @author diegovidal on 2019-12-18.
 */

@Entity
data class ArticleDto(
    private val sku: String,
    private val title: String,
    private val description: String?,
    private val imageUrl: String,

    private val isReview: Boolean = false,
    private val isFavorite: Boolean = false
)