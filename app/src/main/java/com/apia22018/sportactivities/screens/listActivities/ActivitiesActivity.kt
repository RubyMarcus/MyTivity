package com.apia22018.sportactivities.screens.listActivities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import com.apia22018.sportactivities.R
import com.apia22018.sportactivities.databinding.ListActivityBinding
import com.apia22018.sportactivities.screens.mapActivities.MapActivity
import com.apia22018.sportactivities.screens.mapActivities.MapFragment
import com.apia22018.sportactivities.screens.newActivities.AddActivityActivity
import com.apia22018.sportactivities.utils.InjectorUtils
import com.apia22018.sportactivities.utils.loadFragment
import kotlinx.android.synthetic.main.fragment_activity_list.*
import kotlinx.android.synthetic.main.list_activity.*
import kotlinx.android.synthetic.main.map_fragment.*

class ActivitiesActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ActivitiesActivity::class.java))
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
            var intent = Intent(this, AddActivityActivity::class.java)
            startActivity(intent)
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
