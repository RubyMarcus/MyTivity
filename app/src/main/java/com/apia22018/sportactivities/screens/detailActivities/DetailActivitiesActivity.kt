package com.apia22018.sportactivities.screens.detailActivities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.apia22018.sportactivities.R
import com.apia22018.sportactivities.data.listActivities.Activities
import com.apia22018.sportactivities.screens.message.MessageFragment
import com.apia22018.sportactivities.utils.loadFragment
import kotlinx.android.synthetic.main.detail_activities_activity.*

class DetailActivitiesActivity : AppCompatActivity() {

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

//        val bundle = intent.extras ?: Bundle()
//        val activities: Activities = bundle.getParcelable(VALUE) ?: Activities()

        setSupportActionBar(toolbar_detail)

        supportActionBar?.apply {
            title = "TITLE"
        }

        bottom_nav_detail.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_activity -> {
                    println("Activity")
//                    loadFragment(ActivityFragment.newInstance())
                    true
                }
                R.id.action_message -> {
                    loadFragment(MessageFragment.newInstance())
                    true
                }
                R.id.action_map -> {
                    println("map")
//                    loadFragment(MapFragment.newInstance())
                    true
                }

                else -> false
            }
        }

        loadFragment(MessageFragment.newInstance())
    }

    //TODO("NEEDS OWN TOOLBAR DESIGN MENU ThING)
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }
}
