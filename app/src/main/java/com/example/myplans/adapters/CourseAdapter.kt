package com.example.myplans.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myplans.DB.Course
import com.example.myplans.databinding.CardCellCourseBinding

class CourseAdapter(private val listCourse: List<Course>) :
    RecyclerView.Adapter<CourseAdapter.HolderItem>() {
    class HolderItem(val binding: CardCellCourseBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderItem {
        return HolderItem(
            CardCellCourseBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HolderItem, position: Int) {
        val course = listCourse[position]
        holder.binding.apply {
            tvTitle.text = course.name
            tvDate.text = course.days
            tvTime.text = course.time
            Log.e("title", course.name)
        }
    }

    override fun getItemCount(): Int = 3
}