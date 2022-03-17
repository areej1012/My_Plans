package com.example.myplans.ui.plan

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myplans.DB.*
import com.example.myplans.R
import com.example.myplans.adapters.*
import com.example.myplans.databinding.FragmentPlanBinding

class PlanFragment : Fragment() {

    private lateinit var binding: FragmentPlanBinding
    private lateinit var adapterClassStudent: ClassStudentAdapter
    private lateinit var meetingAdapter: MeetingAdapter
    private val viewModel by lazy { ViewModelProvider(this)[PlanViewModel::class.java] }
    private val meetingList = listOf<Meeting>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentPlanBinding.inflate(inflater, container, false)
        val root = binding.root

        meetingAdapter = MeetingAdapter(meetingList)
        binding.rvMeeting.adapter = meetingAdapter
        binding.rvMeeting.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, true)



        setUpClass()
        setUpHomeWork()
        setUpQuiz()
        setUpTask()
        return root
    }

    override fun onStart() {
        super.onStart()
        setUpMeeting()
    }

    private fun setUpMeeting() {

        activity?.let {
            viewModel.getMeetings().observe(it, { meeting ->
                Log.e("meeting size", meeting.size.toString())
                if (meeting.isNotEmpty()) {
                    binding.meeting.visibility = View.VISIBLE
                    binding.rvMeeting.visibility = View.VISIBLE
                    meetingAdapter.update(meeting)
                }
            }
            )
        }

    }

    private fun setUpClass() {
        val classes = listOf<ClassStudent>(
            ClassStudent("Math", "cc", true, "sunday", "12:00", "14:00", "Math"),
            ClassStudent("Math", "Mth", true, "monday", "1:00", "4:00", "Program"),
            ClassStudent("Math", "Psh", true, "Sunday", "10:00 ", "11:00", "Data structures"),
            ClassStudent("M", "Gam", true, "sunday", "12:00", "14:00", "NetWork")
        )
        adapterClassStudent =
            ClassStudentAdapter(classes, object : ClassStudentAdapter.OptionsMenuClickListener {
                override fun onOptionsMenuClicked(position: Int) {
                    performOptionsMenuClick(position, classes)
                }

            })
        binding.rvCourse.adapter = adapterClassStudent
        binding.rvCourse.layoutManager = LinearLayoutManager(activity)
    }

    private fun setUpHomeWork() {
        val homeWork = listOf<HomeWork>(HomeWork(5, "Project", "uh", true, "15 Oct", 9))
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
            Task(2, "eso", "83w2", false, false, "12 Oct", "")
        )
        binding.rvTask.adapter = TaskAdapter(task)
        binding.rvTask.layoutManager = LinearLayoutManager(activity)
    }

    // this method will handle the onclick options click
    private fun performOptionsMenuClick(position: Int, course: List<ClassStudent>) {
        // create object of PopupMenu and pass context and view where we want
        // to show the popup menu
        val popupMenu =
            PopupMenu(activity, binding.rvCourse[position].findViewById(R.id.textViewOptions))
        // add the menu
        popupMenu.inflate(R.menu.course_menu)

        popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                when (item?.itemId) {
                    R.id.cancel_course_only -> {
                        // here are the logic to delete an item from the list
                        val tempLang = course[position]
                        adapterClassStudent.notifyDataSetChanged()
                        return true
                    }
                }
                return false
            }
        })
        popupMenu.show()
    }
}