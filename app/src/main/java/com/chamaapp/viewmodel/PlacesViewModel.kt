package com.chamaapp.viewmodel

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chamaapp.BuildConfig
import com.chamaapp.R
import com.chamaapp.model.Response
import com.chamaapp.util.Status
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.PlaceLikelihood
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest

class PlacesViewModel: ViewModel() {
    private val mutableResponse = MutableLiveData<Response?>()
    val response: LiveData<Response?> get() = mutableResponse

    private val mutableStatus = MutableLiveData<Status>()
    val status: LiveData<Status> get() = mutableStatus

    private val mutableLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = mutableLoading

    fun requestPlaces(context: Context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED) {
            mutableLoading.postValue(true)

            val responseResult = Response()
            Places.initialize(context, BuildConfig.GOOGLE_MAPS_API_KEY)
            val placesClient = Places.createClient(context)
            val placeFields: List<Place.Field> = listOf(Place.Field.NAME, Place.Field.LAT_LNG)
            val request: FindCurrentPlaceRequest = FindCurrentPlaceRequest.newInstance(placeFields)
            val placeResponse = placesClient.findCurrentPlace(request)
            val placesList: MutableList<Place> = mutableListOf()
            placeResponse.addOnCompleteListener { task ->
                mutableLoading.postValue(false)
                if (task.isSuccessful) {
                    val response = task.result
                    for (placeLikelihood: PlaceLikelihood in response?.placeLikelihoods ?: emptyList()) {
                        placesList.add(placeLikelihood.place)
                    }

                    responseResult.placesList = placesList
                    responseResult.status = Status.SUCCESS
                    mutableResponse.postValue(responseResult)

                } else {
                    val exception = task.exception
                    responseResult.status = Status.ERROR
                    if (exception is ApiException) {
                        exception.message.also { responseResult.message = it }
                    }

                    mutableResponse.postValue(responseResult)
                }
            }
        } else {
            mutableStatus.postValue(Status.NO_PERMISSION)
        }
    }
}