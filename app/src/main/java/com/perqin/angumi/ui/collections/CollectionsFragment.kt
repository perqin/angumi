package com.perqin.angumi.ui.collections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.perqin.angumi.AppNavigationDirections
import com.perqin.angumi.R
import com.perqin.angumi.data.models.CollectionType
import com.perqin.angumi.data.settings.isSignedIn
import com.perqin.angumi.databinding.CollectionsFragmentBinding
import com.perqin.angumi.utils.ShouldNotReachException
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

        binding.signInButton.setOnClickListener {
            requireActivity().findNavController(R.id.nav_host_fragment_container_view)
                .navigate(AppNavigationDirections.globalAuthAction())
        }
        binding.collectionTypeGroup.viewTreeObserver.addOnGlobalLayoutListener {
            // Any layout events of the whole tree of this fragment will trigger this listener
            // On navigated away, the layout event is dispatched but not executed until this
            // fragment's view is destroyed.
            if (_binding == null) {
                return@addOnGlobalLayoutListener
            }
            val childrenMinWidth =
                binding.collectionTypeGroup.width / binding.collectionTypeGroup.childCount
            binding.collectionTypeGroup.children.forEach {
                if (it.minimumWidth != childrenMinWidth) {
                    it.minimumWidth = childrenMinWidth
                }
            }
        }
        binding.collectionTypeGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                val type = when (checkedId) {
                    R.id.wish_button -> CollectionType.WISH
                    R.id.collect_button -> CollectionType.COLLECT
                    R.id.do_button -> CollectionType.DO
                    R.id.on_hold_button -> CollectionType.ON_HOLD
                    R.id.dropped_button -> CollectionType.DROPPED
                    else -> throw ShouldNotReachException()
                }
                lifecycleScope.launch {
                    try {
                        viewModel.selectCollectionType(type)
                    } catch (e: Exception) {
                        println(e)
                    }
                }
            }
        }
        binding.collectionsRecyclerView.adapter = adapter
        binding.collectionsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        collectViewState(viewModel.session) {
            binding.signedInLayout.visibility = if (it.isSignedIn()) View.VISIBLE else View.GONE
            binding.notSignedInLayout.visibility = if (it.isSignedIn()) View.GONE else View.VISIBLE
        }
        collectViewState(viewModel.collections) {
            adapter.dataset = it.data
        }
        collectViewState(viewModel.collectionType) {
            binding.collectionTypeGroup.check(
                when (it) {
                    CollectionType.WISH -> R.id.wish_button
                    CollectionType.COLLECT -> R.id.collect_button
                    CollectionType.DO -> R.id.do_button
                    CollectionType.ON_HOLD -> R.id.on_hold_button
                    CollectionType.DROPPED -> R.id.dropped_button
                }
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
