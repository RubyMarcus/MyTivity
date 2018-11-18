package com.apia22018.sportactivities.screens.containers

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.view.Menu
import com.apia22018.sportactivities.R
import com.apia22018.sportactivities.data.activities.Activities
import com.apia22018.sportactivities.screens.detail.DetailFragment
import com.apia22018.sportactivities.screens.map.MapFragment
import com.apia22018.sportactivities.screens.message.MessageFragment
import com.apia22018.sportactivities.utils.loadFragment
import kotlinx.android.synthetic.main.detail_container_activity.*

class DetailContainerActivity : AppCompatActivity() {

    companion object {
        private const val VALUE = "value"

        fun start(context: Context, activity: Activities) {
            val intent = Intent(context, DetailContainerActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            context.startActivity(intent.apply {
                putExtras(Bundle().apply {
                    putParcelable(VALUE, activity)
                })
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_container_activity)

        val bundle = intent.extras ?: Bundle()
        val activities: Activities = bundle.getParcelable(VALUE) ?: Activities()

        setSupportActionBar(toolbar_detail)

        supportActionBar?.apply {
            title = "TITLE"
            subtitle = "OPTIONAL SUBTITLE?"
        }

        tabs(activities)

    }

    private fun tabs(activities: Activities){
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        loadFragment(DetailFragment.newInstance(activities))
                    }
                    1 -> {
                        loadFragment(MessageFragment.newInstance(activities))
                    }
                    2 -> {
                        loadFragment(MapFragment.newInstance(activities))
                    }
                }
            }

            override fun onTabReselected(p0: TabLayout.Tab?) {}
            override fun onTabUnselected(p0: TabLayout.Tab?) {}
        })

        loadFragment(DetailFragment.newInstance(activities))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_detail, menu)
        return true
    }
}
