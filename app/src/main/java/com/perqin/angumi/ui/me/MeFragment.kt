package com.perqin.angumi.ui.me

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.perqin.angumi.AppNavigationDirections
import com.perqin.angumi.R
import com.perqin.angumi.databinding.MeFragmentBinding
import com.perqin.angumi.utils.collectViewState
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MeFragment : Fragment() {
    private var _binding: MeFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            try {
                viewModel.reload()
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
        return MeFragmentBinding.inflate(inflater, container, false).apply {
            _binding = this
        }.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signInButton.setOnClickListener {
            requireActivity().findNavController(R.id.nav_host_fragment_container_view)
                .navigate(AppNavigationDirections.globalAuthAction())
        }
        binding.signOutButton.setOnClickListener {
            viewModel.signOut()
        }
        collectViewState(viewModel.user) {
            val signedIn = it != null
            binding.nicknameText.visibility = if (signedIn) View.VISIBLE else View.GONE
            binding.nicknameText.text = it?.nickname.orEmpty()
            binding.usernameText.visibility = if (signedIn) View.VISIBLE else View.GONE
            binding.usernameText.text = "@${it?.username.orEmpty()}"
            binding.signatureText.visibility = if (signedIn) View.VISIBLE else View.GONE
            binding.signatureText.text = it?.sign.orEmpty()
            binding.signOutButton.visibility = if (signedIn) View.VISIBLE else View.GONE
            binding.signInButton.visibility = if (signedIn) View.GONE else View.VISIBLE
            it?.avatar?.large?.let { url ->
                Glide.with(this@MeFragment).load(url).into(binding.avatarView)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}