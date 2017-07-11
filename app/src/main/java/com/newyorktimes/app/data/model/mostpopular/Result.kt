package com.newyorktimes.app.data.model.mostpopular

import com.google.gson.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class Result {

    @SerializedName("url")
    @Expose
    var url: String? = null
    @SerializedName("adx_keywords")
    @Expose
    var adxKeywords: String? = null
    @SerializedName("column")
    @Expose
    var column: String? = null
    @SerializedName("section")
    @Expose
    var section: String? = null
    @SerializedName("byline")
    @Expose
    var byline: String? = null
    @SerializedName("type")
    @Expose
    var type: String? = null
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("abstract")
    @Expose
    var abstract: String? = null
    @SerializedName("published_date")
    @Expose
    var publishedDate: String? = null
    @SerializedName("source")
    @Expose
    var source: String? = null
    @SerializedName("id")
    @Expose
    var id: Long? = null
    @SerializedName("asset_id")
    @Expose
    var assetId: Long? = null
    @SerializedName("views")
    @Expose
    var views: Long? = null

    var media: Any? = null

    fun setMediumValues(optionValues: List<Medium>) {
        media = optionValues
    }

    class MediumDeserializer : JsonDeserializer<Result> {

        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Result? {
            var medium = Gson().fromJson<Result>(json, Result::class.java)
            val jsonObject = json.asJsonObject

            //check if response has media key and then check it's type if not empty string
            if (jsonObject.has("media")) {
                val elem = jsonObject.get("media")
                if (elem != null && elem.isJsonArray) {
                    val valuesString = elem.asJsonArray

                    val values = Gson().fromJson<List<Medium>>(valuesString, object : TypeToken<ArrayList<Medium>>() {
                    }.type)
                    medium.setMediumValues(values)
                }
            }
            return medium
        }

    }
}