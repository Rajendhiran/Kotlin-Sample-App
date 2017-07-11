package com.newyorktimes.app.common

import com.newyorktimes.app.data.model.mostpopular.MostPopular
import com.newyorktimes.app.data.model.mostpopular.Result
import com.newyorktimes.app.data.model.searcharticle.ArticleSearch
import com.newyorktimes.app.data.model.searcharticle.Response
import java.util.*
import kotlin.collections.ArrayList

/**
 * Factory class that makes instances of data models with random field values.
 * The aim of this class is to help setting up test fixtures.
 */
object TestDataFactory {

    private val sRandom = Random()

    fun randomUuid(): String {
        return UUID.randomUUID().toString()
    }

    fun emptyList(): List<com.newyorktimes.app.data.model.mostpopular.Result>? {
        return ArrayList()
    }

    fun makeMostPopular(id: String): MostPopular {
        val mostPopular = MostPopular()
        mostPopular.results = emptyList()
        mostPopular.copyright = randomUuid()
        mostPopular.status = randomUuid()
        mostPopular.numResults = id.toInt()
        return mostPopular
    }

    fun makeArticleSearch(id: String): ArticleSearch {
        val articleSearch = ArticleSearch()
        val mResponse = Response()
        mResponse.docs = ArrayList()
        articleSearch.response = mResponse
        articleSearch.copyright = randomUuid()
        articleSearch.status = randomUuid()
        return articleSearch
    }

    fun makeResult(id: String): Result {
        val result = Result()
        result.media = null
        result.abstract = randomUuid()
        result.adxKeywords = randomUuid()
        result.assetId = id.toLong()
        result.byline = randomUuid()
        result.column = randomUuid()
        result.id = id.toLong()
        result.section = randomUuid()
        result.title = randomUuid()
        result.source = randomUuid()
        result.type = randomUuid()
        result.url = randomUuid()
        return result
    }

    fun makeMostPopularList(count: Int): List<MostPopular> {
        val pokemonList = (0..count - 1).mapTo(ArrayList<MostPopular>()) { makeMostPopular(it.toString()) }
        return pokemonList
    }

    fun makeResultList(count: Int): List<Result> {
        val pokemonList = (0..count - 1).mapTo(ArrayList<Result>()) { it -> makeResult(it.toString()) }
        return pokemonList
    }

}