package com.apia22018.sportactivities.screens.list

import android.support.v7.recyclerview.extensions.ListAdapter
import android.view.View
import android.view.ViewGroup
import com.apia22018.sportactivities.data.activities.Activities
import com.apia22018.sportactivities.screens.containers.DetailActivityContainerActivity

class ActivitiesAdapter : ListAdapter<Activities, ActivitiesViewHolder>(ActivitiesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): ActivitiesViewHolder = ActivitiesViewHolder.newInstance(parent)

    override fun onBindViewHolder(holder: ActivitiesViewHolder, position: Int) {
        val activity = getItem(position)
        holder.apply {
            bind(createOnClickListener(activity), activity)
        }
    }

    private fun createOnClickListener(activities: Activities): View.OnClickListener {
        return View.OnClickListener {
            DetailActivityContainerActivity.start(it.context, activities)
        }
    }

}