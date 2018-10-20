package com.apia22018.sportactivities.screens.listActivities

import android.support.v7.recyclerview.extensions.ListAdapter
import android.view.View
import android.view.ViewGroup
import com.apia22018.sportactivities.data.listActivities.Activities
import com.apia22018.sportactivities.screens.detailActivityContainer.DetailActivityContainerActivity

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