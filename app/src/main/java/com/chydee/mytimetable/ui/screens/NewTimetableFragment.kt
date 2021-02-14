@file:Suppress("DEPRECATION")

package com.chydee.mytimetable.ui.screens

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.chydee.mytimetable.R
import com.chydee.mytimetable.data.models.Timetable
import com.chydee.mytimetable.databinding.FragmentNewTimetableBinding
import com.chydee.mytimetable.ui.adapters.DivLikeAdapter
import com.chydee.mytimetable.ui.adapters.LabelsAdapter
import com.chydee.mytimetable.ui.viewmodel.MainViewModel
import com.chydee.mytimetable.utils.*
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewTimetableFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private var binding: FragmentNewTimetableBinding by autoCleared()

    private lateinit var labelsAdapter: LabelsAdapter

    private var tableLabel: Int = R.color.primaryDarkColor

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNewTimetableBinding.inflate(inflater)
        /**
         *  Inset the top view-group item so it doesn't go all the way up
         */
        ViewCompat.setOnApplyWindowInsetsListener(
                binding.root.findViewById(
                        R.id.newTableScreen
                )
        ) { _, insets ->
            binding.root.findViewById<MaterialButton>(R.id.btnUp)
                    .setMarginTop(insets.systemWindowInsetTop)
            insets.consumeSystemWindowInsets()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleOnClickEvents()
        setupTagList()
    }

    override fun onResume() {
        super.onResume()
        activity?.window?.statusBarColor = Color.TRANSPARENT
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.window?.statusBarColor = Color.WHITE
    }

    /**
     *  Listen and Handle click events
     */
    private fun handleOnClickEvents() {
        binding.btnContinue.setOnClickListener {
            it.isEnabled = false
            validateAndContinue()
        }
        binding.btnUp.setOnClickListener { findNavController().popBackStack() }
    }

    /**
     *  Validate by verify that the name field is not empty and then continue
     */
    private fun validateAndContinue() {
        val name = binding.timetableNameInput.takeText()
        val tag = binding.selectedTimetableTagText.text.toString()
        if (name.isEmpty()) {
            binding.timetableNameTextInputLayout.error = "Timetable name cannot be empty"
        } else {
            saveTimetableName(name, tag)
        }
    }

    /**
     *  Save Timetable to database
     */
    private fun saveTimetableName(tableName: String, tag: String) {
        val timetable = Timetable(tableName = tableName, tableTag = tag, timetableLabel = tableLabel)
        viewModel.saveTimetableInfo(timetable)
        observePropertyTimetable()
    }

    private fun observePropertyTimetable() {
        viewModel.timetable.observe(viewLifecycleOwner, {
            if (it != null) {
                findNavController().navigate(NewTimetableFragmentDirections.actionNewTimetableFragmentToNewLessonFragment())
            } else {
                binding.root.showSnackBar("Error creating Timetable")
            }
        })
    }

    /**
     * set up timetable tags list
     */
    private fun setupTagList() {
        val recyclerView = binding.timetableTagsRecyclerView
        val layoutManager = FlexboxLayoutManager(requireContext())
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.CENTER
        recyclerView.layoutManager = layoutManager

        val divLikeContent = ArrayList<String>()
        divLikeContent.add("Exam")
        divLikeContent.add("Test")
        divLikeContent.add("Practical")
        divLikeContent.add("Sanitation")
        divLikeContent.add("Class")

        val adapter = DivLikeAdapter(divLikeContent)

        recyclerView.adapter = adapter

        adapter.setOnLabelClickListener(object : DivLikeAdapter.OnTagClickListener {
            override fun onTagClicked(text: String) {
                binding.selectedTimetableTagText.text = text
            }
        })

        loadAndSetupLabelList()
    }

    private fun loadAndSetupLabelList() {
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.labelsRecyclerView.layoutManager = layoutManager

        labelsAdapter = LabelsAdapter()

        with(binding) {
            labelsRecyclerView.adapter = labelsAdapter
            labelsAdapter.submitList(colors)
            labelsRecyclerView.setHasFixedSize(true)
            labelsRecyclerView.isNestedScrollingEnabled = false
        }

        labelsAdapter.setOnLabelClickListener(object : LabelsAdapter.OnItemClickListener {
            override fun onLabelClicked(color: com.chydee.mytimetable.data.models.Color) {
                tableLabel = color.colorRes
            }
        })

    }

}