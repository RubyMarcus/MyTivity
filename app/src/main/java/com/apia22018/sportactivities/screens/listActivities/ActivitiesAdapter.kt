package com.apia22018.sportactivities.screens.listActivities

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.apia22018.sportactivities.data.listActivities.Activities

class ActivitiesAdapter : ListAdapter<Activities, ActivitiesViewHolder>(ActivitiesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): ActivitiesViewHolder = ActivitiesViewHolder.newInstance(parent)

    override fun onBindViewHolder(holder: ActivitiesViewHolder, position: Int) {
        val activity = getItem(position)
        holder.apply {
            bind(createOnClickListener(activity.activityId), activity)
        }
    }

    private fun createOnClickListener(activityId: String): View.OnClickListener {
        return View.OnClickListener {
            System.out.println("View got clicked $activityId")
        }
    }

}