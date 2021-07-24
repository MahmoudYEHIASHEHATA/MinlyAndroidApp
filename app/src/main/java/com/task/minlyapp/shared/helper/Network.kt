package com.task.minlyapp.shared.helper

import android.content.Context
import android.net.ConnectivityManager
import com.google.gson.Gson
import com.task.minlyapp.R
import com.task.minlyapp.shared.apiWrapper.GeneralApiResponse
import com.task.minlyapp.shared.apiWrapper.Error

import retrofit2.Response
import java.io.IOException
import com.task.minlyapp.shared.apiWrapper.ResponseCode

/**
 *Created by Mahmoud Shehatah on 15/7/2021
 */

fun isConnected(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return (cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnectedOrConnecting)
}


suspend fun processCall(
    context: Context,
    request: suspend () -> Response<*>
): Any? {

    return if (!isConnected(context)) {
        Error(
            ResponseCode.NETWORK_NOT_AVAILABLE.value,
            context.getString(R.string.no_network)
        )
    } else {
        try {
            val response = request.invoke()

            return when (val responseCode = response.code()) {
                ResponseCode.OK.value -> {
                    response.body()
                }
                else -> {

                    val errorResponse = Gson().fromJson(
                        response.errorBody()?.charStream(),
                        GeneralApiResponse::class.java
                    )

                    Error(
                        responseCode,
                        errorResponse.error.message
                    )
                }
            }
        } catch (t: Throwable) {


            if (t is IOException) {
                Error(
                    ResponseCode.NETWORK_ERROR.value,
                    t.message.toString()
                )
            } else {
                Error(
                    ResponseCode.GENERAL_ERROR.value,
                    t.message.toString()
                )
            }
        }
    }
}