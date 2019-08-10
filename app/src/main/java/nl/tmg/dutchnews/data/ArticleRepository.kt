package nl.tmg.dutchnews.data

import androidx.paging.LivePagedListBuilder
import nl.tmg.core.dto.NetworkState
import nl.tmg.dutchnews.api.ApiServices
import nl.tmg.dutchnews.db.LocalCache
import nl.tmg.dutchnews.dto.ArticleResult




interface ArticleRepository {
    fun getTopHeadLines(country: String): ArticleResult
}

class ArticleRepositoryImpl(
    private val services: ApiServices,
    private val localCache: LocalCache
) : ArticleRepository {
    companion object {
        private const val PAGE_SIZE = 20
    }

    override fun getTopHeadLines(country: String): ArticleResult {
        val dataSourceFactory = localCache.getArticles()
        val boundaryCallback = TopHeadBoundaryCallback(
            pageSize    = PAGE_SIZE,
            country     = country,
            services    = services,
            localCache  = localCache
        )
        val networkState = boundaryCallback.networkState
        val data = LivePagedListBuilder(dataSourceFactory, PAGE_SIZE)
            .setBoundaryCallback(boundaryCallback)
            .build()

        return ArticleResult(networkState, data)
    }

}