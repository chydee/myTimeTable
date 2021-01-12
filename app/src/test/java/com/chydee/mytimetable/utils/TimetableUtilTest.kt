package com.chydee.mytimetable.utils

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class TimetableUtilTest {

    @Test
    fun `empty course code returns false`() {
        val result = TimetableUtil.validateTimetableInput(
            "school", 0, "", "5", 5, "test tutor", "1000PK",
            "Mon", "11:00", "13:00"
        )
        assertThat(result).isFalse()
    }
}