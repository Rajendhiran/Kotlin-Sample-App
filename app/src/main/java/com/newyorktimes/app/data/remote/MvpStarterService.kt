package com.newyorktimes.app.data.remote


import com.newyorktimes.app.BuildConfig
import com.newyorktimes.app.data.model.mostpopular.MostPopular
import com.newyorktimes.app.data.model.searcharticle.ArticleSearch
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MvpStarterService {

    @Headers("apikey:" + BuildConfig.NYTIMES_API_KEY)
    @GET("svc/mostpopular/v2/mostviewed/all-sections/30.json?offset=20")
    fun getMostPopularViewedItems(): Single<MostPopular>

    @Headers("apikey:" + BuildConfig.NYTIMES_API_KEY)
    @GET("svc/search/v2/articlesearch.json?sort=newest&?hl=true")
    fun articleSearch(@Query("q") searchQuery: String, @Query("page") page: Int): Single<ArticleSearch>

}
