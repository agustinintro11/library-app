package com.example.libraryapp.ui.screens.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.libraryapp.databinding.FragmentSignupBinding
import com.example.libraryapp.ui.common.BaseViewBindingFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_signup.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignupFragment : BaseViewBindingFragment<FragmentSignupBinding>() {

    override fun inflateViewBinding(): FragmentSignupBinding =
        FragmentSignupBinding.inflate(layoutInflater)

    private val viewModel: SignupViewModel by viewModel()
    private val args: SignupFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservers()
        setOnFocusChangeListeners()

        val orgNameDeeplink = args.orgName
        val emailDeeplink = args.email
        if (!orgNameDeeplink.isNullOrBlank() && !emailDeeplink.isNullOrBlank()) {
            ti_org_name.setText(orgNameDeeplink)
            ti_email.setText(emailDeeplink)

            tf_org_name.isEnabled = false
            tf_email.isEnabled = false
            btn_signup.text = "Registrarse a la organización"
        }

        signup_toolbar.setNavigationOnClickListener {
            router.popBackStack()
        }

        viewModel.createdAdminAndOrg.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Organización Creada")
                    .setMessage(
                        "La organización ${it.organization.name} ha sido creada correctamente y tu eres el " +
                                "administrador, para iniciar sesión con los datos ingresados presiona OK"
                    )
                    .setPositiveButton("OK") { _, _ ->
                        router.navigate(
                            SignupFragmentDirections.actionSignupFragmentToLoginFragment(
                                it.organization.name,
                                ti_email.text.toString()
                            )
                        )
                    }
                    .show()
            }
        })


        btn_signup.setOnClickListener {

            viewModel.buttonClick(
                ti_email.text.toString(),
                ti_password.text.toString(),
                ti_org_name.text.toString(),
                ti_name.text.toString(),
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
            tf_org_name.error = it
            tf_org_name.isErrorEnabled = true
        })

        viewModel.errorOrganization.observe(viewLifecycleOwner, Observer {
            tf_name.error = it
            tf_name.isErrorEnabled = true
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

        tf_org_name.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) tf_org_name.isErrorEnabled = false
        }

        tf_name.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) tf_name.isErrorEnabled = false
        }
    }

}
