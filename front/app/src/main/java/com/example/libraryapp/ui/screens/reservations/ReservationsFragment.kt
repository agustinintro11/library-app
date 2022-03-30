package com.example.libraryapp.ui.screens.reservations

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.libraryapp.R
import com.example.libraryapp.data.model.Book
import com.example.libraryapp.databinding.FragmentReservationsBinding
import com.example.libraryapp.ui.common.BaseViewBindingFragment
import com.example.libraryapp.ui.common.InfiniteScrollListener
import com.example.libraryapp.ui.common.extensions.observeStateResult
import com.example.libraryapp.ui.screens.catalog.CatalogFragmentDirections
import kotlinx.android.synthetic.main.fragment_catalog.*
import kotlinx.android.synthetic.main.fragment_reservations.*
import kotlinx.android.synthetic.main.fragment_reservations.not_found_tv
import kotlinx.android.synthetic.main.return_book_item.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReservationsFragment : BaseViewBindingFragment<FragmentReservationsBinding>() {
    private val viewModel: ReservationsViewModel by viewModel()

    override fun inflateViewBinding(): FragmentReservationsBinding =
        FragmentReservationsBinding.inflate(layoutInflater)

    private var currentPage = 1
    private lateinit var reservationsItemsAdapter: ReservationsItemsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        reservationsItemsAdapter = ReservationsItemsAdapter(this.requireContext(), ArrayList())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecycler()
        loadData()

        swipe_rv_reservations.setOnRefreshListener {
            reservationsItemsAdapter.clear()
            currentPage = 1
            loadData()
            swipe_rv_reservations.isRefreshing = false
        }

        requireActivity().window.statusBarColor = resources.getColor(R.color.purple_700)

        chipGroup.setOnCheckedChangeListener { group, checkedId ->
            reservationsItemsAdapter.clear()
            currentPage = 1
            loadData()
        }

    }

    private fun initializeRecycler() {
        val linearLayoutManager = LinearLayoutManager(requireActivity())
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL

        val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        divider.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.divider)!!)

        rv_reserved_books.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            adapter = reservationsItemsAdapter
            addOnScrollListener(
                InfiniteScrollListener({ loadData() }, linearLayoutManager)
            )
        }
    }
    private fun loadData() {

        val status = when(chipGroup.checkedChipId){
            R.id.chip_next -> "next"
            R.id.chip_active -> "active"
            R.id.chip_finished -> "finished"
            R.id.chip_pending -> "pending"
            else -> ""
        }

        viewModel.loadResults(status, currentPage, SIZE)
        viewModel.activeReservations.observeStateResult(this,
            onFailure = {
                pb_active_reservations.visibility = View.INVISIBLE
                Toast.makeText(requireActivity(), it.message, Toast.LENGTH_LONG).show()
            },
            onLoading = {
                if (reservationsItemsAdapter.itemCount == 0)
                    not_found_tv.visibility = View.INVISIBLE
                pb_active_reservations.visibility = View.VISIBLE
            },
            onSuccess = {
                pb_active_reservations.visibility = View.INVISIBLE
                reservationsItemsAdapter.addResults(it!!)
                val isEmpty = reservationsItemsAdapter.itemCount == 0
                not_found_tv.visibility = if (isEmpty) View.VISIBLE else View.GONE
                rv_reserved_books.visibility = if (isEmpty) View.GONE else View.VISIBLE
            }
        )
        currentPage++
    }

    companion object {
        const val SIZE = 18
    }

}
