package nl.tmg.dutchnews


import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import nl.tmg.dutchnews.db.ArticleDao
import nl.tmg.dutchnews.db.TMGDataBase
import org.junit.*


@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ArticleDaoTest {

    var database    : TMGDataBase?   = null

    var dao         : ArticleDao?   = null

    @Test
    fun initDatabase() {
        val context = InstrumentationRegistry.getInstrumentation().context
        Assert.assertNotNull(context)
        database    = TMGDataBase.getInstance(context,true)
        Assert.assertNotNull(database)
        database?.let {
            dao = it.getArticleDao()
            Assert.assertNotNull(dao)
        }
    }

    @After
    fun closeDatabase(){
        database?.let {
            it.close()
        }
    }
}