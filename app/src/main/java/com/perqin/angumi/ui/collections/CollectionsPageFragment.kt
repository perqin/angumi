package com.perqin.angumi.ui.collections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.perqin.angumi.data.models.SubjectType
import com.perqin.angumi.databinding.CollectionsPageFragmentBinding
import com.perqin.angumi.utils.collectViewState
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class CollectionsPageFragment : Fragment() {
    companion object {
        const val ARG_SUBJECT_TYPE = "subjectType"

        fun newInstance(subjectType: SubjectType) = CollectionsPageFragment().apply {
            arguments = bundleOf(
                ARG_SUBJECT_TYPE to subjectType.ordinal
            )
        }
    }

    private var _binding: CollectionsPageFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CollectionsPageViewModel by inject {
        val subjectType = SubjectType.values()[requireArguments().getInt(ARG_SUBJECT_TYPE)]
        parametersOf(subjectType)
    }

    private val adapter = CollectionsAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return CollectionsPageFragmentBinding.inflate(inflater, container, false).apply {
            _binding = this
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        collectViewState(viewModel.collectionType) {
            binding.collectionTypeChip.setText(it.titleRes)
        }
        collectViewState(viewModel.collectionList) {
            adapter.dataset = it.data
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
