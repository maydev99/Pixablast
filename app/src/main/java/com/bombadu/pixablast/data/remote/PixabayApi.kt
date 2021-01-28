package com.bombadu.pixablast.data.remote


import com.bombadu.pixablast.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApi {
    @GET("/api/")
    suspend fun searchForImage(
        @Query("q") searchQuery: String,
        @Query("key") apiKey: String = BuildConfig.API_KEY
    ) : Response<PixabayData>
}