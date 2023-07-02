package com.perqin.angumi.ui.collections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.perqin.angumi.AppNavigationDirections
import com.perqin.angumi.R
import com.perqin.angumi.data.settings.isSignedIn
import com.perqin.angumi.databinding.CollectionsFragmentBinding
import com.perqin.angumi.utils.collectViewState
import org.koin.android.ext.android.inject

class CollectionsFragment : Fragment() {
    private var _binding: CollectionsFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CollectionsViewModel by inject()

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

        collectViewState(viewModel.session) { session ->
            binding.debugSignInButton.setText(if (session.isSignedIn()) R.string.title_sign_out else R.string.title_sign_in)
            binding.debugSignInButton.setOnClickListener {
                if (session.isSignedIn()) {
                    viewModel.signOut()
                } else {
                    requireActivity().findNavController(R.id.nav_host_fragment_container_view)
                        .navigate(AppNavigationDirections.globalAuthAction())
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
