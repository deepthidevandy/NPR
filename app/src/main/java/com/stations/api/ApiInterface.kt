package com.stations.api

import com.stations.data.RequestAccessToken
import com.stations.data.ResponseAccessToken
import com.stations.data.StationResponse
import com.stations.util.Constants
import retrofit2.http.*

interface ApiInterface {

    @POST(Constants.USER_AUTH)
    suspend fun getAccessToken(@Body requestAccessToken: RequestAccessToken): ResponseAccessToken

    @GET(Constants.CHANNELS)
    suspend fun getChannelsList(
        @Query("q") city: String?,
        @Query("lat") lat: String?,
        @Query("lon") lon: String?,
        @Header("Authorization") authorization: String?
    ): StationResponse

}