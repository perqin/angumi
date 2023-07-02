package com.perqin.angumi.ui.collections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.perqin.angumi.AppNavigationDirections
import com.perqin.angumi.R
import com.perqin.angumi.databinding.CollectionsFragmentBinding

class CollectionsFragment : Fragment() {
    private var _binding: CollectionsFragmentBinding? = null
    private val binding get() = _binding!!

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
        binding.debugSignInButton.setOnClickListener {
            requireActivity().findNavController(R.id.nav_host_fragment_container_view)
                .navigate(AppNavigationDirections.globalAuthAction())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
