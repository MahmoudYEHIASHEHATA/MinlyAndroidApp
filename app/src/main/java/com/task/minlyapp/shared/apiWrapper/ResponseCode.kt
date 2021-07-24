package com.task.minlyapp.shared.apiWrapper

/**
 *Created by Mahmoud Shehatah on 15/7/2021
 */
enum class  ResponseCode(val value: Int) {
    OK(200),UNAUTHORIZED(401),INTERNAL_SERVER(500),NETWORK_NOT_AVAILABLE(600),NETWORK_ERROR(700),GENERAL_ERROR(800)
}