package com.example.myplans.ui.plan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myplans.R

class PlanFragment : Fragment() {

    private lateinit var planViewModel: PlanViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        planViewModel =
                ViewModelProvider(this).get(PlanViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_plan, container, false)

        planViewModel.text.observe(viewLifecycleOwner, Observer {

        })
        return root
    }
}