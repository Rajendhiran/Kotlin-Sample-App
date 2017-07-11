package com.newyorktimes.app.data.model.searcharticle

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ArticleSearch {

    @SerializedName("response")
    @Expose
    var response: Response? = null
    @SerializedName("status")
    @Expose
    var status: String? = null
    @SerializedName("copyright")
    @Expose
    var copyright: String? = null

}
