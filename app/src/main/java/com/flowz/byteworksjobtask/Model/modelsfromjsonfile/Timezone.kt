package com.flowz.byteworksjobtask.Model.modelsfromjsonfile


import com.google.gson.annotations.SerializedName

data class Timezone(
    val abbreviation: String,
    val gmtOffset: Int,
    val gmtOffsetName: String,
    val tzName: String,
    val zoneName: String
)