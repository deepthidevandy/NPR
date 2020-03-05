package com.stations.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object OkHttpService {

    private var okHttpClient: OkHttpClient? = null

    private const val gatewayUrl =
        "https://authorization.api.npr.org"

    private const val stationsUrl = "https://station.api.npr.org"

    /*
     * Returns the ApiInterface reference
     */
    fun apiInterface(urlType: Boolean): ApiInterface {
        return Retrofit.Builder()
            .baseUrl(if (urlType) stationsUrl else gatewayUrl)
            .client(httpClient())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(ApiInterface::class.java)
    }

    /*
     * Returns the OkHttpClient instance
     * Here you can Customise the Interceptor
     */
    private fun httpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return okHttpClient ?: OkHttpClient().newBuilder()
            .addInterceptor(DataInterceptor())
            .addInterceptor(logging)
            .build()
    }

}

