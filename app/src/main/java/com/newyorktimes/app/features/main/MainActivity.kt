package com.newyorktimes.app.features.main

import android.os.Bundle
import android.support.v4.view.MenuItemCompat.getActionView
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import butterknife.BindView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent
import com.newyorktimes.app.R
import com.newyorktimes.app.data.model.mostpopular.Result
import com.newyorktimes.app.data.model.searcharticle.Doc
import com.newyorktimes.app.features.base.BaseActivity
import com.newyorktimes.app.features.common.ErrorView
import com.newyorktimes.app.util.EndlessRecyclerViewScrollListener
import com.newyorktimes.app.util.StringUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Predicate
import io.reactivex.observers.DisposableObserver
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class MainActivity : BaseActivity(), MainMvpView, ErrorView.ErrorListener {

    @Inject lateinit var mItemAdapter: ItemAdapter
    @Inject lateinit var mMainPresenter: MainPresenter

    @BindView(R.id.view_error) @JvmField var mErrorView: ErrorView? = null
    @BindView(R.id.progress) @JvmField var mProgress: ProgressBar? = null
    @BindView(R.id.recycler_pokemon) @JvmField var mItemRecycler: RecyclerView? = null
    @BindView(R.id.swipe_to_refresh) @JvmField var mSwipeRefreshLayout: SwipeRefreshLayout? = null
    @BindView(R.id.toolbar) @JvmField var mToolbar: Toolbar? = null

    private var mScrollListener: EndlessRecyclerViewScrollListener? = null
    private var mSearchView: SearchView? = null
    private var mDisposable: Disposable? = null
    private var TAG = MainActivity::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent().inject(this)
        mMainPresenter.attachView(this)

        setSupportActionBar(mToolbar)

        mSwipeRefreshLayout?.setProgressBackgroundColorSchemeResource(R.color.primary)
        mSwipeRefreshLayout?.setColorSchemeResources(R.color.white)
        mSwipeRefreshLayout?.setOnRefreshListener { mMainPresenter.getMostPopular() }

        mItemRecycler?.layoutManager = LinearLayoutManager(this)
        mItemRecycler?.adapter = mItemAdapter

        mScrollListener = object : EndlessRecyclerViewScrollListener(mItemRecycler?.layoutManager as LinearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                if (page < 121 && !mSearchView?.query.toString().isBlank()) {

                    mMainPresenter.articleSearch(mSearchView?.query.toString(), page)
                }
            }

            override fun setRefreshing(state: Boolean) {
            }

            override fun onScrolled(dx: Int, dy: Int) {

            }
        }

        mItemRecycler?.addOnScrollListener(mScrollListener)

        mErrorView?.setErrorListener(this)

        mMainPresenter.getMostPopular()


    }

    override val layout: Int
        get() = R.layout.activity_main

    override fun onDestroy() {
        super.onDestroy()
        mMainPresenter.detachView()
        mDisposable?.dispose()
        mItemRecycler?.removeOnScrollListener(mScrollListener)
    }


    override fun showMostPopular(result: List<Result>?) {
        if (result != null) {
            mItemAdapter.setItems(result as MutableList<Any>)
        }
        mItemAdapter.notifyDataSetChanged()

        mItemRecycler?.visibility = View.VISIBLE
        mSwipeRefreshLayout?.visibility = View.VISIBLE
    }

    override fun showSearchResult(result: List<Doc>?) {

        if (result?.isNotEmpty() == true) {
            mItemAdapter.addItems(result as MutableList<Doc>)

            mItemAdapter.notifyDataSetChanged()

            mItemRecycler?.visibility = View.VISIBLE
            mSwipeRefreshLayout?.visibility = View.VISIBLE
        } else {
            mErrorView?.findViewById<TextView>(R.id.text_error_message)?.text = StringUtil.fromHtml(getString(R.string.empty_message, mSearchView?.query))
            mErrorView?.findViewById<TextView>(R.id.button_reload)?.visibility = View.INVISIBLE
            mItemRecycler?.visibility = View.GONE
            mSwipeRefreshLayout?.visibility = View.VISIBLE
            mErrorView?.visibility = View.VISIBLE
        }
    }

    override fun showProgress(show: Boolean) = if (show) {
        if (mItemRecycler?.visibility == View.VISIBLE && mItemAdapter.itemCount > 0) {
            mSwipeRefreshLayout?.isRefreshing = true
        } else {
            mProgress?.visibility = View.VISIBLE

            mItemRecycler?.visibility = View.GONE
            mSwipeRefreshLayout?.visibility = View.GONE
        }

        mErrorView?.visibility = View.GONE
    } else {
        mSwipeRefreshLayout?.isRefreshing = false
        mProgress?.visibility = View.GONE
    }

    override fun showError(error: Throwable) {
        mItemRecycler?.visibility = View.GONE
        mSwipeRefreshLayout?.visibility = View.GONE
        mErrorView?.visibility = View.VISIBLE
        mErrorView?.findViewById<TextView>(R.id.button_reload)?.visibility = View.VISIBLE
        Timber.e(error, "There was an error retrieving the news")
    }


    override fun onReloadData() {
        mMainPresenter.getMostPopular()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)


        mSearchView = getActionView(menu.findItem(R.id.action_search)) as SearchView

        onSearchInvoked(mSearchView as SearchView)

        return super.onCreateOptionsMenu(menu)
    }

    fun onSearchInvoked(searchView: SearchView) {

        val searchText = searchView.findViewById<View>(android.support.v7.appcompat.R.id.search_src_text) as TextView
        searchText.hint = resources.getString(R.string.search_message)

        RxTextView.textChangeEvents(searchText)
                .debounce(600, TimeUnit.MILLISECONDS) // default Scheduler is Computation
                .filter(Predicate<TextViewTextChangeEvent> {
                    if (true)
                        if (!it.text().toString().isEmpty())
                            return@Predicate true
                    false
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getSearchObserver())
    }

    private fun getSearchObserver(): DisposableObserver<TextViewTextChangeEvent> {
        return object : DisposableObserver<TextViewTextChangeEvent>() {
            override fun onComplete() {
                Timber.d("onComplete")
            }

            override fun onError(e: Throwable) {
                Timber.e(e, "onError")
            }

            override fun onNext(onTextChangeEvent: TextViewTextChangeEvent) {

                mItemAdapter.clear()
                mScrollListener?.resetState()
                mMainPresenter.articleSearch(onTextChangeEvent.text().toString(), 0)
            }
        }
    }

}