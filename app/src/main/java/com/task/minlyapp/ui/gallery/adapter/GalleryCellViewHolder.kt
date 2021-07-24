package com.task.minlyapp.ui.gallery.adapter

import androidx.recyclerview.widget.RecyclerView
import com.task.minlyapp.databinding.ListItemBinding

class GalleryCellViewHolder(
    val binding: ListItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: String) {
        this.binding.item=data
    }
}