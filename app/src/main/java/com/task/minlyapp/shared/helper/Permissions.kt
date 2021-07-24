package com.task.minlyapp.shared.helper

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import timber.log.Timber

/**
 *Created by Mahmoud Shehatah on 15/7/2021
 */

const val CAMERA_PERMISSION_REQUEST_CODE = 101
const val CAMERA_PERMISSION = Manifest.permission.CAMERA
const val READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE
const val WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE

fun Activity.checkCameraPermission(): Boolean {
    Timber.i("Checking for camera permissions")
    val permissions = arrayOf(
        CAMERA_PERMISSION,
        READ_EXTERNAL_STORAGE,
        WRITE_EXTERNAL_STORAGE
    )

    return if (ContextCompat.checkSelfPermission(
            this,
            READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
            this,
            WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
            this,
            CAMERA_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        Timber.i( "Permissions Found")
        true
    } else {
        Timber.i("Not Found and asking for them")
        ActivityCompat.requestPermissions(this, permissions, CAMERA_PERMISSION_REQUEST_CODE)
        false
    }
}


