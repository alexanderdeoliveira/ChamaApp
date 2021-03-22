package com.chamaapp.list

import androidx.recyclerview.widget.RecyclerView
import com.chamaapp.databinding.ListItemPlacesBinding
import com.google.android.libraries.places.api.model.Place

class PlaceViewHolder (private val binding: ListItemPlacesBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Place?) {
        binding.apply {
            place = item
            executePendingBindings()
        }
    }
}