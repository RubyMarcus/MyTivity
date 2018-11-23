package com.apia22018.sportactivities.screens.map

import android.Manifest
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
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
import com.apia22018.sportactivities.data.activities.Activities
import com.apia22018.sportactivities.screens.containers.DetailContainerActivity
import com.github.florent37.runtimepermission.kotlin.askPermission
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.Marker

class MapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private lateinit var viewModel: MapViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val zoomLevel = 12f
    private lateinit var activity: Activities

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.map_fragment, container, false)

        val bundle = arguments ?: Bundle()
        activity = bundle.getParcelable(MapFragment.VALUE) ?: Activities()

        val factory: MapViewModelFactory = InjectorUtils.provideMapViewModelFactory()
        viewModel = ViewModelProviders
                .of(this, factory)
                .get(MapViewModel::class.java)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.requireActivity())

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        if (activity.activityId == "") {
            viewModel.getActivities().observe(this, Observer { activities ->
                activities?.map { placeMarker(it, googleMap) }

                askPermissionToShowUserLocation { granted ->
                    placeUserOnMapIfPermissionGranted(granted, activities, googleMap)
                }
                googleMap.setOnInfoWindowClickListener(this)
            })
        } else {
            activity.let {
                placeMarker(it, googleMap)
                positionCameraOnMap(googleMap, it.lat, it.long)
            }
        }
    }

    private fun positionCameraOnMap(googleMap: GoogleMap, lat: Double, long: Double) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, long), zoomLevel))
    }

    private fun placeUserOnMapIfPermissionGranted(granted: Boolean, activities: List<Activities>?, googleMap: GoogleMap) {
        if (granted && ContextCompat.checkSelfPermission(requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location? ->
                        location?.run {
                            googleMap.isMyLocationEnabled = true
                            positionCameraOnMap(googleMap, this.latitude, this.longitude)
                        }
                    }
        } else {
            activities?.get(activities.size - 1)?.run {
                val noUserLocationZoomLevel = 8f
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(this.lat, this.long), noUserLocationZoomLevel))
            }
        }
    }

    private fun placeMarker(it: Activities, googleMap: GoogleMap) {
        val marker = googleMap.addMarker(MarkerOptions()
                .position(LatLng(it.lat, it.long))
                .title(it.title)
                .snippet(it.description)
        )

        marker.tag = it.activityId
    }

    override fun onInfoWindowClick(marker: Marker?) {
        marker?.let {
            viewModel.getActivity(it.tag as String)?.also { activities ->
                DetailContainerActivity.start(this.requireContext(), activities)
            }
        }
    }

    private fun askPermissionToShowUserLocation(granted: (Boolean) -> Unit = {}) {
        askPermission(
                Manifest.permission.ACCESS_FINE_LOCATION
        ) {
            if (it.isAccepted) {
                granted(true)
            }
        }.onDeclined { e ->
            if (e.denied.size > 0) {
                AlertDialog.Builder(requireContext())
                        .setMessage("Please accept the permission so that we can provide your position on the map")
                        .setPositiveButton("yes") { _: DialogInterface, _: Int ->
                            e.askAgain()
                        }
                        .setNegativeButton("no") { dialog: DialogInterface, _: Int ->
                            dialog.dismiss()
                            granted(false)
                        }.show();
            }
            if (e.foreverDenied.size > 0) {
//                e.goToSettings()
                granted(false)
            }
        }
    }

    companion object {
        private const val VALUE = "value"

        @JvmStatic
        fun newInstance(activities: Activities) = MapFragment().apply {
            arguments = Bundle().apply {
                putParcelable(VALUE, activities)
            }
        }
    }
}