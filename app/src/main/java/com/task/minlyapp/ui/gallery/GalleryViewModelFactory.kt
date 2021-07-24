package com.task.minlyapp.ui.gallery

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.task.minlyapp.data.repos.GalleryRepository

/**
 *Created by Mahmoud Shehatah on 15/7/2021
 */
class GalleryViewModelFactory(
    private val application: Application,
    private val repository: GalleryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GalleryViewModel::class.java)) {
            return GalleryViewModel(application, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}