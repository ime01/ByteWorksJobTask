package com.flowz.byteworksjobtask.Model.modelsfromjsonfile


import com.google.gson.annotations.SerializedName

data class State(
    val id: Int,
    val name: String,
    @SerializedName("state_code")
    val stateCode: String
)