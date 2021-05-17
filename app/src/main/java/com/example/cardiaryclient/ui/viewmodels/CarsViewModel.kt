package com.example.cardiaryclient.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cardiaryclient.dto.AuthToken
import com.example.cardiaryclient.dto.UserCredentials
import com.example.cardiaryclient.models.CarsData
import com.example.cardiaryclient.repositories.CarsRepository
import com.example.cardiaryclient.utils.Resource
import kotlinx.coroutines.launch

class CarsViewModel @ViewModelInject constructor(
    private val carsRepository: CarsRepository
) : ViewModel() {

    private val _res = MutableLiveData<Resource<CarsData>>()

    val res: LiveData<Resource<CarsData>>
        get() = _res

    private val _auth = MutableLiveData<Resource<AuthToken>>()

    val auth: LiveData<Resource<AuthToken>>
        get() = _auth

    fun getCars() {
        viewModelScope.launch {
            _res.postValue(Resource.loading(null))
            carsRepository.getCars().let { response ->
                if(response.isSuccessful) {
                    _res.postValue(Resource.success(response.body()))
                } else {
                    _res.postValue(Resource.error(response.errorBody().toString(), null))
                }
            }
        }
    }

    fun authenticateUser(credentials: UserCredentials) {
        viewModelScope.launch {
            carsRepository.authenticate(credentials).let { response ->
                if(response.isSuccessful) {
                    _auth.postValue(Resource.success(response.body()))
                } else {
                    _auth.postValue(Resource.error(response.errorBody().toString(), null))
                }
            }
        }
    }
}