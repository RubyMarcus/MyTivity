package com.apia22018.sportactivities.screens.listActivities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import com.apia22018.sportactivities.R
import com.apia22018.sportactivities.R.menu.bottom_nav
import com.apia22018.sportactivities.databinding.ListActivityBinding
import com.apia22018.sportactivities.utils.InjectorUtils
import kotlinx.android.synthetic.main.list_activity.*

class ActivitiesActivity : AppCompatActivity() {

    private lateinit var viewModel: ActivitiesViewModel

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ActivitiesActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ListActivityBinding = DataBindingUtil.setContentView(this, R.layout.list_activity)

        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            title = "TITLE"
            subtitle = "OPTIONAL SUBTITLE?"
        }

        val factory: ActivitiesViewModelFactory = InjectorUtils.provideActivitiesViewModelFactory()
        viewModel = ViewModelProviders
                .of(this, factory)
                .get(ActivitiesViewModel::class.java)

        val adapter = ActivitiesAdapter()

        activities_list.layoutManager = LinearLayoutManager(this)
        binding.activitiesList.adapter = adapter

        subscribeUI(adapter)
        bottomNavigation()
    }

    private fun subscribeUI(adapter: ActivitiesAdapter) {
        viewModel.getActivities().observe(this, Observer { activities ->
            if (activities != null) adapter.submitList(activities)
        })
    }

    private fun bottomNavigation(){
        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_bar_map -> println("MAPS ACTIVITY")
                R.id.action_bar_list -> println("NOTHING?")
                else -> {
                    // Something else...
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }
}
