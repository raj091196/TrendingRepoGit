package com.repository.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    val networkStateLiveData = MutableLiveData<Boolean>()
}