package com.task.minlyapp.shared.apiWrapper

/**
 *Created by Mahmoud Shehatah on 15/7/2021
 */

class GeneralApiResponse<T>(
    val data:T,
    val error: Error
)