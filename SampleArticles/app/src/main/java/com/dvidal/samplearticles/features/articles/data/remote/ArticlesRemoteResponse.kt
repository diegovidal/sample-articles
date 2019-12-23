package com.dvidal.samplearticles.features.articles.data.remote

/**
 * @author diegovidal on 2019-12-23.
 */
data class ArticlesRemoteResponse(
    private val articles: List<ArticleRemote>
)

data class ArticleRemoteMidia(
    private val uri: String,
    private val mimeType: String
)

data class ArticleRemote(
    val sku: String = "",
    val title: String = "",
    val description: String? = "",
    val media: List<ArticleRemoteMidia>
)