package com.perqin.angumi.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.perqin.angumi.R
import com.perqin.angumi.data.auth.OAuthState
import com.perqin.angumi.databinding.FragmentSignInBinding
import com.perqin.angumi.utils.collectViewState
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignInFragment : Fragment() {
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SignInViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.notNewToSignIn()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentSignInBinding.inflate(inflater, container, false).apply {
            _binding = this
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.authorizeButton.setOnClickListener {
            startActivity(Intent(requireActivity(), OAuthInitiatorActivity::class.java))
        }
        binding.notNowButton.setOnClickListener {
            findNavController().popBackStack()
        }

        collectViewState(viewModel.oAuthState) {
            val isTerminateState = it == OAuthState.SUCCESSFUL || it == OAuthState.FAILED
            val isAuthorizing = it == OAuthState.INITIATED || it == OAuthState.HANDLING_CALLBACK
            binding.authorizeButton.isEnabled = !isAuthorizing
            binding.authorizeButton.setText(if (isAuthorizing) R.string.title_authorizing else R.string.title_authorize)
            binding.notNowButton.isEnabled = !isAuthorizing
            if (isTerminateState) {
                viewModel.resetState()
                if (it == OAuthState.SUCCESSFUL) {
                    onAuthSucceeded()
                } else {
                    onAuthFailed()
                }
            }
        }
    }

    private fun onAuthSucceeded() {
        Toast.makeText(requireContext(), R.string.toast_authorizationSucceeded, Toast.LENGTH_SHORT)
            .show()
        findNavController().popBackStack()
    }

    private fun onAuthFailed() {
        Toast.makeText(requireContext(), R.string.toast_authorizationFailed, Toast.LENGTH_SHORT)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}