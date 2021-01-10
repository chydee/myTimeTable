package com.chydee.mytimetable.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import com.chydee.mytimetable.data.models.Period
import com.chydee.mytimetable.databinding.ItemDayEventsBinding

class DayEventsViewHolder(private var binding: ItemDayEventsBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(period: Period) {
        binding.period = period
        binding.executePendingBindings()
    }
}