package com.example.myplans.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myplans.DB.Quiz
import com.example.myplans.databinding.CardCellQuizBinding

class QuizAdapter(val listQuiz: List<Quiz>) : RecyclerView.Adapter<QuizAdapter.HolderItem>() {
    class HolderItem(val binding: CardCellQuizBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderItem {
        return HolderItem(
            CardCellQuizBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HolderItem, position: Int) {
        val quiz = listQuiz[position]
        holder.binding.apply {
            tvCourse.text = "Course"
            tvDate.text = quiz.date
            tvTime.text = quiz.timeQuiz
            tvTitle.text = quiz.title
        }
    }

    override fun getItemCount(): Int = listQuiz.size
}