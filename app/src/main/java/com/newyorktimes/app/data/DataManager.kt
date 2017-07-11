package com.newyorktimes.app.data

import com.newyorktimes.app.data.model.mostpopular.MostPopular
import com.newyorktimes.app.data.model.searcharticle.ArticleSearch
import com.newyorktimes.app.data.remote.MvpStarterService
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataManager @Inject
constructor(private val mMvpStarterService: MvpStarterService) {

    fun getMostPopularViewedItems(): Single<MostPopular> {
        return mMvpStarterService.getMostPopularViewedItems()
    }

    fun articleSearch(searchQuery: String, page: Int): Single<ArticleSearch> {
        return mMvpStarterService.articleSearch(searchQuery, page)
    }
}

