package com.example.libraryapp.ui.screens.manageBooks.editBook

import android.app.AlertDialog
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import android.widget.Toast
import androidx.core.text.bold
import androidx.navigation.fragment.navArgs
import com.example.libraryapp.MainActivity
import com.example.libraryapp.R
import com.example.libraryapp.databinding.FragmentEditBookBinding
import com.example.libraryapp.ui.common.BaseViewBindingFragment
import com.example.libraryapp.ui.common.extensions.themeColor
import kotlinx.android.synthetic.main.book_form.*
import kotlinx.android.synthetic.main.fragment_edit_book.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditBookFragment : BaseViewBindingFragment<FragmentEditBookBinding>() {

    private val viewModel: EditBookViewModel by viewModel()
    private val args: EditBookFragmentArgs by navArgs()

    override fun inflateViewBinding(): FragmentEditBookBinding =
        FragmentEditBookBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.currentBook.value = args.book

        ti_book_title.setText(args.book.title)
        ti_book_isbn.setText(args.book.isbn)
        ti_book_authors.setText(args.book.authors)
        ti_book_year.setText(args.book.year.toString())
        ti_book_quantity.setText(args.book.quantity.toString())

        requireActivity().window.statusBarColor =
            requireContext().themeColor(R.attr.colorSecondaryVariant)

        setObservers()
        setOnFocusChangeListeners()

        btn_editBook.setOnClickListener {
            val title = ti_book_title.text.toString()
            val isbn = ti_book_isbn.text.toString()
            val authors = ti_book_authors.text.toString()
            val year = ti_book_year.text.toString()
            val quantity = ti_book_quantity.text.toString()

            val builder = AlertDialog.Builder(requireContext())
            builder.setMessage("¿Seguro que deseas editar el libro ${title} (ISBN: ${isbn})?")
                .setPositiveButton("Si") { _, _ ->
                    viewModel.buttonClick(title, isbn, authors, year, quantity)
                }
                .setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
            val alert = builder.create()
            alert.show()

        }

        btn_revert.setOnClickListener {

            val builder = AlertDialog.Builder(requireContext())
            builder.setMessage("¿Deseas revertir los cambios?")
                .setPositiveButton("Si") { _, _ ->
                    ti_book_title.setText(args.book.title)
                    ti_book_isbn.setText(args.book.isbn)
                    ti_book_authors.setText(args.book.authors)
                    ti_book_year.setText(args.book.year.toString())
                    ti_book_quantity.setText(args.book.quantity.toString())

                    viewModel.isModified.value = false
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

        viewModel.isModified.observe(viewLifecycleOwner, {
            btn_editBook.isEnabled = it
            btn_revert.isEnabled = it
        })

        viewModel.modifiedBook.observe(viewLifecycleOwner, {
            val message = SpannableStringBuilder()
                .append("Se modificó el libro ")
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
            } else {
                viewModel.isModified.value = isModified()
            }
        }

        ti_book_isbn.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                tf_book_isbn.error = ""
            } else {
                viewModel.isModified.value = isModified()
            }
        }

        ti_book_authors.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                tf_book_authors.error = ""
            } else {
                viewModel.isModified.value = isModified()
            }
        }

        ti_book_year.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                tf_book_year.error = ""
            } else {
                viewModel.isModified.value = isModified()
            }
        }

        ti_book_quantity.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                tf_book_quantity.error = ""
            } else {
                viewModel.isModified.value = isModified()

            }
        }
    }

    private fun isModified(): Boolean {
        val isTitleModified = ti_book_title.text.toString() != args.book.title
        val isIsbnModified = ti_book_isbn.text.toString() != args.book.isbn
        val isAuthorsModified = ti_book_authors.text.toString() != args.book.authors
        val isYearModified = ti_book_year.text.toString().toInt() != args.book.year
        val isQuantityModified = ti_book_quantity.text.toString().toInt() != args.book.quantity
        return isTitleModified || isIsbnModified || isAuthorsModified || isYearModified || isQuantityModified
    }

}
