package com.chamaapp.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.chamaapp.util.Status
import com.chamaapp.databinding.FragmentPlacesListBinding
import com.chamaapp.viewmodel.PlacesViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel


class PlacesListFragment: Fragment() {
    private val placesViewModel by sharedViewModel<PlacesViewModel>()

    private var _binding: FragmentPlacesListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentPlacesListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = this@PlacesListFragment
            viewModel = this@PlacesListFragment.placesViewModel
        }

        setupView()
        setupObservables()

    }

    private fun setupObservables() {
        placesViewModel.status.observe(viewLifecycleOwner, Observer { status ->
            when(status) {
                Status.NO_PERMISSION -> getLocationPermission()
                else -> {}
            }
        })
    }

    private fun setupView() {
        placesViewModel.requestPlaces(requireContext())
    }

    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) { } else {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_REQUEST_CODE)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    placesViewModel.requestPlaces(requireContext())
                }
                return
            }
            else -> { }
        }

    }

    companion object {
        const val PERMISSION_REQUEST_CODE = 1
    }

}