package com.dms.imagesearch.api.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AdConfig (
    @SerializedName("safeFlags")
    @Expose
    var safeFlags: List<String>? = null,

    @SerializedName("highRiskFlags")
    @Expose
    var highRiskFlags: List<Any>? = null,

    @SerializedName("unsafeFlags")
    @Expose
    var unsafeFlags: List<Any>? = null,

    @SerializedName("wallUnsafeFlags")
    @Expose
    var wallUnsafeFlags: List<Any>? = null,

    @SerializedName("showsAds")
    @Expose
    var showsAds: Boolean? = null

)