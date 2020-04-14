package nl.tmg.dutchnews


import android.content.Context
import androidx.room.paging.LimitOffsetDataSource
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import nl.tmg.dutchnews.db.ArticleDao
import nl.tmg.dutchnews.db.TMGDataBase
import nl.tmg.dutchnews.dto.Article
import nl.tmg.dutchnews.dto.articleGenerator
import org.junit.*


@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ArticleDaoTest {

    private var context: Context? = null
    private var database: TMGDataBase? = null

    private var dao: ArticleDao? = null

    @Before
    fun initDatabase() {
        context  = InstrumentationRegistry.getInstrumentation().context
        database = TMGDataBase.getInstance(context!!, true)
        dao      = database!!.getArticleDao()
    }

    @Test
    fun step_0_testDbCreated() {
        Assert.assertNotNull(context)
        Assert.assertNotNull(database)
        Assert.assertNotNull(dao)
    }

    @Test
    fun step_1_testData(){
        dao!!.insertArticle(arrayListOf(articleGenerator()))
        val list = (dao!!.getArticles().create() as LimitOffsetDataSource).loadRange(0, 10)
        Assert.assertTrue(list.size > 0)
    }

    @After
    fun closeDatabase() {
        database?.let {
            it.close()
        }
    }
}