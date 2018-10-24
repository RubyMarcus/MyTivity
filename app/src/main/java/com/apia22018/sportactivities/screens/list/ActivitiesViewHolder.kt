package com.apia22018.sportactivities.screens.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.apia22018.sportactivities.data.activities.Activities
import com.apia22018.sportactivities.databinding.ActivitiesListItemBinding

class ActivitiesViewHolder(private val binding: ActivitiesListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun newInstance(parent: ViewGroup): ActivitiesViewHolder {
            return ActivitiesViewHolder(ActivitiesListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
            ))
        }
    }

    fun bind(listener: View.OnClickListener, item: Activities) {
        binding.apply {
            clickListener = listener
            activities = item
            executePendingBindings()
        }
    }
}