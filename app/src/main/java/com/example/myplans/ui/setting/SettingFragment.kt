package com.example.myplans.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myplans.R

class SettingFragment : Fragment() {


    private lateinit var viewModel: SettingViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProvider(this).get(SettingViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_plan_view, container, false)
        val textView: TextView = root.findViewById(R.id.text_slideshow)
        viewModel.text.observe(viewLifecycleOwner, {
            textView.text = it
        })
        return root
    }
}