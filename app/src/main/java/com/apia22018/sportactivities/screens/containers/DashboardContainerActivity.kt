package com.apia22018.sportactivities.screens.containers

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.apia22018.sportactivities.R
import com.apia22018.sportactivities.data.activities.Activities
import com.apia22018.sportactivities.screens.list.ListFragment
import com.apia22018.sportactivities.screens.map.MapFragment
import com.apia22018.sportactivities.screens.add.AddActivity
import com.apia22018.sportactivities.screens.login.LoginActivity
import com.apia22018.sportactivities.utils.loadFragment
import com.google.firebase.auth.FirebaseAuth
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
            title = "Activities"
        }

        bottomNavigation()

        floatingActionButton.setOnClickListener {
            AddActivity.start(this)
        }

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
        loadFragment(ListFragment.newInstance())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout_button -> {
                FirebaseAuth.getInstance().signOut()
                LoginActivity.start(this)
                finish()
            }
            else -> { }

        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }
}
