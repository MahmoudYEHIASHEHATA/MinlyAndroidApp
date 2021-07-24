package com.task.minlyapp.ui.gallery.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.task.minlyapp.databinding.ListItemBinding

class GalleryItemsAdapter : ListAdapter<String, GalleryCellViewHolder>(
        DiffUtilCallback
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryCellViewHolder {
        return GalleryCellViewHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GalleryCellViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    companion object DiffUtilCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(
            oldItem: String,
            newItem: String
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: String,
            newItem: String
        ): Boolean {
            return oldItem == newItem
        }
    }
}