package com.apia22018.sportactivities.screens.detail

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.apia22018.sportactivities.data.attendee.Attendee

class DetailAttendeeAdapter(private val detailViewModel: DetailViewModel) : RecyclerView.Adapter<DetailAttendeeViewHolder>() {

    private val attendees: MutableList<Attendee> = mutableListOf()

    fun setAttendees(attendee: List<Attendee>) {
        this.attendees.apply {
            addAll(attendee)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): DetailAttendeeViewHolder = DetailAttendeeViewHolder.newInstance(parent, detailViewModel)

    override fun getItemCount(): Int = this.attendees.size

    override fun onBindViewHolder(holder: DetailAttendeeViewHolder, position: Int) = holder.bind(attendees[position])



    /*{
        val attendee = this.attendees[position]
        holder.apply {
            bind(attendee)
        }
    }*/

}