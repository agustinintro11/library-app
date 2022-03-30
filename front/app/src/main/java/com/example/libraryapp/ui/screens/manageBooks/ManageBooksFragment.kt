package com.example.libraryapp.ui.screens.manageBooks

import android.app.AlertDialog
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.core.text.bold
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.libraryapp.MainActivity
import com.example.libraryapp.R
import com.example.libraryapp.databinding.FragmentManageBooksBinding
import com.example.libraryapp.ui.common.BaseViewBindingFragment
import com.example.libraryapp.ui.common.InfiniteScrollListener
import com.example.libraryapp.ui.common.extensions.observeStateResult
import com.google.android.material.transition.MaterialElevationScale
import kotlinx.android.synthetic.main.fragment_catalog.*
import kotlinx.android.synthetic.main.fragment_manage_books.*
import kotlinx.android.synthetic.main.fragment_manage_books.not_found_tv
import org.koin.androidx.viewmodel.ext.android.viewModel

class ManageBooksFragment : BaseViewBindingFragment<FragmentManageBooksBinding>() {
    private val viewModel: ManageBooksViewModel by viewModel()

    override fun inflateViewBinding(): FragmentManageBooksBinding =
        FragmentManageBooksBinding.inflate(layoutInflater)

    private var currentPage = 1
    private lateinit var manageBooksItemsAdapter: ManageBooksItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        manageBooksItemsAdapter = ManageBooksItemsAdapter(this.requireContext(), ArrayList(), {
            router.navigate(
                ManageBooksFragmentDirections.actionManageBooksFragmentToEditBookFragment(it)
            )
        },
            {
                router.navigate(
                    ManageBooksFragmentDirections.actionManageBooksFragmentToEditBookFragment(it)
                )
            },
            {
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage(
                    "¿Seguro que deseas eliminar el libro ${it.title} (ISBN: " +
                            "${it.isbn})?\nEsta acción no podrá deshacerse"
                )
                    .setPositiveButton("Si") { _, _ ->
                        viewModel.deleteBook(it)
                    }
                    .setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
                val alert = builder.create()
                alert.show()
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().window.statusBarColor = resources.getColor(R.color.purple_700)

        viewModel.error.observe(viewLifecycleOwner, {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
        })

        viewModel.deletedBook.observe(viewLifecycleOwner, {
            val message = SpannableStringBuilder()
                .append("Se eliminó el libro")
                .bold { append(it.title) }
                .append(" correctamente")

            (activity as MainActivity).showSnackbar(message, false)
            manageBooksItemsAdapter.removeFromList(it)
        })

        initializeRecycler()
        loadData()

        swipe_rv_manageBooks.setOnRefreshListener {
            manageBooksItemsAdapter.clear()
            currentPage = 1
            loadData()
            swipe_rv_manageBooks.isRefreshing = false
        }

        sv_search_manageBooks.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                currentPage = 1
                manageBooksItemsAdapter.clear()
                loadData()
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })

        fab_add_book.setOnClickListener {
            (activity as MainActivity).getCurrentNavigationFragment()?.apply {
                exitTransition = MaterialElevationScale(false).apply {
                    duration = 300L
                }
                reenterTransition = MaterialElevationScale(true).apply {
                    duration = 300L
                }
            }
            router.navigate(ManageBooksFragmentDirections.actionManageBooksFragmentToAddBookFragment())
        }

    }

    private fun initializeRecycler() {
        val linearLayoutManager = LinearLayoutManager(requireActivity())
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL

        rv_books_manageBooks.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            adapter = manageBooksItemsAdapter
            addOnScrollListener(
                InfiniteScrollListener({ loadData() }, linearLayoutManager)
            )
        }
    }

    private fun loadData() {
        val filter = if (sv_search_manageBooks.query.isNullOrEmpty()) null else sv_search_manageBooks.query.toString()
        viewModel.loadResults(filter, currentPage, SIZE)
        viewModel.manageBooksBooks.observeStateResult(this,
            onFailure = {
                pb_manageBooks.visibility = View.INVISIBLE
                Toast.makeText(requireActivity(), it.message, Toast.LENGTH_LONG).show()
            },
            onLoading = {
                if (manageBooksItemsAdapter.itemCount == 0)
                    not_found_tv.visibility = View.INVISIBLE
                pb_manageBooks.visibility = View.VISIBLE
            },
            onSuccess = {
                pb_manageBooks.visibility = View.INVISIBLE
                manageBooksItemsAdapter.addResults(it!!)

                val isEmpty = manageBooksItemsAdapter.itemCount == 0
                not_found_tv.visibility = if (isEmpty) View.VISIBLE else View.GONE
                rv_books_manageBooks.visibility = if (isEmpty) View.GONE else View.VISIBLE
            }
        )
        currentPage++
    }

    companion object {
        const val SIZE = 18
    }
}
