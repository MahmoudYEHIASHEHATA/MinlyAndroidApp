package com.task.minlyapp.network

import com.task.minlyapp.data.model.GalleryListResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

/**
 *Created by Mahmoud Shehatah on 15/7/2021
 */
interface GalleryServices {

    @GET("/images")
    suspend fun gallery(): Response<GalleryListResponse>

    @Multipart
    @POST("images/upload")
    suspend  fun uploadImg(
        @Part image: MultipartBody.Part
    ): Response<String>
}