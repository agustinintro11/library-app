package com.example.libraryapp.ui.screens.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.libraryapp.MainActivity
import com.example.libraryapp.databinding.FragmentLoginBinding
import com.example.libraryapp.ui.common.BaseViewBindingFragment
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseViewBindingFragment<FragmentLoginBinding>() {
    private val viewModel: LoginViewModel by viewModel()
    private val args: LoginFragmentArgs by navArgs()

    override fun inflateViewBinding(): FragmentLoginBinding =
        FragmentLoginBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!args.email.isNullOrBlank()) {
            ti_email.setText(args.email)
            ti_organization.setText(args.orgName)
        }
        setObservers()
        setOnFocusChangeListeners()

        login_toolbar.setNavigationOnClickListener {
            router.popBackStack()
        }

        viewModel.createdSession.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                (activity as MainActivity).updateHeaderUser(it.user.name)
                (activity as MainActivity).updateHeaderOrg(ti_organization.text.toString())
                (activity as MainActivity).updateMenuByUserType(it.user.isAdmin)

                router.navigate(LoginFragmentDirections.actionLoginFragmentToCatalogFragment())
            }
        })

        btn_login.setOnClickListener {

            viewModel.buttonClick(
                ti_email.text.toString(),
                ti_password.text.toString(),
                ti_organization.text.toString()
            )
        }
    }

    private fun setObservers() {
        viewModel.error.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
        })

        viewModel.errorPassword.observe(viewLifecycleOwner, Observer {
            tf_password.error = it
            tf_password.isErrorEnabled = true
        })

        viewModel.errorEmail.observe(viewLifecycleOwner, Observer {
            tf_email.error = it
            tf_email.isErrorEnabled = true
        })

        viewModel.errorOrganization.observe(viewLifecycleOwner, Observer {
            tf_organization.error = it
            tf_organization.isErrorEnabled = true
        })
    }

    private fun setOnFocusChangeListeners() {

        ti_password.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                tf_password.isErrorEnabled = false
            }
        }

        ti_email.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) tf_email.isErrorEnabled = false
        }

        ti_organization.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) tf_organization.isErrorEnabled = false
        }
    }
}
