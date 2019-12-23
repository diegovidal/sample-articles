package com.dvidal.samplearticles.features.articles.domain

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

    companion object {

        fun mapperToArticleView(articleDto: ArticleDto): ArticleView {

            return ArticleView(
                sku = articleDto.sku,
                title = articleDto.title,
                description = articleDto.description,
                imageUrl = articleDto.imageUrl,
                isReview = articleDto.isReview,
                isFavorite = articleDto.isFavorite
            )
        }
    }
}