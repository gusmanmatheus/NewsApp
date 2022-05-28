package com.example.newsapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.usecases.GetHeadlineUseCase
import com.example.newsapp.presentation.model.HeadlinePresentation
import com.example.newsapp.presentation.model.toPresentation
import kotlinx.coroutines.launch

class MainViewModel(private val getHeadlineUseCase: GetHeadlineUseCase) : ViewModel() {
    private val _listHeadlinesLiveData = MutableLiveData<List<HeadlinePresentation>>()
    val listHeadlinesLiveData: LiveData<List<HeadlinePresentation>> = _listHeadlinesLiveData
    private val _errorLivedata = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLivedata
    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> = _loadingLiveData
    fun getHeadLines() {
        _loadingLiveData.value = true
        viewModelScope.launch {
            getHeadlineUseCase.invoke().onSuccess {
                _loadingLiveData.postValue(false)
                _listHeadlinesLiveData.postValue(it.toPresentation())
            }
                .onFailure {
                    _loadingLiveData.postValue(false)
                    _errorLivedata.postValue(it.message)
                }
        }
    }
}