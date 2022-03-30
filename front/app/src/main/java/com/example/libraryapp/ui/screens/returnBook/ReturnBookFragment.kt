package com.example.libraryapp.ui.screens.returnBook

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.libraryapp.R
import com.example.libraryapp.databinding.FragmentReturnBookBinding
import com.example.libraryapp.ui.common.BaseViewBindingFragment
import com.example.libraryapp.ui.common.InfiniteScrollListener
import com.example.libraryapp.ui.common.extensions.observeStateResult
import kotlinx.android.synthetic.main.fragment_return_book.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReturnBookFragment : BaseViewBindingFragment<FragmentReturnBookBinding>() {

    private val viewModel: ReturnBookViewModel by viewModel()

    private var currentPage = 1
    private lateinit var returnBookItemsAdapter: ReturnBookItemsAdapter

    override fun inflateViewBinding(): FragmentReturnBookBinding =
        FragmentReturnBookBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        returnBookItemsAdapter = ReturnBookItemsAdapter(
            this.requireContext(), ArrayList()
        ) {
            router.navigate(ReturnBookFragmentDirections.actionReturnBookFragmentToBookReservationsFragment(it))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().window.statusBarColor = resources.getColor(R.color.purple_700)

        initializeRecycler()
        loadData()

        swipe_rv_returnBooks.setOnRefreshListener {
            returnBookItemsAdapter.clear()
            currentPage = 1
            loadData()
            swipe_rv_returnBooks.isRefreshing = false
        }

        sv_search_returnBooks.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                currentPage = 1
                returnBookItemsAdapter.clear()
                loadData()
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
    }

    private fun initializeRecycler() {
        val linearLayoutManager = LinearLayoutManager(requireActivity())
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL

        rv_books_returnBooks.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            adapter = returnBookItemsAdapter
            addOnScrollListener(
                InfiniteScrollListener({ loadData() }, linearLayoutManager)
            )
        }
    }

    private fun loadData() {
        val filter =
            if (sv_search_returnBooks.query.isNullOrEmpty()) null else sv_search_returnBooks.query.toString()
        viewModel.loadResults(filter, currentPage, SIZE)
        viewModel.returnBookBooks.observeStateResult(this,
            onFailure = {
                pb_returnBooks.visibility = View.INVISIBLE
                Toast.makeText(requireActivity(), it.message, Toast.LENGTH_LONG).show()
            },
            onLoading = {
                if (returnBookItemsAdapter.itemCount == 0)
                    tv_not_found_return.visibility = View.INVISIBLE
                pb_returnBooks.visibility = View.VISIBLE
            },
            onSuccess = {
                pb_returnBooks.visibility = View.INVISIBLE
                returnBookItemsAdapter.addResults(it!!)

                val isEmpty = returnBookItemsAdapter.itemCount == 0
                tv_not_found_return.visibility = if (isEmpty) View.VISIBLE else View.GONE
                rv_books_returnBooks.visibility = if (isEmpty) View.GONE else View.VISIBLE
            }
        )
        currentPage++
    }

    companion object {
        const val SIZE = 18
    }
}
