package com.example.libraryapp.ui.screens.inviteUser

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.libraryapp.MainActivity
import com.example.libraryapp.R
import com.example.libraryapp.databinding.FragmentInviteUserBinding
import com.example.libraryapp.ui.common.BaseViewBindingFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_apikey_management.*
import kotlinx.android.synthetic.main.fragment_invite_user.*
import kotlinx.android.synthetic.main.fragment_signup.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class InviteUserFragment : BaseViewBindingFragment<FragmentInviteUserBinding>() {

    private val viewModel: InviteUserViewModel by viewModel()

    override fun inflateViewBinding(): FragmentInviteUserBinding =
        FragmentInviteUserBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservers()
        setOnFocusChangeListeners()

        btn_send_invitation.setOnClickListener {

            viewModel.buttonClick(
                ti_email_invite.text.toString(),
                chip_admin.isChecked
            )
        }
    }

    private fun setObservers() {
        viewModel.error.observe(viewLifecycleOwner, {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
        })

        viewModel.invitationSent.observe(viewLifecycleOwner, {
            (activity as MainActivity).showSnackbar(
                SpannableStringBuilder("Se envió la invitación correctamente"), true)
        })

        viewModel.errorEmail.observe(viewLifecycleOwner, {
            tf_email_invite.error = it
            tf_email_invite.isErrorEnabled = true
        })

    }

    private fun setOnFocusChangeListeners() {

        ti_email_invite.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) tf_email_invite.isErrorEnabled = false
        }

    }
}
