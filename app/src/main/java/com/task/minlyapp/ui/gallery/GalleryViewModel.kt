package com.task.minlyapp.ui.gallery

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.task.minlyapp.data.model.GalleryListResponse
import com.task.minlyapp.data.repos.GalleryRepository
import kotlinx.coroutines.*
import timber.log.Timber
import com.task.minlyapp.shared.apiWrapper.Resource
import okhttp3.MultipartBody

/**
 *Created by Mahmoud Shehatah on 15/7/2021
 */

class GalleryViewModel(
    application: Application,
    private val repository: GalleryRepository
) : AndroidViewModel(application) {


    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val currentGalleryApiWrapper = MutableLiveData<Resource<GalleryListResponse>>()
    val currentUpImgApiWrapper = MutableLiveData<Resource<String>>()

    var galleryItemsList = MutableLiveData<List<String?>>()

    init {
        Timber.i("${this.javaClass.name} created")
        fetchGalleryData()
    }

    fun fetchGalleryData() {
        uiScope.launch {
            currentGalleryApiWrapper.value =Resource.Loading()
            withContext(Dispatchers.IO) {
                val response = repository.fetchGallery()
                currentGalleryApiWrapper.postValue(response)
                galleryItemsList.postValue(response.data)
            }
        }
    }

    fun upImageToServer(filePart:  MultipartBody.Part) {
        uiScope.launch {
            currentUpImgApiWrapper.value =Resource.Loading()
            withContext(Dispatchers.IO) {
                val response = repository.upImg(filePart)
                currentUpImgApiWrapper.postValue(response)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
        Timber.i("${this.javaClass.name} cleared")
    }
}