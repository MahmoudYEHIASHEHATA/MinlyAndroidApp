package com.task.minlyapp.data.repos

import android.content.Context
import com.task.minlyapp.shared.apiWrapper.Error
import com.task.minlyapp.shared.apiWrapper.Resource
import com.task.minlyapp.data.model.GalleryListResponse
import com.task.minlyapp.network.NetworkClient
import com.task.minlyapp.shared.helper.processCall
import okhttp3.MultipartBody

/**
 *Created by Mahmoud Shehatah on 15/7/2021
 */

class GalleryRepository(private val context: Context) {


    suspend fun fetchGallery(): Resource<GalleryListResponse> {

        val response = processCall(context) {
            NetworkClient.galleryServices.gallery()
        }

        return when (response) {
            is GalleryListResponse -> {
                Resource.Success(response)
            }
            else -> {
                Resource.DataError(response as Error)
            }
        }
    }

    suspend fun upImg(file: MultipartBody.Part): Resource<String> {

        val response = processCall(context) {
            NetworkClient.galleryServices.uploadImg(file)
        }

        return when (response) {
            is String -> {
                Resource.Success(response)
            }
            else -> {
                Resource.DataError(response as Error)
            }
        }
    }
}