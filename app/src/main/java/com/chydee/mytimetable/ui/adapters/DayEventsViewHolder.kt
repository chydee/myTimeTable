package com.chydee.mytimetable.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import com.chydee.mytimetable.data.models.Lesson
import com.chydee.mytimetable.databinding.ItemDayEventsBinding

class DayEventsViewHolder(private var binding: ItemDayEventsBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(lesson: Lesson) {
        binding.lesson = lesson
        binding.courseIllustration.setImageResource(lesson.illusID)
        binding.executePendingBindings()
    }
}