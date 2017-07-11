package com.newyorktimes.app.data.model.searcharticle

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Headline {

    @SerializedName("main")
    @Expose
    var main: String? = null
    @SerializedName("print_headline")
    @Expose
    var printHeadline: String? = null

}
