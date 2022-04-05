package com.example.myplans.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myplans.DB.HomeWork
import com.example.myplans.databinding.CardCellHomworkBinding

class HomeWorkAdapter(private var homeWorkList: List<HomeWork>) :
    RecyclerView.Adapter<HomeWorkAdapter.HolderItem>() {
    class HolderItem(val binding: CardCellHomworkBinding) : RecyclerView.ViewHolder(binding.root)

    private val limit = 2

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
            tvCourse.text = homeWork.fk_nameCourse
            tvTitle.text = homeWork.title
            tvDate.text = "${homeWork.day}-${homeWork.month}-${homeWork.year}"

        }
    }

    override fun getItemCount(): Int {
        return if (homeWorkList.size > limit)
            limit
        else
            homeWorkList.size
    }

    fun update(newList: List<HomeWork>) {
        homeWorkList = newList
        notifyDataSetChanged()
    }
}