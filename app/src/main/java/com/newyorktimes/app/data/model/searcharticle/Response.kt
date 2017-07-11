package com.newyorktimes.app.data.model.searcharticle

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Response {

    @SerializedName("meta")
    @Expose
    var meta: Meta? = null
    @SerializedName("docs")
    @Expose
    var docs: List<Doc>? = null

}
