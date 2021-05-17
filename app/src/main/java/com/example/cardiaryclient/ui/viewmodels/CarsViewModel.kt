package com.example.cardiaryclient.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cardiaryclient.dto.AuthToken
import com.example.cardiaryclient.dto.Credentials
import com.example.cardiaryclient.models.CarsData
import com.example.cardiaryclient.repositories.CarsRepository
import com.example.cardiaryclient.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarsViewModel @Inject constructor(
    private val carsRepository: CarsRepository
) : ViewModel() {

    private val _res = MutableLiveData<Resource<CarsData>>()

    val res: LiveData<Resource<CarsData>>
        get() = _res

    private val _auth = MutableLiveData<Resource<AuthToken>>()

    val auth: LiveData<Resource<AuthToken>>
        get() = _auth

    fun getCars() {
        Log.d("AuthToken", auth.value?.data?.token.toString())
        auth.value?.data?.token.let { token ->
            viewModelScope.launch {
                _res.postValue(Resource.loading(null))
                val authToken = "Bearer $token"
                carsRepository.getCars(authToken).let { response ->
                    if(response.isSuccessful) {
                        _res.postValue(Resource.success(response.body()))
                    } else {
                        _res.postValue(Resource.error(response.errorBody().toString(), null))
                    }
                }
            }
        }
    }

    fun authenticate(credentials: Credentials) {
        viewModelScope.launch {
            carsRepository.authenticate(credentials).let { response ->
                if (response.isSuccessful) {
                    _auth.postValue(Resource.success(response.body()))
                } else {
                    _auth.postValue(Resource.error(response.errorBody().toString(), null))
                }
            }
        }
    }
}