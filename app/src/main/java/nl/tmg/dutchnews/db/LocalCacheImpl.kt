package nl.tmg.dutchnews.db

import androidx.paging.DataSource
import nl.tmg.dutchnews.dto.Article
import java.util.concurrent.Executor


class LocalCacheImpl(
    private val articleDao: ArticleDao,
    private val executor: Executor
) : LocalCache {

    override fun insertArticle(articles: List<Article>, onCompleted: () -> Unit) {
        executor.execute {
            try {
                articleDao.insertArticle(articles)
            } catch (e: Exception) {
                print(e.message)
            }
            onCompleted()
        }
    }

    override fun getArticles(): DataSource.Factory<Int, Article> = articleDao.getArticles()
}