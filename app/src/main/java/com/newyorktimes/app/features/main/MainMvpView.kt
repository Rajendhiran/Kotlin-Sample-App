package com.newyorktimes.app.features.main

import com.newyorktimes.app.data.model.mostpopular.Result
import com.newyorktimes.app.data.model.searcharticle.Doc
import com.newyorktimes.app.features.base.MvpView

interface MainMvpView : MvpView {

    fun showMostPopular(result: List<Result>?)

    fun showSearchResult(result: List<Doc>?)

    fun showProgress(show: Boolean)

    fun showError(error: Throwable)

}