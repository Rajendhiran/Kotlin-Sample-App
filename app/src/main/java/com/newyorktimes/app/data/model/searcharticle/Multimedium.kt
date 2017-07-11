package com.newyorktimes.app.data.model.searcharticle

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Multimedium {

    @SerializedName("width")
    @Expose
    var width: Int? = null
    @SerializedName("url")
    @Expose
    var url: String? = null
    @SerializedName("rank")
    @Expose
    var rank: Int? = null
    @SerializedName("height")
    @Expose
    var height: Int? = null
    @SerializedName("subtype")
    @Expose
    var subtype: String? = null
    @SerializedName("legacy")
    @Expose
    var legacy: Legacy? = null
    @SerializedName("type")
    @Expose
    var type: String? = null

}
