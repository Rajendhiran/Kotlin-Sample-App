package com.newyorktimes.app

import com.newyorktimes.app.common.TestDataFactory
import com.newyorktimes.app.data.DataManager
import com.newyorktimes.app.data.model.searcharticle.ArticleSearch
import com.newyorktimes.app.data.model.searcharticle.Doc
import com.newyorktimes.app.features.main.MainMvpView
import com.newyorktimes.app.features.main.MainPresenter
import com.newyorktimes.app.util.RxSchedulersOverrideRule
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class MainPresenterTest {

    @Mock lateinit var mMockMainMvpView: MainMvpView
    @Mock lateinit var mMockDataManager: DataManager
    private var mMainPresenter: MainPresenter? = null

    @JvmField
    @Rule
    val mOverrideSchedulersRule = RxSchedulersOverrideRule()

    @Before
    fun setUp() {
        mMainPresenter = MainPresenter(mMockDataManager)
        mMainPresenter?.attachView(mMockMainMvpView)
    }

    @After
    fun tearDown() {
        mMainPresenter?.detachView()
    }

    @Test
    @Throws(Exception::class)
    fun getMostPopularReturnsArticles() {
        val articleList = TestDataFactory.makeMostPopular("1")
        `when`(mMockDataManager.getMostPopularViewedItems())
                .thenReturn(Single.just(articleList))

        mMainPresenter?.getMostPopular()

        verify<MainMvpView>(mMockMainMvpView, times(2)).showProgress(anyBoolean())
        verify<MainMvpView>(mMockMainMvpView).showMostPopular(articleList.results)
        verify<MainMvpView>(mMockMainMvpView, never()).showError(RuntimeException())

    }

    @Test
    @Throws(Exception::class)
    fun articleSearchReturnsError() {
        val query = "new york"
        `when`(mMockDataManager.articleSearch(query, 1))
                .thenReturn(Single.error<ArticleSearch>(RuntimeException()))

        mMainPresenter?.articleSearch(query, 1)

        verify<MainMvpView>(mMockMainMvpView, times(2)).showProgress(anyBoolean())
        verify<MainMvpView>(mMockMainMvpView).showError(RuntimeException())
        verify<MainMvpView>(mMockMainMvpView, never()).showSearchResult(ArgumentMatchers.anyList<Doc>())
    }

}