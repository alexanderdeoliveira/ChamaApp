package com.chamaapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.chamaapp.R
import com.chamaapp.databinding.FragmentMapBinding
import com.chamaapp.viewmodel.PlacesViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.android.viewmodel.ext.android.sharedViewModel

class MapFragment: Fragment(), OnMapReadyCallback {
    private val placesViewModel by sharedViewModel<PlacesViewModel>()
    private var googleMap: GoogleMap? = null
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        return binding.root
    }

    private fun setupObservables() {
        placesViewModel.response.observe(viewLifecycleOwner, Observer { response ->
            response?.placesList?.let {
                val builder = LatLngBounds.Builder()
                it.forEach { place ->
                    addMarker(place.latLng)
                    builder.include(place.latLng)
                }

                setCamera(builder.build())
            }

        })
    }

    private fun addMarker(position: LatLng?) {
        position?.let {
            googleMap?.addMarker(MarkerOptions().position(it))
        }
    }

    private fun setCamera(bounds: LatLngBounds) {
        val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 8)
        googleMap?.animateCamera(cameraUpdate)
    }

    override fun onMapReady(map: GoogleMap?) {
        googleMap = map
        setupObservables()
    }

}