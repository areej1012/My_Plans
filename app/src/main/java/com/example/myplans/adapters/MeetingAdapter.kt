package com.example.myplans.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myplans.DB.Meeting
import com.example.myplans.R
import com.example.myplans.databinding.CardCellMeetingBinding

class MeetingAdapter(val meetingList: List<Meeting>) :
    RecyclerView.Adapter<MeetingAdapter.HolderItem>() {
    class HolderItem(val binding: CardCellMeetingBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderItem {
        return HolderItem(
            CardCellMeetingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HolderItem, position: Int) {
        val meeting = meetingList[position]
        holder.binding.apply {
            if (position % 2 == 1) {
                liner.background =
                    holder.itemView.resources.getDrawable(R.drawable.meeting_pink, null)
                img.setImageResource(R.drawable.date_pink)

            }

            tvTitle.text = meeting.title
            tvTime.text = meeting.time
            tvLocation.text = meeting.location
            tvDay.text = "15"
            tvMonth.text = meeting.date
        }
    }

    override fun getItemCount(): Int = meetingList.size
}