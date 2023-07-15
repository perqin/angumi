package com.perqin.angumi.ui.collections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.tabs.TabLayoutMediator
import com.perqin.angumi.AppNavigationDirections
import com.perqin.angumi.R
import com.perqin.angumi.data.settings.isSignedIn
import com.perqin.angumi.databinding.CollectionsFragmentBinding
import com.perqin.angumi.utils.collectViewState
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class CollectionsFragment : Fragment() {
    private var _binding: CollectionsFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CollectionsViewModel by inject()
    private lateinit var collectionsPagerAdapter: CollectionsPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        collectionsPagerAdapter = CollectionsPagerAdapter(this)
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
        binding.collectionsViewPager.adapter = collectionsPagerAdapter
        binding.collectionsViewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                println("onPageSelected: $position")
                loadTabAt(position)
            }
        })
        loadTabAt(binding.collectionsViewPager.currentItem)
        TabLayoutMediator(binding.tabLayout, binding.collectionsViewPager) { tab, position ->
            tab.setText(collectionsPagerAdapter.pages[position].titleRes)
        }.attach()

        collectViewState(viewModel.session) {
            binding.signedInLayout.visibility = if (it.isSignedIn()) View.VISIBLE else View.GONE
            binding.notSignedInLayout.visibility = if (it.isSignedIn()) View.GONE else View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadTabAt(position: Int) {
        lifecycleScope.launch {
            try {
                // TODO: Should load the whole tab content
                viewModel.loadCollections(collectionsPagerAdapter.pages[position])
            } catch (e: Exception) {
                Toast.makeText(
                    requireContext(),
                    R.string.toast_failedToLoadData,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
