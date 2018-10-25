package com.apia22018.sportactivities.screens.attendee

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.apia22018.sportactivities.data.attendee.Attendee

class AttendeeAdapter(private val attendeeViewModel: AttendeeViewModel) : RecyclerView.Adapter<AttendeeViewHolder>() {

    private val attendees: MutableList<Attendee> = mutableListOf()

    fun setAttendees(attendee: List<Attendee>) {
        this.attendees.apply {
            addAll(attendee)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): AttendeeViewHolder = AttendeeViewHolder.newInstance(parent)

    override fun getItemCount(): Int = this.attendees.size

    override fun onBindViewHolder(holder: AttendeeViewHolder, position: Int) {
        val attendee = this.attendees[position]
        holder.apply {
            bind(attendee)
        }
    }

}