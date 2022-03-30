package com.example.libraryapp.ui.common

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {
    protected val router: NavRouter by lazy { FragmentNavRouter(this) }

    protected fun setupScreenToolbar(
        toolbar: Toolbar,
        title: String? = null,
        hasUpButton: Boolean = true
    ) {
        (activity as? AppCompatActivity)?.apply {
            setSupportActionBar(toolbar)
            if (title != null) {
                supportActionBar?.title = title
            }
            if (hasUpButton) {
                setHasOptionsMenu(true)
                supportActionBar?.setDisplayHomeAsUpEnabled(hasUpButton)
            }
        }
    }
}
