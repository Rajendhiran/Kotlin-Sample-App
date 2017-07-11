package com.newyorktimes.app

import com.newyorktimes.app.common.TestDataFactory
import com.newyorktimes.app.data.DataManager
import com.newyorktimes.app.data.remote.MvpStarterService
import com.newyorktimes.app.util.RxSchedulersOverrideRule
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DataManagerTest {

    @Rule @JvmField val mOverrideSchedulersRule = RxSchedulersOverrideRule()
    @Mock lateinit var mMockMvpStarterService: MvpStarterService

    private var mDataManager: DataManager? = null

    @Before
    fun setUp() {
        mDataManager = DataManager(mMockMvpStarterService)
    }

    @Test
    fun getMostPopularListAndEmitList() {
        val mostPopular = TestDataFactory.makeMostPopular("1")

        `when`(mMockMvpStarterService.getMostPopularViewedItems())
                .thenReturn(Single.just(mostPopular))

        mDataManager?.getMostPopularViewedItems()
                ?.test()
                ?.assertComplete()
                ?.assertValue(TestDataFactory.makeMostPopular("1"))
    }

    @Test
    fun searchArticleAndEmitResult() {
        val query = "new york"
        val articles = TestDataFactory.makeArticleSearch("1")
        `when`(mMockMvpStarterService.articleSearch(query, 1))
                .thenReturn(Single.just(articles))

        mDataManager?.articleSearch(query, 1)
                ?.test()
                ?.assertComplete()
                ?.assertValue(articles)
    }
}
