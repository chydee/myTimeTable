package com.chydee.mytimetable.ui.adapters

import com.chydee.mytimetable.data.models.Period

interface OnPeriodClickListener {
    fun onItemClick(period: Period)
}