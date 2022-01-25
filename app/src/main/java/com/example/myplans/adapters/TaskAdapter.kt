package com.example.myplans.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myplans.DB.Task
import com.example.myplans.databinding.CardCellTaskBinding

class TaskAdapter(val listTask: List<Task>) : RecyclerView.Adapter<TaskAdapter.HolderItem>() {
    class HolderItem(val binding: CardCellTaskBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderItem {
        return HolderItem(
            CardCellTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HolderItem, position: Int) {
        val task = listTask[position]
        holder.binding.apply {
            tvTitle.text = task.titleTask
            tvDate.text = task.Date
        }
    }

    override fun getItemCount(): Int = 0
}