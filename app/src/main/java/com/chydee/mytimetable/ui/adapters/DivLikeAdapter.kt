package com.chydee.mytimetable.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chydee.mytimetable.R

class DivLikeAdapter(private val mItems: ArrayList<String>) :
    RecyclerView.Adapter<DivLikeAdapter.DivItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DivLikeAdapter.DivItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_timetable_tag, parent, false)
        return DivItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: DivItemViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    inner class DivItemViewHolder constructor(itemView: View) : RecyclerView.ViewHolder
        (itemView) {
        private val text: TextView = itemView.findViewById(R.id.timetableTagText)

        fun onBind(position: Int) {
            text.text = mItems[position]
        }
    }
}