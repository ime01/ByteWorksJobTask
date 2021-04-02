package com.flowz.byteworksjobtask.Model


import com.google.gson.annotations.SerializedName

data class Countries(
    val code: Int,
    val extra: List<Any>,
    val result: List<Result>
)