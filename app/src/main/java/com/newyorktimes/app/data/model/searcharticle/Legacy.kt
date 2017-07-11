package com.newyorktimes.app.data.model.searcharticle

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Legacy {

    @SerializedName("xlargewidth")
    @Expose
    var xlargewidth: Int? = null
    @SerializedName("xlarge")
    @Expose
    var xlarge: String? = null
    @SerializedName("xlargeheight")
    @Expose
    var xlargeheight: Int? = null
    @SerializedName("wide")
    @Expose
    var wide: String? = null
    @SerializedName("widewidth")
    @Expose
    var widewidth: Int? = null
    @SerializedName("wideheight")
    @Expose
    var wideheight: Int? = null
    @SerializedName("thumbnailheight")
    @Expose
    var thumbnailheight: Int? = null
    @SerializedName("thumbnail")
    @Expose
    var thumbnail: String? = null
    @SerializedName("thumbnailwidth")
    @Expose
    var thumbnailwidth: Int? = null

}
