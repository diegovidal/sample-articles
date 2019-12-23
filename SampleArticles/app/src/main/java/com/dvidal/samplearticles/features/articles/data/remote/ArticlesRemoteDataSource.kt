package com.dvidal.samplearticles.features.articles.data.remote

import com.dvidal.samplearticles.core.common.EitherResult
import com.dvidal.samplearticles.features.articles.presentation.ArticleView

/**
 * @author diegovidal on 2019-12-18.
 */
interface ArticlesRemoteDataSource {

    fun fetchAllArticles(numArticles: Int): EitherResult<List<ArticleView>>
}