package com.chamaapp

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.chamaapp.list.PlacesAdapter
import com.google.android.libraries.places.api.model.Place

@BindingAdapter("places")
fun places(view: RecyclerView, items: MutableList<Place>?) {
    val dividerItemDecoration = DividerItemDecoration(
        view.context,
        DividerItemDecoration.VERTICAL
    )

    view.addItemDecoration(dividerItemDecoration)
    view.adapter = PlacesAdapter(items)
}