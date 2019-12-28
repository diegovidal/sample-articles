package com.dvidal.samplearticles.features.articles.data.remote

import com.dvidal.samplearticles.features.articles.presentation.ArticleView
import com.squareup.moshi.Json

/**
 * @author diegovidal on 2019-12-23.
 */
data class ArticlesRemoteResponse(
    @Json(name = "_embedded") val embedded: ArticlesRemoteFinalResponse
) {

    companion object {
        fun empty(): ArticlesRemoteResponse =  ArticlesRemoteResponse(embedded = ArticlesRemoteFinalResponse(emptyList()))
    }
}

data class ArticlesRemoteFinalResponse(
    val articles: List<ArticleRemote> = emptyList()
)

data class ArticleRemoteMedia(
    val uri: String = "",
    val mimeType: String = ""
)

data class ArticleRemote(
    val sku: String = "",
    val title: String = "",
    val description: String? = "",
    val media: List<ArticleRemoteMedia> = emptyList()
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