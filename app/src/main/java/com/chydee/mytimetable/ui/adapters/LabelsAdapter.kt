package com.chydee.mytimetable.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chydee.mytimetable.databinding.ItemLabelsBinding

class LabelsAdapter : RecyclerView.Adapter<LabelsAdapter.MyViewHolder>() {

    interface OnItemClickListener {
        fun onLabelClicked(color: Color)
    }

    private lateinit var listener: OnItemClickListener

    private val diffCallback = object : DiffUtil.ItemCallback<Color>() {
        override fun areItemsTheSame(oldItem: Color, newItem: Color): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Color, newItem: Color): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<Color>) = differ.submitList(list)

    internal fun setOnLabelClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }


    inner class MyViewHolder(private val binding: ItemLabelsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(color: Color) {
            with(binding) {
                /*colorCard.setOnClickListener {
                    listener.onColorSelected(color)
                }
                colorCard.setCardBackgroundColor(color.colorRes)*/
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return from(parent)
    }

    private fun from(parent: ViewGroup): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemLabelsBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val color = differ.currentList[position]
        holder.bind(color)
    }


    override fun getItemCount(): Int = differ.currentList.size


}