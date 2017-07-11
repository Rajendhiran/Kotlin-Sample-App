package com.newyorktimes.app.features.main

import com.newyorktimes.app.data.DataManager
import com.newyorktimes.app.data.model.mostpopular.MostPopular
import com.newyorktimes.app.data.model.searcharticle.ArticleSearch
import com.newyorktimes.app.features.base.BasePresenter
import com.newyorktimes.app.injection.ConfigPersistent
import com.newyorktimes.app.util.rx.scheduler.SchedulerUtils
import javax.inject.Inject

@ConfigPersistent
class MainPresenter @Inject
constructor(private val mDataManager: DataManager) : BasePresenter<MainMvpView>() {

    override fun attachView(mvpView: MainMvpView) {
        super.attachView(mvpView)
    }

    fun getMostPopular() {
        checkViewAttached()
        mvpView?.showProgress(true)
        mDataManager.getMostPopularViewedItems()
                .compose(SchedulerUtils.ioToMain<MostPopular>())
                .subscribe({ mostPopular ->
                    mvpView?.showProgress(false)
                    mvpView?.showMostPopular(mostPopular.results)
                }) { throwable ->
                    mvpView?.showProgress(false)
                    mvpView?.showError(throwable)
                }
    }

    fun articleSearch(searchQuery: String, page : Int) {
        checkViewAttached()
        mvpView?.showProgress(true)
        mDataManager.articleSearch(searchQuery,page)
                .compose(SchedulerUtils.ioToMain<ArticleSearch>())
                .subscribe({ articles ->
                    mvpView?.showProgress(false)
                    mvpView?.showSearchResult(articles.response?.docs)
                }) { throwable ->
                    mvpView?.showProgress(false)
                    mvpView?.showError(throwable)
                }
    }

}