package nl.tmg.dutchnews.db

import androidx.paging.DataSource
import nl.tmg.dutchnews.dto.Article


interface LocalCache {

    fun insertArticle(articles : List<Article>, onCompleted : () -> Unit)

    fun getArticles() : DataSource.Factory<Int,Article>
}