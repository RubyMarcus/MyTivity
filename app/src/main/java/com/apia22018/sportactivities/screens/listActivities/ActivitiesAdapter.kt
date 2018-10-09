package com.apia22018.sportactivities.screens.listActivities

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.apia22018.sportactivities.data.listActivities.Activities

class ActivitiesAdapter() : RecyclerView.Adapter<ActivitesViewHolder>() {
    private val activities: MutableList<Activities> = mutableListOf()

    fun setActivities(activities: List<Activities>) {
        this.activities.apply {
            clear()
            addAll(activities)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): ActivitesViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount() = this.activities.size

    override fun onBindViewHolder(viewHolder: ActivitesViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}