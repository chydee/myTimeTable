package com.chydee.mytimetable.ui.adapters

import com.chydee.mytimetable.data.models.Lesson

interface OnLessonClickListener {
    fun onItemClick(lesson: Lesson)
}