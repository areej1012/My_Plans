package com.example.myplans.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myplans.DB.ClassStudent
import com.example.myplans.databinding.CardCellClassBinding

class ClassStudentAdapter(
    private var listClass: List<ClassStudent>,
    private var optionsMenuClickListener: OptionsMenuClickListener
) :
    RecyclerView.Adapter<ClassStudentAdapter.HolderItem>() {
    class HolderItem(val binding: CardCellClassBinding) : RecyclerView.ViewHolder(binding.root)

    private val limit = 3

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

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HolderItem, position: Int) {
        val classes = listClass[position]
        holder.binding.apply {
            tvCourse.text = classes.fk_nameCourse
            tvDate.text = daysInArb(classes.day)
            tvTime.text = "${classes.timeStart} - ${classes.timeEnd}"
        }
        // implement on clickListener and pass position of the item
        // rest we will handle in PlanFragment.kt
        holder.binding.textViewOptions.setOnClickListener {
            optionsMenuClickListener.onOptionsMenuClicked(position)
        }
    }

    override fun getItemCount(): Int {
        return if (listClass.size > limit)
            limit
        else
            listClass.size
    }

    fun update(newList: List<ClassStudent>) {
        listClass = newList
        notifyDataSetChanged()
    }

    private fun daysInArb(day: String): String {
        when (day) {
            "sunday" -> return "الأحد"
            "monday" -> return "الأثنين"
            "tuesday" -> return "الثلاثاء"
            "wednesday" -> return "الأربعاء"
            "thursday" -> return "الخميس"
            "friday" -> return "الجمعة"
            "saturday" -> return "السبت"
        }
        return day


    }
}

