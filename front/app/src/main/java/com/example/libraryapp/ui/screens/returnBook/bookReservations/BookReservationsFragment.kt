package com.example.libraryapp.ui.screens.returnBook.bookReservations

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.core.text.bold
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.libraryapp.MainActivity
import com.example.libraryapp.R
import com.example.libraryapp.databinding.FragmentBookReservationsBinding
import com.example.libraryapp.ui.common.BaseViewBindingFragment
import com.example.libraryapp.ui.common.InfiniteScrollListener
import com.example.libraryapp.ui.common.extensions.observeStateResult
import com.example.libraryapp.ui.common.extensions.themeColor
import kotlinx.android.synthetic.main.fragment_book_reservations.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookReservationsFragment : BaseViewBindingFragment<FragmentBookReservationsBinding>() {

    private val viewModel: BookReservationsViewModel by viewModel()
    private val args: BookReservationsFragmentArgs by navArgs()


    private var currentPage = 1
    private lateinit var returnBookItemsAdapter: BookReservationsItemsAdapter

    override fun inflateViewBinding(): FragmentBookReservationsBinding =
        FragmentBookReservationsBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        returnBookItemsAdapter = BookReservationsItemsAdapter(
            this.requireContext(), ArrayList()
        ) {
            viewModel.returnBook(it.id)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().window.statusBarColor =
            requireContext().themeColor(R.attr.colorSecondaryVariant)

        tv_book_reservations_title.text = args.book.title
        initializeRecycler()
        loadData()

        swipe_rv_returnBooks_reservations.setOnRefreshListener {
            returnBookItemsAdapter.clear()
            currentPage = 1
            loadData()
            swipe_rv_returnBooks_reservations.isRefreshing = false
        }

        sv_search_returnBooks_reservations.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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

        viewModel.returnedBook.observe(viewLifecycleOwner,{
            returnBookItemsAdapter.refreshItem(it)

            val message = SpannableStringBuilder()
                .append("Se devolvió la reserva ${it.id} del libro ")
                .bold { append(args.book.title) }
                .append(" correctamente")

            (activity as MainActivity).showSnackbar(message, true)
        })
    }

    private fun initializeRecycler() {
        val linearLayoutManager = LinearLayoutManager(requireActivity())
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL

        rv_returnBooks_reservations.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            adapter = returnBookItemsAdapter
            addOnScrollListener(
                InfiniteScrollListener({ loadData() }, linearLayoutManager)
            )
        }
    }

    private fun loadData() {
        val username =
            if (sv_search_returnBooks_reservations.query.isNullOrEmpty()) null else sv_search_returnBooks_reservations.query.toString()
        viewModel.loadResults(args.book.isbn,username, currentPage, SIZE)
        viewModel.reservationsOfBooks.observeStateResult(this,
            onFailure = {
                pb_returnBooks_reservations.visibility = View.INVISIBLE
                Toast.makeText(requireActivity(), it.message, Toast.LENGTH_LONG).show()
            },
            onLoading = {
                if (returnBookItemsAdapter.itemCount == 0)
                    tv_not_found_reservations_of_book.visibility = View.INVISIBLE
                pb_returnBooks_reservations.visibility = View.VISIBLE
            },
            onSuccess = {
                pb_returnBooks_reservations.visibility = View.INVISIBLE
                returnBookItemsAdapter.addResults(it!!)

                val isEmpty = returnBookItemsAdapter.itemCount == 0
                tv_not_found_reservations_of_book.visibility = if (isEmpty) View.VISIBLE else View.GONE
                rv_returnBooks_reservations.visibility = if (isEmpty) View.GONE else View.VISIBLE
            }
        )
        currentPage++
    }

    companion object {
        const val SIZE = 18
    }
}
