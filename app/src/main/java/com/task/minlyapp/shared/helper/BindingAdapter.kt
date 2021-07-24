package com.task.minlyapp.shared.helper

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.task.minlyapp.R
import com.task.minlyapp.network.NetworkClient
import com.task.minlyapp.ui.gallery.adapter.GalleryItemsAdapter

/**
 *Created by Mahmoud Shehatah on 15/7/2021
 */


@BindingAdapter("galleryListItems")
fun RecyclerView.galleryListItems(items: List<String>?) {
    val adapter = this.adapter as GalleryItemsAdapter
    adapter.submitList(items)
}


@BindingAdapter(value = ["imagePath"], requireAll = true)
fun ImageView.loadImageFromPath(path: String?) {
    path?.let {
        this.loadImageFromPathByGlide(it)
    }
}


fun ImageView.loadImageFromPathByGlide(
    path: String,
) {
    val imageUri = "${NetworkClient.BASE_URL}$path"

    Glide
        .with(context)
        .load(imageUri)
        .error(R.drawable.ic_launcher_background)
        .centerCrop()
        .into(this)
}
