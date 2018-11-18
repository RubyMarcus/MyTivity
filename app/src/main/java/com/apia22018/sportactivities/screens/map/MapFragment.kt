package com.apia22018.sportactivities.screens.map

import android.Manifest
import android.annotation.SuppressLint
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
    private lateinit var gMap: GoogleMap
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

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.requireActivity())

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return view
    }

    override fun onMapReady(googleMap: GoogleMap) {
        gMap = googleMap

        if (activity.activityId == "") {
            viewModel.getActivities().observe(this, Observer {
                it?.mapNotNull { item ->
                    val marker = googleMap.addMarker(MarkerOptions()
                            .position(LatLng(item.lat, item.long))
                            .title(item.title)
                            .snippet(item.description)
                    )

                    marker.tag = item.activityId
                }

                askPermissionToShowUserLocation()
                if (ContextCompat.checkSelfPermission(requireContext(),
                                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    it?.get(it.size - 1)?.also { activities ->
                        val noUserLocationZoomLevel = 8f
                        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(activities.lat, activities.long), noUserLocationZoomLevel))
                    }
                }
            })

            gMap.setOnInfoWindowClickListener(this)
        } else {
            activity.let {
                val singleActivityPosition = LatLng(it.lat, it.long)
                googleMap.addMarker(MarkerOptions().position(singleActivityPosition))

                gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(singleActivityPosition, zoomLevel))

            }
        }
    }

    override fun onInfoWindowClick(marker: Marker?) {
        marker?.let {
            viewModel.getActivity(it.tag as String)?.also { activities ->
                DetailContainerActivity.start(this.requireContext(), activities)
            }
        }
    }


    @SuppressLint("MissingPermission")
    private fun updateUserLocation() {
        fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    location?.let {
                        gMap.isMyLocationEnabled = true
                        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(it.latitude, it.longitude), zoomLevel))
                    }
                }
    }

    private fun askPermissionToShowUserLocation() {
        askPermission(
                Manifest.permission.ACCESS_FINE_LOCATION
        ) {
            if (it.isAccepted) {
                updateUserLocation()
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
                        }.show();
            }
            if (e.foreverDenied.size > 0) {
//                e.goToSettings()
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