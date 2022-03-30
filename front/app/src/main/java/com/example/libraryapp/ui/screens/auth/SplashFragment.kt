package com.example.libraryapp.ui.screens.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.libraryapp.databinding.FragmentSplashBinding
import com.example.libraryapp.ui.common.BaseViewBindingFragment
import com.example.libraryapp.ui.common.SessionViewModel
import kotlinx.android.synthetic.main.fragment_splash.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : BaseViewBindingFragment<FragmentSplashBinding>() {
    private val sessionViewModel: SessionViewModel by viewModel()

    override fun inflateViewBinding(): FragmentSplashBinding =
        FragmentSplashBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionViewModel.getUser()
        sessionViewModel.error.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
        })

        sessionViewModel.sessionUser.observe(viewLifecycleOwner, Observer {
            if (it != null)

                router.navigate(SplashFragmentDirections.actionSplashFragmentToCatalogFragment())
        })

        btn_login_account.setOnClickListener {
            router.navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
        }

        btn_create_admin.setOnClickListener {
            router.navigate(SplashFragmentDirections.actionSplashFragmentToSignupFragment())
        }
    }

}
