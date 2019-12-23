package com.dvidal.samplearticles.features.articles.data.remote

import com.dvidal.samplearticles.features.articles.presentation.ArticleView

/**
 * @author diegovidal on 2019-12-23.
 */
data class ArticlesRemoteResponse(
    val articles: List<ArticleRemote>
) {

    companion object {
        fun empty(): ArticlesRemoteResponse =  ArticlesRemoteResponse(articles = emptyList())
    }
}

data class ArticleRemoteMedia(
    val uri: String,
    private val mimeType: String
)

data class ArticleRemote(
    val sku: String = "",
    val title: String = "",
    val description: String? = "",
    val media: List<ArticleRemoteMedia>
) {

    fun mapperToArticleView(): ArticleView {

        return ArticleView(
            sku = this.sku,
            title = this.title,
            description = this.description,
            imageUrl = this.media.first().uri
        )
    }
}