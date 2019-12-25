package com.dvidal.samplearticles.features.start.domain

import android.os.Parcelable
import com.dvidal.samplearticles.features.articles.presentation.ArticleView
import kotlinx.android.parcel.Parcelize

/**
 * @author diegovidal on 2019-12-25.
 */

@Parcelize
data class ArticlesInfoParam (
    private val totalArticles: Int = 0,
    private val totalFavoriteArticles: Int = 0
) : Parcelable {


    companion object {

        fun calculateArticlesInfoParam(list: List<ArticleView>): ArticlesInfoParam {

            val ta = list.size
            val tfa = list.filter { it.isFavorite }.size

            return ArticlesInfoParam(ta, tfa)
        }
    }
}