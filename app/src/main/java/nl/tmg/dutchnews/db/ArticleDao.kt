package nl.tmg.dutchnews.db


import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import nl.tmg.dutchnews.dto.Article


@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticle(articles : List<Article>)

    @Query("select * from article")
    fun getArticles() : DataSource.Factory<Int,Article>
}