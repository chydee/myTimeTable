package com.chydee.mytimetable.ui.adapters

import com.chydee.mytimetable.data.models.Lesson

interface OnPeriodClickListener {
    fun onItemClick(lesson: Lesson)
}