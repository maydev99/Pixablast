package com.bombadu.pixablast.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bombadu.pixablast.data.local.LocalData
import com.bombadu.pixablast.data.remote.PixabayData
import com.bombadu.pixablast.other.Event
import com.bombadu.pixablast.other.Resource
import com.bombadu.pixablast.repositories.ImagePostRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImagePostViewModel @ViewModelInject constructor (
    private val repository: ImagePostRepository
) : ViewModel() {

    private val _images = MutableLiveData<Event<Resource<PixabayData>>>()
    val images: LiveData<Event<Resource<PixabayData>>> = _images

    private val _curImageUrl = MutableLiveData<String>()
    val curImageUrl: LiveData<String> = _curImageUrl

    fun setCurImageUrl(url: String) {
        _curImageUrl.postValue(url)
    }

    fun searchForImage(imageQuery: String) {
        if (imageQuery.isEmpty()) {
            return
        }

        _images.value = Event(Resource.loading(null))
        viewModelScope.launch {
            val response = repository.searchForImage(imageQuery)
            _images.value = Event(response)
        }
    }

    fun insertEntry(localData: LocalData) = viewModelScope.launch {
        repository.insertEntry(localData)
    }

}