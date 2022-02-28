package com.example.myplans.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myplans.DB.Course
import com.example.myplans.databinding.CardCellAddCourseBinding

class AlertAddAdapter(val arrayList: ArrayList<Course>, val context: Context) :
    RecyclerView.Adapter<AlertAddAdapter.ItemHolder>() {
    class ItemHolder(val binding: CardCellAddCourseBinding) : RecyclerView.ViewHolder(binding.root)

    val name: TextView = TextView(context)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            CardCellAddCourseBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.binding.apply {
            tvCourse.text = arrayList[position].nameCourse
        }
        holder.binding.tvCourse.setOnClickListener {
            Toast.makeText(context, arrayList[position].nameCourse, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = arrayList.size
}