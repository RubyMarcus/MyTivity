package com.apia22018.sportactivities.data.listActivities

data class Activities(
        val title: String = "",
        val description: String = "",
        val totalSeats: Int = 0,
        val occupiedSeats: Int = 0,
        val activityDate: Long = 0,
        var activityId: String = ""
) {

    fun emptySeaterinos(): String {
        val emptySeats = totalSeats - occupiedSeats
        return emptySeats.toString()
    }
}