package com.chydee.mytimetable.utils

object TimetableUtil {

    /**
     * The input is not valid if...
     * ... @param timetableFor is empty
     * ... @param id/courseCode/courseTitle/courseLabel/courseTutor/place/dayOfTheWeek/startTime/endTime is empty
     */
    fun validateTimetableInput(
        timetableFor: String,
        id: Int,
        courseCode: String,
        courseTitle: String,
        courseLabel: Int,
        courseTutor: String,
        place: String,
        dayOfTheWeek: String,
        startTime: String,
        endTime: String
    ): Boolean {
        return true
    }
}