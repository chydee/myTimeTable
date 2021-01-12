package com.chydee.mytimetable.utils

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class TimetableUtilTest {

    @Test
    fun `when every field is empty or invalid return false`() {
        val result = TimetableUtil.validateTimetableInput(
            "", -1, "", "", -1, "", "",
            "", "", ""
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `when timeTable for already exists return false`() {
        val result = TimetableUtil.validateTimetableInput(
            "Geology", 0, "CSC111", "Intro To Computing", 5, "Test tutor", "1000PK",
            "Mon", "11:00", "13:00"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `when timeTableFor is not empty and does not already exists return true`() {
        val result = TimetableUtil.validateTimetableInput(
            "Language", 0, "CSC111", "Intro To Computing", 5, "Test tutor", "1000PK",
            "Mon", "11:00", "13:00"
        )
        assertThat(result).isTrue()
    }

    @Test
    fun `when course title contains a number return false`() {
        val result = TimetableUtil.validateTimetableInput(
            "Language", 0, "CSC111", "Intro To Computing 101", 5, "Test tutor", "1000PK",
            "Mon", "11:00", "13:00"
        )
        assertThat(result).isFalse()
    }


    @Test
    fun `empty course code returns false`() {
        val result = TimetableUtil.validateTimetableInput(
            "school", 0, "", "Intro To Computing", 5, "Test tutor", "1000PK",
            "Mon", "11:00", "13:00"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `empty course title returns false`() {
        val result = TimetableUtil.validateTimetableInput(
            "school", 0, "CSC111", "", 6, "Test tutor", "1000PK",
            "Mon", "11:00", "13:00"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `invalid course label returns false`() {
        val result = TimetableUtil.validateTimetableInput(
            "school", 0, "CSC111", "Intro To Computing", -1, "Test tutor", "1000PK",
            "Mon", "11:00", "13:00"
        )
        assertThat(result).isFalse()
    }


    @Test
    fun `empty course tutor returns false`() {
        val result = TimetableUtil.validateTimetableInput(
            "school", 0, "CSC111", "Intro To Computing", -1, "", "1000PK",
            "Mon", "11:00", "13:00"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `empty place  returns false`() {
        val result = TimetableUtil.validateTimetableInput(
            "school", 0, "CSC111", "Intro To Computing", -1, "Test Tutor", "",
            "Mon", "11:00", "13:00"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `empty or invalid day of the week returns false`() {
        val result = TimetableUtil.validateTimetableInput(
            "school", 0, "CSC111", "Intro To Computing", 5, "Test tutor", "1000PK",
            "", "11:00", "13:00"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `empty start and end time returns false`() {
        val result = TimetableUtil.validateTimetableInput(
            "school", 0, "CSC111", "Intro To Computing", -1, "Test tutor", "1000PK",
            "Mon", "", ""
        )
        assertThat(result).isFalse()
    }


}