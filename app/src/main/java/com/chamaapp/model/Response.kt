package com.chamaapp.model

import android.os.Parcelable
import com.chamaapp.util.Status
import com.google.android.libraries.places.api.model.Place
import kotlinx.parcelize.Parcelize

@Parcelize
data class Response (
    var placesList: MutableList<Place>? = null,
    var status: Status = Status.SUCCESS,
    var message: String? = null
) : Parcelable