package com.apia22018.sportactivities.data.listActivities

import android.content.Context

class ActivitiesRepository {
    operator fun get(context: Context): List<Activities> {
        var activites = mutableListOf<Activities>()

        activites.add(Activities("activityID","Football", "We are going to have fun", 13, 1, 2398472398472398472))
        activites.add(Activities("activityID", "Dancing", "Night out on the town", 5, 2, 34334656556))
        activites.add(Activities("activityID", "Hiking", "Forest walk with some new friends", 4, 1, 304239402349))
        activites.add(Activities("activityID","Football", "We are going to have fun", 13, 1, 2398472398472398472))
        activites.add(Activities("activityID", "Dancing", "Night out on the town", 5, 2, 34334656556))
        activites.add(Activities("activityID", "Hiking", "Forest walk with some new friends", 4, 1, 304239402349))
        activites.add(Activities("activityID","Football", "We are going to have fun", 13, 1, 2398472398472398472))
        activites.add(Activities("activityID", "Dancing", "Night out on the town", 5, 2, 34334656556))
        activites.add(Activities("activityID", "Hiking", "Forest walk with some new friends", 4, 1, 304239402349))
        activites.add(Activities("activityID","Football", "We are going to have fun", 13, 1, 2398472398472398472))
        activites.add(Activities("activityID", "Dancing", "Night out on the town", 5, 2, 34334656556))
        activites.add(Activities("activityID", "Hiking", "Forest walk with some new friends", 4, 1, 304239402349))

        return activites
    }

}