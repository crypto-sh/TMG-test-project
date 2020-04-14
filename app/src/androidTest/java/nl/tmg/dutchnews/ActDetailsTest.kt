package nl.tmg.dutchnews


import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import nl.tmg.dutchnews.dto.Article
import nl.tmg.dutchnews.dto.Source
import nl.tmg.dutchnews.ui.ActDetails
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters


@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ActDetailsTest {

    lateinit var article : Article

    @get:Rule
    var rule = ActivityTestRule(ActDetails::class.java, false, false)


    @Before
    fun prepareArticle(){
        article = Article(0,"title",Source("","source_name"),"imageUrl",null,"content", "author")
        val intent = Intent().putExtra("data",article)
        rule.launchActivity(intent)
    }

    @Test
    fun step0_checkIntent(){
        Assert.assertNotNull(rule.activity.intent)
    }

    @Test
    fun step1_checkvalue(){
        val data = rule.activity.intent.getParcelableExtra<Article>("data")
        onView(withId(R.id.tvTitle)).check(matches(isDisplayed())).check(matches(withText(data.title)))
        onView(withId(R.id.tvAuthor)).check(matches(isDisplayed())).check(matches(withText(data.author)))
        onView(withId(R.id.tvSource)).check(matches(isDisplayed())).check(matches(withText(data.source.name)))
        onView(withId(R.id.tvContent)).check(matches(isDisplayed())).check(matches(withText(data.content)))
        Assert.assertEquals(data.image, article.image)
    }
}