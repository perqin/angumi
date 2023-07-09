package com.perqin.angumi.ui.collections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.perqin.angumi.R
import com.perqin.angumi.databinding.CollectionsFragmentBinding
import com.perqin.angumi.utils.collectViewState
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class CollectionsFragment : Fragment() {
    private var _binding: CollectionsFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CollectionsViewModel by inject()
    private val adapter = CollectionsAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            try {
                viewModel.loadCollections()
            } catch (e: Exception) {
                Toast.makeText(
                    requireContext(),
                    R.string.toast_failedToLoadData,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return CollectionsFragmentBinding.inflate(inflater, container, false).apply {
            _binding = this
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.collectionsRecyclerView.adapter = adapter
        binding.collectionsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        collectViewState(viewModel.collections) {
            adapter.dataset = it.data
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
