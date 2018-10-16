package com.apia22018.sportactivities.screens.listActivities

import android.support.v7.util.DiffUtil
import com.apia22018.sportactivities.data.listActivities.Activities

class ActivitiesDiffCallback: DiffUtil.ItemCallback<Activities>() {
    override fun areItemsTheSame(oldItem: Activities, newItem: Activities): Boolean {
        return oldItem.activityId == newItem.activityId
    }

    override fun areContentsTheSame(oldItem: Activities, newItem: Activities): Boolean {
        return oldItem == newItem
    }
}