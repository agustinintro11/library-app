package com.example.libraryapp.ui.screens.manageBooks.addBook

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import android.widget.Toast
import androidx.core.text.bold
import androidx.transition.Slide
import com.example.libraryapp.MainActivity
import com.example.libraryapp.R
import com.example.libraryapp.databinding.FragmentAddBookBinding
import com.example.libraryapp.ui.common.BaseViewBindingFragment
import com.example.libraryapp.ui.common.extensions.themeColor
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.android.synthetic.main.book_form.*
import kotlinx.android.synthetic.main.fragment_add_book.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddBookFragment : BaseViewBindingFragment<FragmentAddBookBinding>() {

    private val viewModel: AddBookViewModel by viewModel()

    override fun inflateViewBinding(): FragmentAddBookBinding =
        FragmentAddBookBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().window.statusBarColor =
            requireContext().themeColor(R.attr.colorSecondaryVariant)

        setObservers()
        setOnFocusChangeListeners()

        enterTransition = MaterialContainerTransform().apply {
            startView = requireActivity().findViewById(R.id.fab_add_book)
            endView = add_book_layout
            duration = 300L
            scrimColor = Color.TRANSPARENT
            containerColor = requireContext().themeColor(R.attr.colorSurface)
            startContainerColor = requireContext().themeColor(R.attr.colorSecondary)
            endContainerColor = requireContext().themeColor(R.attr.colorSurface)
        }
        returnTransition = Slide().apply {
            duration = 300L
            addTarget(R.id.manageBooks_fragment)
        }

        btn_add_book.setOnClickListener {
            val title = ti_book_title.text.toString()
            val isbn = ti_book_isbn.text.toString()
            val authors = ti_book_authors.text.toString()
            val year = ti_book_year.text.toString()
            val quantity = ti_book_quantity.text.toString()

            val builder = AlertDialog.Builder(requireContext())
            builder.setMessage("¿Seguro que deseas agregar el libro ${title} (ISBN: ${isbn})?")
                .setPositiveButton("Si") { _, _ ->
                    viewModel.buttonClick(title, isbn, authors, year, quantity)
                }
                .setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
            val alert = builder.create()
            alert.show()

        }
    }

    private fun setObservers() {
        viewModel.error.observe(viewLifecycleOwner, {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
        })

        viewModel.addedBook.observe(viewLifecycleOwner, {
            val message = SpannableStringBuilder()
                .append("Se añadió el libro ")
                .bold { append(it.title) }
                .append(" correctamente")

            (activity as MainActivity).showSnackbar(message, true)
            router.popBackStack()
        })

        viewModel.errorTitle.observe(viewLifecycleOwner) {
            tf_book_title.error = it
        }

        viewModel.errorISBN.observe(viewLifecycleOwner) {
            tf_book_isbn.error = it
        }

        viewModel.errorAuthors.observe(viewLifecycleOwner) {
            tf_book_authors.error = it
        }

        viewModel.errorQuantity.observe(viewLifecycleOwner) {
            tf_book_quantity.error = it
        }

        viewModel.errorYear.observe(viewLifecycleOwner) {
            tf_book_year.error = it
        }

    }

    private fun setOnFocusChangeListeners() {

        ti_book_title.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                tf_book_title.error = ""
            }
        }

        ti_book_isbn.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                tf_book_isbn.error = ""
            }
        }

        ti_book_authors.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                tf_book_authors.error = ""
            }
        }

        ti_book_year.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                tf_book_year.error = ""
            }
        }

        ti_book_quantity.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                tf_book_quantity.error = ""
            }
        }
    }
}
