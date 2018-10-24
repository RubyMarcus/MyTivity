package com.apia22018.sportactivities.screens.mapActivities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.apia22018.sportactivities.R
import com.apia22018.sportactivities.utils.InjectorUtils
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : Fragment() {

    private lateinit var viewModel: MapViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val factory: MapViewModelFactory = InjectorUtils.provideMapViewModelFactory()
        viewModel = ViewModelProviders
                .of(this, factory)
                .get(MapViewModel::class.java)

        return inflater.inflate(R.layout.map_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync { gMap ->

//            viewModel.getLocation().observe(this, Observer { locations ->
//                locations?.forEach {
//                    val marker = LatLng(it.lat, it.long)
//                    gMap.addMarker(MarkerOptions().position(marker).title("title"))
//                    gMap.moveCamera(CameraUpdateFactory.newLatLng(marker))
//
//                }
//            })
        }
    }

    companion object {
        @JvmStatic
        fun newInstance () = MapFragment()
    }
}