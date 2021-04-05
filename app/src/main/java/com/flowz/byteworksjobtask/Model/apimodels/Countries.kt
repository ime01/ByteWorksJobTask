package com.flowz.byteworksjobtask.Model.apimodels


data class Countries(
    val code: Int,
    val extra: List<Any>,
    val result: List<Result>
)