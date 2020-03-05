package com.stations.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.stations.api.OkHttpService
import com.stations.repository.ApiRepositoryImpl

class ViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> {
                AuthViewModel( ApiRepositoryImpl(OkHttpService.apiInterface(false))) as T
            }
            modelClass.isAssignableFrom(StationsListViewModel::class.java) -> {
                StationsListViewModel( ApiRepositoryImpl(OkHttpService.apiInterface(true))) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}