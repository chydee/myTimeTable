package com.chydee.mytimetable.utils

object TimetableUtil {


    private val timeTableNames = arrayListOf(
        "Computer Science Department",
        "Life Science",
        "Physics",
        "Maths & Stats",
        "Geology"
    )

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
        courseTutor: String = "Unknown",
        place: String,
        dayOfTheWeek: String,
        startTime: String,
        endTime: String
    ): Boolean {
        if (timetableFor.isNotEmpty() && timetableFor in timeTableNames) {
            return false
        }
        if (timetableFor.isEmpty()) {
            return false
        }
        if (id <= -1 || courseCode.isEmpty()) {
            return false
        }
        if (courseTitle.isEmpty() || courseTitle.contains("-?\\d+(\\.\\d+)?".toRegex())) {
            return false
        }
        if (courseLabel <= 0) {
            return false
        }
        if (courseTutor.isEmpty() || dayOfTheWeek.isEmpty() || place.isEmpty()) {
            return false
        }
        if (startTime.isEmpty() || endTime.isEmpty()) {
            return false
        }
        return true
    }
}