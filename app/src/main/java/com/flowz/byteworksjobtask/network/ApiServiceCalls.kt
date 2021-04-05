package com.flowz.printfuljobtask.network

import com.flowz.byteworksjobtask.Model.apimodels.Countries
import retrofit2.http.GET

interface ApiServiceCalls {

    @GET("/countries")
    suspend fun FetchCountries(): Countries
}