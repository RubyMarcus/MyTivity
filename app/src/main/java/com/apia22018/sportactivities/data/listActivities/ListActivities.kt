package com.apia22018.sportactivities.data.listActivities

data class ListActivities(
        val activityId: String,
        val title: String,
        val description: String,
        val totalSeats: Int,
        val occupiedSeats: Int,
        val activityDate: Long
)