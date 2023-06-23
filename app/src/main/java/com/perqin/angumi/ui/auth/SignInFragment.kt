package com.perqin.angumi.ui.auth

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.perqin.angumi.BuildConfig
import com.perqin.angumi.databinding.FragmentSignInBinding
import org.koin.android.ext.android.inject

class SignInFragment : Fragment() {
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SignInViewModel by inject()

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
            val uri = Uri.parse("https://bgm.tv/oauth/authorize")
                .buildUpon()
                .appendQueryParameter("client_id", BuildConfig.BANGUMI_CLIENT_ID)
                .appendQueryParameter("response_type", "code")
                // TODO: Setup redirect_uri
                .build()
            CustomTabsIntent.Builder().build().launchUrl(requireActivity(), uri)
        }
        binding.notNowButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}