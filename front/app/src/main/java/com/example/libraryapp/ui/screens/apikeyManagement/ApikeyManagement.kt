package com.example.libraryapp.ui.screens.apikeyManagement

import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.libraryapp.MainActivity
import com.example.libraryapp.databinding.FragmentApikeyManagementBinding
import com.example.libraryapp.ui.common.BaseViewBindingFragment
import kotlinx.android.synthetic.main.fragment_apikey_management.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ApikeyManagement : BaseViewBindingFragment<FragmentApikeyManagementBinding>() {

    override fun inflateViewBinding(): FragmentApikeyManagementBinding =
        FragmentApikeyManagementBinding.inflate(layoutInflater)

    private val viewModel: ApikeyManagementViewModel by viewModel()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCurrentApiKey()

        viewModel.currentApiKey.observe(viewLifecycleOwner, {
            ti_apikey.setText(it)
        })

        viewModel.error.observe(viewLifecycleOwner, {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
        })

        tf_apikey.setEndIconOnClickListener {
            val clipboardManager =
                (activity as MainActivity).getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("text", ti_apikey.text)
            clipboardManager.setPrimaryClip(clipData)
            Toast.makeText(requireContext(), "Se copió el texto al portapapeles", Toast.LENGTH_LONG)
                .show()
        }

        btn_refresh_apikey.setOnClickListener {

            val builder = AlertDialog.Builder(requireContext())
            builder.setMessage("¿Seguro que deseas renovar la API key de esta organización?")
                .setPositiveButton("Si") { _, id ->
                    viewModel.refreshOrganizationApiKey()
                }
                .setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
            val alert = builder.create()
            alert.show()
        }
    }
}
