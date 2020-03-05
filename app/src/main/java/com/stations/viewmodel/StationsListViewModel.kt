package com.stations.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stations.data.RequestStations
import com.stations.repository.ApiRepository
import com.stations.response.Response
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class StationsListViewModel(private val apiRepository: ApiRepository) :
    ViewModel() {

    private val handler = CoroutineExceptionHandler { _, exception ->
        exception.printStackTrace()
        _liveDataAccessToken.postValue(Response.Failure)
    }

    fun getStationsList(requestStations: RequestStations) {
        viewModelScope.launch(handler) {
            val stations = async { apiRepository.getChannelsList(requestStations) }
            _liveDataAccessToken.value = Response.SUCCESS_STATIONS(stations.await())

        }
    }

    private val _liveDataAccessToken = MutableLiveData<Response>()

    val liveDataAccessToken: LiveData<Response> = _liveDataAccessToken

}






