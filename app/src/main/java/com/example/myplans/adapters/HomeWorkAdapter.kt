package com.example.myplans.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myplans.DB.HomeWork
import com.example.myplans.databinding.CardCellHomworkBinding

class HomeWorkAdapter(val homeWorkList: List<HomeWork>) :
    RecyclerView.Adapter<HomeWorkAdapter.HolderItem>() {
    class HolderItem(val binding: CardCellHomworkBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderItem {
        return HolderItem(
            CardCellHomworkBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HolderItem, position: Int) {
        val homeWork = homeWorkList[position]
        holder.binding.apply {
            tvCourse.text = "course"
            tvDate.text = homeWork.date
            tvTitle.text = homeWork.title
        }
    }

    override fun getItemCount(): Int = 0
}