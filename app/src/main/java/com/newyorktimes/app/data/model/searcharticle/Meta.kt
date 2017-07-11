package com.newyorktimes.app.data.model.searcharticle

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Meta {

    @SerializedName("hits")
    @Expose
    var hits: Int? = null
    @SerializedName("time")
    @Expose
    var time: Int? = null
    @SerializedName("offset")
    @Expose
    var offset: Int? = null

}
