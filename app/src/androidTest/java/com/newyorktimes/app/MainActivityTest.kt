package com.newyorktimes.app

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.newyorktimes.app.common.TestComponentRule
import com.newyorktimes.app.common.TestDataFactory
import com.newyorktimes.app.data.model.mostpopular.MostPopular
import com.newyorktimes.app.data.model.searcharticle.ArticleSearch
import com.newyorktimes.app.features.main.MainActivity
import com.newyorktimes.app.util.ErrorTestUtil
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.`when`

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private val mComponent = TestComponentRule(InstrumentationRegistry.getTargetContext())
    private val mMain = ActivityTestRule(MainActivity::class.java, false, false)

    // TestComponentRule needs to go first to make sure the Dagger ApplicationTestComponent is set
    // in the Application before any Activity is launched.
    @Rule @JvmField
    var chain: TestRule = RuleChain.outerRule(mComponent).around(mMain)

    @Test
    fun checkArticlesDisplay() {
        val namedResourceList = TestDataFactory.makeResultList(1)
        val articleList = TestDataFactory.makeMostPopular("1")
        stubDataManagerGetMostPopularList(Single.just(articleList))
        mMain.launchActivity(null)

        for (articleName in namedResourceList) {
            onView(withText(articleName.title))
                    .check(matches(isDisplayed()))
        }
    }

    @Test
    fun checkErrorViewDisplays() {
        stubDataManagerGetMostPopularList(Single.error<MostPopular>(RuntimeException()))
        mMain.launchActivity(null)
        ErrorTestUtil.checkErrorViewsDisplay()
    }

    fun stubDataManagerGetMostPopularList(single: Single<MostPopular>) {
        `when`(mComponent.mockDataManager.getMostPopularViewedItems())
                .thenReturn(single)
    }

    fun stubDataManagerGetSearchArticle(single: Single<ArticleSearch>) {
        `when`(mComponent.mockDataManager.articleSearch(ArgumentMatchers.anyString(), 1))
                .thenReturn(single)
    }

}