package com.dms.imagesearch.api.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ImageResponse (
    @SerializedName("data")
    @Expose
    var data: List<Datum>? = null,

    @SerializedName("success")
    @Expose
    var success: Boolean? = null,

    @SerializedName("status")
    @Expose
    var status: Int? = null

)