package com.dvidal.samplearticles.features.articles.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dvidal.samplearticles.features.articles.presentation.ArticleView

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
) {

    fun mapperToArticleView(): ArticleView {

        return ArticleView(
            sku = this.sku,
            title = this.title,
            description = this.description,
            imageUrl = this.imageUrl,
            isReview = this.isReview,
            isFavorite = this.isFavorite
        )
    }
}
