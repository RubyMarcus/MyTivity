package com.apia22018.sportactivities.screens.map

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
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
import android.support.v4.app.ActivityCompat
import com.apia22018.sportactivities.screens.containers.DetailContainerActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.Marker

class MapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {
    private lateinit var viewModel: MapViewModel
    private val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0
    private var mLocationPermissionGranted = false
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var gMap: GoogleMap

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.map_fragment, container, false)

        val factory: MapViewModelFactory = InjectorUtils.provideMapViewModelFactory()
        viewModel = ViewModelProviders
                .of(this, factory)
                .get(MapViewModel::class.java)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.requireActivity())

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return view
    }

    override fun onMapReady(googleMap: GoogleMap) {
        gMap = googleMap

        updateUserLocation()

        viewModel.getActivities().observe(this, Observer { activities ->
            activities?.mapNotNull { item ->
                val activityPosition = LatLng(item.lat, item.long)
                val marker = googleMap.addMarker(MarkerOptions().position(activityPosition))

                marker.tag = item.activityId
                marker.title = item.title
                marker.snippet = item.description
            }
        })

        gMap.setOnInfoWindowClickListener(this)
    }

    override fun onInfoWindowClick(marker: Marker?) {
        marker?.let {
            viewModel.getActivity(it.tag as String)?.also { activities ->
                DetailContainerActivity.start(this.requireContext(), activities)
            }

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
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true
                }
            }
        }
        updateUserLocation()
    }

    private fun updateUserLocation() {
        if (checkPermission()) {
            fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location? ->
                        location?.let {
                            val zoomLevel = 12f
                            val userLocation = LatLng(it.latitude, it.longitude)
                            gMap.isMyLocationEnabled = true
                            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, zoomLevel))
                        }
                    }
        }
    }

    private fun checkPermission(): Boolean {
        return if (ContextCompat.checkSelfPermission(this.requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            true
        } else {
            getLocationPermission()
            false
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MapFragment()
    }
}