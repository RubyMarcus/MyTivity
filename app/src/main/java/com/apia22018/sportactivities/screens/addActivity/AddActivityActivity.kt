package com.apia22018.sportactivities.screens.addActivity

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.apia22018.sportactivities.R
import com.apia22018.sportactivities.databinding.AddActivityActivityBinding

class AddActivityActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_activity_activity)

        val binding: AddActivityActivityBinding = DataBindingUtil.setContentView(this, R.layout.add_activity_activity)

        val toolbar = binding.toolbar1
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            title = "Add Activity"
            setDisplayHomeAsUpEnabled(true)
        }

    }
}
