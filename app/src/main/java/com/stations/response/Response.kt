package com.stations.response

import com.stations.data.ResponseAccessToken
import com.stations.data.StationResponse

sealed class Response {

    object Loading : Response()
    object Failure : Response()
    class SUCCESS(val token: ResponseAccessToken) : Response()
    class SUCCESS_STATIONS(val stationResponse: StationResponse) : Response()

}
