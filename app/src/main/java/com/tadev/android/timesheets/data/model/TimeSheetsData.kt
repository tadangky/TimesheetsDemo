package com.tadev.android.timesheets.data.model

import com.google.gson.annotations.SerializedName

data class Job(
    @SerializedName("type") var type: Int,
    @SerializedName("staff") var staffName: String? = null,
    @SerializedName("orchard") var orchard: String? = null,
    @SerializedName("block") var block: String? = null,
    @SerializedName("rate_type") var rateType: Int = 1,
    @SerializedName("rate") var rate: Int = 0,
    @SerializedName("row") var row: List<Row>? = null,
)

data class Row(
    @SerializedName("active") var active: Boolean = true,
    @SerializedName("name") var name: String?,
    @SerializedName("max") var max: Int,
    @SerializedName("current") var current: Int,
    @SerializedName("other") var other: String?,
    @SerializedName("other_num") var otherNumber: Int? = 0,

)