package com.example.cardiaryclient.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
}