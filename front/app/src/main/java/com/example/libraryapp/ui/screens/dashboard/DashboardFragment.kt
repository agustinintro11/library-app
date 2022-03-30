package com.example.libraryapp.ui.screens.dashboard

import android.os.Bundle
import android.view.View
import com.example.libraryapp.databinding.FragmentDashboardBinding
import com.example.libraryapp.ui.common.BaseViewBindingFragment
import kotlinx.android.synthetic.main.fragment_dashboard.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : BaseViewBindingFragment<FragmentDashboardBinding>() {
    private val viewModel: DashboardViewModel by viewModel()

    override fun inflateViewBinding(): FragmentDashboardBinding =
        FragmentDashboardBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_logout.setOnClickListener {
           // viewModel.closeSession()
            router.navigate(DashboardFragmentDirections.actionDashboardFragmentToSplashFragment())
        }
    }
}