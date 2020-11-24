package com.dvidal.samplearticles.features.articles.presentation

import com.dvidal.samplearticles.features.articles.data.local.ArticleDto

/**
 * @author diegovidal on 2019-12-23.
 */
data class ArticleView(
    val sku: String = "",
    val title: String = "",
    val description: String? = "",
    val imageUrl: String = "",

    val isReview: Boolean = false,
    val isFavorite: Boolean = false
) {

    fun mapperToArticleDto(): ArticleDto {

        return ArticleDto(
            sku = this.sku,
            title = this.title,
            description = this.description,
            imageUrl = this.imageUrl,
            isReview = this.isReview,
            isFavorite = this.isFavorite
        )
    }
}
