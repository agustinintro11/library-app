package com.example.libraryapp.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding


abstract class BaseViewBindingFragment<V : ViewBinding> : BaseFragment() {
    private var _binding: V? = null
    private val viewBinding get() = requireNotNull(_binding)

    protected abstract fun inflateViewBinding(): V

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = inflateViewBinding()
        return viewBinding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
