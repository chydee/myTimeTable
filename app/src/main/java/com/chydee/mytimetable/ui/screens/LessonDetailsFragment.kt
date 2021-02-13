package com.chydee.mytimetable.ui.screens

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.chydee.mytimetable.databinding.FragmentLessonDetailsBinding
import com.chydee.mytimetable.ui.viewmodel.MainViewModel
import com.chydee.mytimetable.utils.autoCleared
import com.chydee.mytimetable.utils.removeNavIcon


class LessonDetailsFragment : Fragment() {

    private var binding: FragmentLessonDetailsBinding by autoCleared()

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLessonDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.model = viewModel
        removeNavIcon()
    }

    override fun onResume() {
        super.onResume()
        activity?.window?.statusBarColor = Color.TRANSPARENT
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.window?.statusBarColor = Color.WHITE
    }

}