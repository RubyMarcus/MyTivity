package com.apia22018.sportactivities.screens.detailActivities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.apia22018.sportactivities.R
import com.apia22018.sportactivities.data.listActivities.Activities
import com.apia22018.sportactivities.utils.InjectorUtils

class DetailActivitiesActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailActivitiesViewModel

    companion object {
        private const val ID = "id"
        private const val VALUE = "value"

        fun start(context: Context, activity: Activities) {
            context.startActivity(Intent(context, DetailActivitiesActivity::class.java).apply {
                putExtras(Bundle().apply {
                    putParcelable(VALUE, activity)
                })
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activities_activity)

        val bundle = intent.extras ?: Bundle()
        val activities: Activities = bundle.getParcelable(VALUE) ?: Activities()

        val factory: DetailActivitiesViewModelFactory = InjectorUtils.provideDetailActivitiesViewModelFactory(activities)

        viewModel = ViewModelProviders
                .of(this, factory)
                .get(DetailActivitiesViewModel::class.java)

        subscribeToData()
    }

    private fun subscribeToData() {
        viewModel.getLocation().observe(this, Observer {
            println(it)
        })
    }
}
