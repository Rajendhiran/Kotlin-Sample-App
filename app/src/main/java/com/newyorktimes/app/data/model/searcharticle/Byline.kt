package com.newyorktimes.app.data.model.searcharticle

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Byline {

    @SerializedName("person")
    @Expose
    var person: List<Person>? = null
    @SerializedName("original")
    @Expose
    var original: String? = null
    @SerializedName("organization")
    @Expose
    var organization: String? = null

}
