package com.example.myplans.ui.planView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myplans.R

class PlanViewFragment : Fragment() {

    private lateinit var planviewViewModel: PlanviewViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        planviewViewModel =
            ViewModelProvider(this).get(PlanviewViewModel::class.java)
        val root = inflater.inflate(R.layout.setting_fragment, container, false)
        val textView: TextView = root.findViewById(R.id.text_setting)
        planviewViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}