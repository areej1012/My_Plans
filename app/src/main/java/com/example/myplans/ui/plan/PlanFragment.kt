package com.example.myplans.ui.plan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myplans.DB.*
import com.example.myplans.adapters.*
import com.example.myplans.databinding.FragmentPlanBinding

class PlanFragment : Fragment() {

    private lateinit var planViewModel: PlanViewModel
    private lateinit var binding: FragmentPlanBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        planViewModel =
            ViewModelProvider(this).get(PlanViewModel::class.java)
        binding = FragmentPlanBinding.inflate(inflater, container, false)
        val root = binding.root

        setUpMeeting()
        setUpCourse()
        setUpHomeWork()
        setUpQuiz()
        setUpTask()
        return root
    }

    private fun setUpMeeting() {
        val meeting = listOf<Meeting>(
            Meeting(2, "Meeting", "oct", "11:00 AM", "A102"),
            Meeting(2, "Friends", "sept", "10:00 AM", "B102"),
            Meeting(2, "Family", "Nov", "11:00 AM", "A102")
        )

        binding.rvMeeting.adapter = MeetingAdapter(meeting)
        binding.rvMeeting.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, true)
    }

    private fun setUpCourse() {
        val course = listOf<Course>(
            Course(2, "How Learn", true, "sunday", "12:00 - 14:00", 8),
            Course(2, "How monday", true, "monday", "1:00 - 4:00", 8),
            Course(2, "How ", true, "fradiy", "10:00 - 11:00", 8),
            Course(2, "How Learn", true, "sunday", "12:00 - 14:00", 8)
        )
        binding.rvCourse.adapter = CourseAdapter(course)
        binding.rvCourse.layoutManager = LinearLayoutManager(activity)
    }

    private fun setUpHomeWork() {
        val homeWork = listOf<HomeWork>(HomeWork(5, "876", "uh", true, "15 Oct", 9))
        binding.rvHW.adapter = HomeWorkAdapter(homeWork)
        binding.rvHW.layoutManager = LinearLayoutManager(activity)
    }

    private fun setUpQuiz() {
        val quiz = listOf<Quiz>(Quiz(2, "Quiz", "12 Oct", "12:00 PM", true, 10, "", 1))
        binding.rvQuiz.adapter = QuizAdapter(quiz)
        binding.rvQuiz.layoutManager = LinearLayoutManager(activity)
    }

    private fun setUpTask() {
        val task = listOf<Task>(
            Task(
                2, "eso", "83w2",
                isReminder = false,
                isCompleted = false,
                Date = "12 Oct",
                fk = 1
            )
        )
        binding.rvTask.adapter = TaskAdapter(task)
        binding.rvTask.layoutManager = LinearLayoutManager(activity)
    }
}