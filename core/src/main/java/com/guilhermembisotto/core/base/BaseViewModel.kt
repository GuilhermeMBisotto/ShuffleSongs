package com.guilhermembisotto.core.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.cancel

abstract class BaseViewModel : ViewModel() {

    private val _internetHandlerLiveData = MutableLiveData<Boolean>()
    val internetHandlerLiveData: LiveData<Boolean> =
        Transformations.map(_internetHandlerLiveData) { it }

    fun setInternetConnectionStatus(isConnected: Boolean) =
        _internetHandlerLiveData.postValue(isConnected)

    override fun onCleared() {
        viewModelScope.cancel()
        super.onCleared()
    }
}