package com.example.newsapp.presentation.headlinelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.usecases.GetHeadlineUseCase
import com.example.newsapp.presentation.SingleLiveEvent
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
    private val _onNextPage = SingleLiveEvent<HeadlinePresentation>()
    val onNextPage: LiveData<HeadlinePresentation> get() = _onNextPage

    fun getHeadLines() {
        viewModelScope.launch {
            _loadingLiveData.postValue(true)
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

    fun goToNextPage(headLine: HeadlinePresentation) {
        _onNextPage.value = headLine
    }
}