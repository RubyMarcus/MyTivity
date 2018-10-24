package com.apia22018.sportactivities.screens.mapActivities

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.apia22018.sportactivities.R
import com.apia22018.sportactivities.utils.InjectorUtils

class MapFragment : Fragment() {

    private lateinit var viewModel: MapViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.map_fragment, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance () = MapFragment()
    }
}