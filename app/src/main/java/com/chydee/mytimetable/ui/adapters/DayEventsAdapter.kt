package com.chydee.mytimetable.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chydee.mytimetable.data.models.Period
import com.chydee.mytimetable.databinding.ItemDayEventsBinding

class DayEventsAdapter : RecyclerView.Adapter<DayEventsViewHolder>() {

    private lateinit var listener: OnPeriodClickListener


    private val diffCallback = object : DiffUtil.ItemCallback<Period>() {
        override fun areItemsTheSame(oldItem: Period, newItem: Period): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Period, newItem: Period): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<Period>) = differ.submitList(list)

    fun setOnClickListener(listener: OnPeriodClickListener) {
        this.listener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayEventsViewHolder =
        DayEventsViewHolder(ItemDayEventsBinding.inflate(LayoutInflater.from(parent.context)))


    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: DayEventsViewHolder, position: Int) {

        val item = differ.currentList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            listener.onItemClick(period = item)
        }

    }
}
