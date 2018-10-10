package com.apia22018.sportactivities.screens.listActivities

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.apia22018.sportactivities.R
import com.apia22018.sportactivities.databinding.ListActivityBinding
import com.apia22018.sportactivities.utils.InjectorUtils
import kotlinx.android.synthetic.main.list_activity.*

class ActivitiesActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ActivitiesActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val factory: ActivitiesViewModelFactory = InjectorUtils.provideActivitesViewModelFactory()
        val viewModel = ViewModelProviders
                .of(this, factory)
                .get(ActivitiesViewModel::class.java)
        val binding: ListActivityBinding = DataBindingUtil.setContentView(this, R.layout.list_activity)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)
        binding.executePendingBindings()

        val adapter = ActivitiesAdapter()

        activities_list.layoutManager = LinearLayoutManager(this)
        activities_list.adapter = adapter
    }
}
