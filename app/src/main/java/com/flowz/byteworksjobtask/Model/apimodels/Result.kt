package com.flowz.byteworksjobtask.Model.apimodels


import com.google.gson.annotations.SerializedName

data class Result(
    val code: String,
    val name: String,
    val states: Any
)