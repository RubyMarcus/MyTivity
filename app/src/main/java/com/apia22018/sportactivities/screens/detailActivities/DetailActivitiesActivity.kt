package com.apia22018.sportactivities.screens.detailActivities

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.apia22018.sportactivities.R
import com.apia22018.sportactivities.utils.InjectorUtils

class DetailActivitiesActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailActivitiesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activities_activity)

        val factory: DetailActivitiesViewModelFactory = InjectorUtils.provideDetailActivitiesViewModelFactory()

        viewModel = ViewModelProviders
                .of(this, factory)
                .get(DetailActivitiesViewModel::class.java)
    }
}
