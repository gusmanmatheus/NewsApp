package com.example.newsapp.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.usecases.GetHeadlineUseCase
import com.example.newsapp.presentation.model.HeadlinePresentation
import com.example.newsapp.presentation.model.toPresentation
import kotlinx.coroutines.launch

class MainViewModel(private val getHeadlineUseCase: GetHeadlineUseCase) : ViewModel() {
    private val _listHeadlines = MutableLiveData<List<HeadlinePresentation>>()
    val listHeadlines: LiveData<List<HeadlinePresentation>> = _listHeadlines
    private val _errorLivedata = MutableLiveData<String>()
    private val errorLiveData: LiveData<String> = _errorLivedata
    private val _loadingLiveData = MutableLiveData<Boolean>()
    private val loadingLiveData: LiveData<Boolean> = _loadingLiveData
    fun getHeadLines() {
        viewModelScope.launch {
            _loadingLiveData.value = true
            getHeadlineUseCase.invoke().onSuccess {
                _loadingLiveData.postValue(false)
                _listHeadlines.postValue(it.asReversed().toPresentation())
            }
                .onFailure {
                    _loadingLiveData.postValue(false)
                    _errorLivedata.postValue(it.message)
                }

        }
    }
}