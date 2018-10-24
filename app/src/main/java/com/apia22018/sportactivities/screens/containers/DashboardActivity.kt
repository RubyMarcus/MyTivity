package com.apia22018.sportactivities.screens.containers

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.apia22018.sportactivities.R
import com.apia22018.sportactivities.screens.listActivities.ListFragment
import com.apia22018.sportactivities.screens.mapActivities.MapFragment
import com.apia22018.sportactivities.screens.newActivities.AddActivityActivity
import com.apia22018.sportactivities.utils.loadFragment
import kotlinx.android.synthetic.main.list_activity.*

class DashboardActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, DashboardActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_activity)

        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            title = "TITLE"
            subtitle = "OPTIONAL SUBTITLE?"
        }

        bottomNavigation()

        floatingActionButton.setOnClickListener {
            AddActivityActivity.start(this)
        }

        loadFragment(ListFragment.newInstance())
    }

    private fun bottomNavigation(){
        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_bar_map -> {
                    loadFragment(MapFragment.newInstance())
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
