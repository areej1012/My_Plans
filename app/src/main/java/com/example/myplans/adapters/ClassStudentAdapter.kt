package com.example.myplans.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myplans.DB.ClassStudent
import com.example.myplans.databinding.CardCellClassBinding

class ClassStudentAdapter(
    private val listCourse: List<ClassStudent>,
    var optionsMenuClickListener: OptionsMenuClickListener
) :
    RecyclerView.Adapter<ClassStudentAdapter.HolderItem>() {
    class HolderItem(val binding: CardCellClassBinding) : RecyclerView.ViewHolder(binding.root)

    // so that we can handle data most effectively in PlanFragment.kt
    interface OptionsMenuClickListener {
        fun onOptionsMenuClicked(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderItem {
        return HolderItem(
            CardCellClassBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HolderItem, position: Int) {
        val classes = listCourse[position]
        holder.binding.apply {
            tvCourse.text = classes.className
            tvDate.text = classes.day
            tvTime.text = "${classes.timeStart} - ${classes.timeEnd}"
        }
        // implement on clickListener and pass position of the item
        // rest we will handle in PlanFragment.kt
        holder.binding.textViewOptions.setOnClickListener {
            optionsMenuClickListener.onOptionsMenuClicked(position)
        }
    }

    override fun getItemCount(): Int = 3
}

