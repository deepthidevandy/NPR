package com.stations.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stations.data.RequestAccessToken
import com.stations.data.RequestStations
import com.stations.repository.ApiRepository
import com.stations.response.Response
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class AuthViewModel(private val apiRepository: ApiRepository) :
    ViewModel() {

    private val handler = CoroutineExceptionHandler { _, exception ->
        exception.printStackTrace()
        _liveDataAccessToken.postValue(Response.Failure)
    }

    fun getAccessToken(requestAccessToken: RequestAccessToken) {
        viewModelScope.launch(handler) {
            val accessToken = async { apiRepository.getAccessToken(requestAccessToken) }
            _liveDataAccessToken.value = Response.SUCCESS(accessToken.await())

        }
    }

    private val _liveDataAccessToken = MutableLiveData<Response>()

    val liveDataAccessToken: LiveData<Response> = _liveDataAccessToken

}






