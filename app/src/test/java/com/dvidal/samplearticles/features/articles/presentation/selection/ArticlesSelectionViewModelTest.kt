package com.dvidal.samplearticles.features.articles.presentation.selection

import com.dvidal.samplearticles.features.articles.domain.usecases.FetchUnreviewedArticlesUseCase
import com.dvidal.samplearticles.features.articles.domain.usecases.ReviewArticleUseCase
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import org.junit.Before

/**
 * @author diegovidal on 2019-12-29.
 */
class ArticlesSelectionViewModelTest {

    private val coroutineDispatcher = Dispatchers.Default
    private val fetchUnreviewedArticlesUseCase = mockk<FetchUnreviewedArticlesUseCase>(relaxUnitFun = true)
    private val reviewArticleUseCase = mockk<ReviewArticleUseCase>(relaxUnitFun = true)

    private lateinit var viewModel: ArticlesSelectionViewModel

    @Before
    fun setup() {

        viewModel = ArticlesSelectionViewModel(coroutineDispatcher, fetchUnreviewedArticlesUseCase, reviewArticleUseCase)
    }

}