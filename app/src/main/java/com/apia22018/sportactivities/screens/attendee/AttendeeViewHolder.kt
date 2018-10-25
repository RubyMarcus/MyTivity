package com.apia22018.sportactivities.screens.attendee

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.databinding.library.baseAdapters.BR
import com.apia22018.sportactivities.data.attendee.Attendee
import com.apia22018.sportactivities.databinding.AttendeeListItemBinding

class AttendeeViewHolder (private val binding: AttendeeListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun newInstance(parent: ViewGroup): AttendeeViewHolder {
            return AttendeeViewHolder(AttendeeListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
            ))
        }
    }

    fun bind(item: Attendee) {
        binding.apply {
            attendee = item
            executePendingBindings()
        }
    }
}