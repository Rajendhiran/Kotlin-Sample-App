package com.newyorktimes.app.data.model.searcharticle

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Keyword {

    @SerializedName("isMajor")
    @Expose
    var isMajor: String? = null
    @SerializedName("rank")
    @Expose
    var rank: Int? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("value")
    @Expose
    var value: String? = null

}
