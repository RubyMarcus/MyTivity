package com.apia22018.sportactivities.screens.containers

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.Menu
import com.apia22018.sportactivities.R
import com.apia22018.sportactivities.data.activities.Activities
import com.apia22018.sportactivities.screens.alertDialog.AlertFragment
import com.apia22018.sportactivities.screens.detail.DetailFragment
import com.apia22018.sportactivities.screens.map.MapFragment
import com.apia22018.sportactivities.screens.message.MessageFragment
import com.apia22018.sportactivities.utils.loadFragment
import kotlinx.android.synthetic.main.detail_container_activity.*

class DetailContainerActivity : AppCompatActivity(), AlertFragment.NoticeDialogListener {


    companion object {
        private const val ID = "id"
        private const val VALUE = "value"

        fun start(context: Context, activity: Activities) {
            context.startActivity(Intent(context, DetailContainerActivity::class.java).apply {
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
        }

        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        loadFragment(DetailFragment.newInstance(activities.activityId))
                    }
                    1 -> {
                        loadFragment(MessageFragment.newInstance(activities))
                    }
                    2 -> {
                        loadFragment(MapFragment.newInstance())
                    }
                }
            }

            override fun onTabReselected(p0: TabLayout.Tab?) {}
            override fun onTabUnselected(p0: TabLayout.Tab?) {}
        })

        loadFragment(DetailFragment.newInstance(activities.activityId))
    }

    //TODO("NEEDS OWN TOOLBAR DESIGN MENU ThING)
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onDialogPositiveClick(dialog: DialogFragment) {
        print("success")
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {

    }

}
