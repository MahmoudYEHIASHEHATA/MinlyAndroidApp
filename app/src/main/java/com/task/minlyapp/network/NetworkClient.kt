package com.task.minlyapp.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


class NetworkClient private constructor() {

    companion object{
        const val BASE_URL = "http://192.168.1.3:8088/"

        private fun createOkHttpClient(): OkHttpClient {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(15, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build()
        }
        var gson = GsonBuilder()
            .setLenient()
            .create()
        private val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(createOkHttpClient())
            .baseUrl(BASE_URL)
            .build()

        val galleryServices: GalleryServices by lazy {
            retrofit.create(GalleryServices::class.java)
        }
    }
}