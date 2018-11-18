package com.apia22018.sportactivities.screens.detail

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.apia22018.sportactivities.R
import com.apia22018.sportactivities.data.attendee.Attendee
import com.apia22018.sportactivities.databinding.AttendeeListItemBinding

class DetailAttendeeViewHolder(private val binding: AttendeeListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun newInstance(parent: ViewGroup, viewModel: DetailViewModel): DetailAttendeeViewHolder {
            val binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context), R.layout.attendee_list_item, parent, false
            ) as AttendeeListItemBinding
            binding.viewModel = viewModel
            return DetailAttendeeViewHolder(binding)
        }
    }

    fun bind(item: Attendee) {
        binding.apply {
            attendee = item
            executePendingBindings()
        }
    }
}