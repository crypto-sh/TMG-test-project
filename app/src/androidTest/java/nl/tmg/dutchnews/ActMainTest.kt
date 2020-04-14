package nl.tmg.dutchnews



import androidx.arch.core.executor.testing.CountingTaskExecutorRule
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage
import nl.tmg.dutchnews.adapters.RvAdapterArticle
import nl.tmg.dutchnews.ui.ActDetails
import nl.tmg.dutchnews.ui.ActMain
import org.hamcrest.CoreMatchers
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.not
import org.junit.Assert
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ActMainTest {

    @get:Rule
    var testRule = CountingTaskExecutorRule()


    @get:Rule
    var rule = ActivityTestRule(ActMain::class.java, false, true)

    @Test
    fun step0_checkPackageName(){
        val context = InstrumentationRegistry.getInstrumentation().context
        Assert.assertEquals(context.packageName,"nl.tmg.dutchnews.test")
    }

    @Test
    fun step1_loadData(){
        val country = "Netherlands"
        onView(withId(R.id.prg)).check(matches(not(isDisplayed())))
        val recycler = rule.activity.findViewById<RecyclerView>(R.id.rvArticle)
        Assert.assertNotNull(recycler.adapter)
        Assert.assertEquals(recycler.adapter!!.itemCount, 0)
        onView(withId(R.id.etCountry)).perform(clearText(), typeText(country))
        onData(equalTo(country)).inRoot(RootMatchers.isPlatformPopup()).perform(click())
        waitForAdapterChange(recycler)
        Assert.assertNotEquals(recycler.adapter!!.itemCount, 0)
    }

    @Test
    fun step2_checkNavigate(){
        val country = "Netherlands"
        onView(withId(R.id.prg)).check(matches(not(isDisplayed())))
        val recycler = rule.activity.findViewById<RecyclerView>(R.id.rvArticle)
        Assert.assertNotNull(recycler.adapter)
        Assert.assertEquals(recycler.adapter!!.itemCount, 0)
        onView(withId(R.id.etCountry)).perform(clearText(), typeText(country))
        onData(equalTo(country)).inRoot(RootMatchers.isPlatformPopup()).perform(click())
        waitForAdapterChange(recycler)

        onView(withId(R.id.rvArticle)).perform(actionOnItemAtPosition<RvAdapterArticle.ArticleHolder>(0, click()))
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED).let {
                val current = it.elementAt(0)
                Assert.assertEquals(current::class.java, ActDetails::class.java)
            }
        }
    }

    private fun waitForAdapterChange(recyclerView: RecyclerView) {
        val latch = CountDownLatch(1)
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            recyclerView.adapter?.registerAdapterDataObserver(
                object : RecyclerView.AdapterDataObserver() {
                    override fun onChanged() {
                        latch.countDown()
                    }

                    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                        latch.countDown()
                    }
                })
        }
        testRule.drainTasks(1, TimeUnit.SECONDS)
        if (recyclerView.adapter?.itemCount ?: 0 > 0) {
            return
        }
        assertThat(latch.await(10, TimeUnit.SECONDS), CoreMatchers.`is`(true))
    }
}