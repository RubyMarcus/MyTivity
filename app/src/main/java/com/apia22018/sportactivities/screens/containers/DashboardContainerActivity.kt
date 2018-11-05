package com.apia22018.sportactivities.screens.containers

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.apia22018.sportactivities.R
import com.apia22018.sportactivities.data.activities.Activities
import com.apia22018.sportactivities.screens.list.ListFragment
import com.apia22018.sportactivities.screens.map.MapFragment
import com.apia22018.sportactivities.screens.add.AddActivity
import com.apia22018.sportactivities.utils.loadFragment
import kotlinx.android.synthetic.main.dashboard_container_activity.*

class DashboardContainerActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, DashboardContainerActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard_container_activity)

        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            title = "TITLE"
            subtitle = "OPTIONAL SUBTITLE?"
        }

        bottomNavigation()

        floatingActionButton.setOnClickListener {
            AddActivity.start(this)
        }

        loadFragment(ListFragment.newInstance())
    }

    private fun bottomNavigation(){
        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_bar_map -> {
                    loadFragment(MapFragment.newInstance(Activities()))
                    true
                }
                R.id.action_bar_list -> {
                    loadFragment(ListFragment.newInstance())
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }
}
