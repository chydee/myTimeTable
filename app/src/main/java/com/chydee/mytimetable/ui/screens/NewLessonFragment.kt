package com.chydee.mytimetable.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.chydee.mytimetable.R
import com.chydee.mytimetable.data.models.Color
import com.chydee.mytimetable.databinding.FragmentNewLessonBinding
import com.chydee.mytimetable.ui.adapters.LabelsAdapter
import com.chydee.mytimetable.ui.viewmodel.MainViewModel
import com.chydee.mytimetable.utils.autoCleared
import com.chydee.mytimetable.utils.colors
import com.chydee.mytimetable.utils.makeStatusBarTransparent
import com.chydee.mytimetable.utils.setMarginTop
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NewLessonFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()
    private var binding: FragmentNewLessonBinding by autoCleared()

    private lateinit var labelsAdapter: LabelsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNewLessonBinding.inflate(inflater)
        insetView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
    }

    private fun loadAndSetupLabelList() {
        val layoutManager = LinearLayoutManager(requireContext())

        binding.labelsRecyclerView.layoutManager = layoutManager

        labelsAdapter = LabelsAdapter()

        with(binding) {
            labelsRecyclerView.adapter = labelsAdapter
            labelsAdapter.submitList(colors)
            labelsRecyclerView.setHasFixedSize(true)
            labelsRecyclerView.isNestedScrollingEnabled = false
        }

        labelsAdapter.setOnLabelClickListener(object : LabelsAdapter.OnItemClickListener {
            override fun onLabelClicked(color: Color) {
                TODO("Not yet implemented")
            }
        })

    }

    private fun insetView() {
        requireActivity().makeStatusBarTransparent()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root.findViewById(R.id.content_container)) { _, insets ->
            binding.root.findViewById<MaterialButton>(R.id.btnUp)
                .setMarginTop(insets.systemWindowInsetTop)
            insets.consumeSystemWindowInsets()
        }
    }


}