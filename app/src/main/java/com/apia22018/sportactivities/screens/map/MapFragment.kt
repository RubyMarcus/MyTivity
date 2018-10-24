package com.apia22018.sportactivities.screens.map

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.getSystemService
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.apia22018.sportactivities.R
import com.apia22018.sportactivities.utils.InjectorUtils
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.jar.Manifest
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat.checkSelfPermission
import android.util.Log
import com.google.android.gms.common.GooglePlayServicesUtil.isGooglePlayServicesAvailable
import com.google.android.gms.maps.GoogleMap

class MapFragment : Fragment() {

    private lateinit var viewModel: MapViewModel
    private val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0
    private var mLocationPermissionGranted = false
    private lateinit var fusedLocationClient : FusedLocationProviderClient
    private lateinit var googleMap : GoogleMap

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val factory: MapViewModelFactory = InjectorUtils.provideMapViewModelFactory()

        viewModel = ViewModelProviders
                .of(this, factory)
                .get(MapViewModel::class.java)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.requireActivity())

        return inflater.inflate(R.layout.map_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateLocationUI()

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync { gMap ->

            googleMap = gMap

            viewModel.getLocation().observe(this, Observer { locations ->
                locations?.forEach {
                    val marker = LatLng(it.lat, it.long)
                    gMap.addMarker(MarkerOptions().position(marker).title("title"))
                }
            })
        }
    }

    private fun getLocationPermission() {
        /*
     * Request location permission, so that we can get the location of the
     * device. The result of the permission request is handled by a callback,
     * onRequestPermissionsResult.
     */
        if (ContextCompat.checkSelfPermission(this.requireContext(),
                        android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(this.requireActivity(),
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        mLocationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true
                }
            }
        }
        updateLocationUI()
    }

    private fun updateLocationUI() {
        if (checkPermission()) {
            fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location? ->
                        location?.let {
                            val zoomLevel = 12f
                            val userLocation = LatLng(it.latitude, it.longitude)
                            googleMap.isMyLocationEnabled = true
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, zoomLevel))
                        }
                    }
        }
    }

    private fun checkPermission() : Boolean {
        if (ContextCompat.checkSelfPermission(this.requireContext() , android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            getLocationPermission()
            return false
        }
    }

    companion object {
        @JvmStatic
        fun newInstance () = MapFragment()
    }
}