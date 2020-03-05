package com.stations.repository

import com.stations.api.ApiInterface
import com.stations.data.*

interface ApiRepository {

    suspend fun getAccessToken(requestAccessToken: RequestAccessToken): ResponseAccessToken

    suspend fun getChannelsList(requestStations: RequestStations): StationResponse
}

class ApiRepositoryImpl(private val apiInterface: ApiInterface) : ApiRepository {

    override suspend fun getAccessToken(requestAccessToken: RequestAccessToken): ResponseAccessToken {
        return apiInterface.getAccessToken(requestAccessToken)
    }

    override suspend fun getChannelsList(requestStations: RequestStations): StationResponse {
        return apiInterface.getChannelsList(requestStations.city,requestStations.lat, requestStations.lon, requestStations.Authorization)
    }

}

