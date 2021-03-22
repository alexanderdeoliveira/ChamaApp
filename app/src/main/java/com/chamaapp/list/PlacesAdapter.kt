package com.chamaapp.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chamaapp.R
import com.chamaapp.databinding.ListItemPlacesBinding
import com.google.android.libraries.places.api.model.Place

class PlacesAdapter(
    private val places: List<Place>? = null
): RecyclerView.Adapter<PlaceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder =
        DataBindingUtil.inflate<ListItemPlacesBinding>(
            LayoutInflater.from(parent.context),
            R.layout.list_item_places,
            parent,
            false
        ).let { PlaceViewHolder(it) }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) = holder.bind(places?.get(position))

    override fun getItemCount(): Int {
        return if(places.isNullOrEmpty()) {
            0
        } else {
            places.size
        }
    }

}