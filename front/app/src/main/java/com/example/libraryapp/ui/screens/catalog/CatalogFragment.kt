package com.example.libraryapp.ui.screens.catalog

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.libraryapp.R
import com.example.libraryapp.databinding.FragmentCatalogBinding
import com.example.libraryapp.ui.common.BaseViewBindingFragment
import com.example.libraryapp.ui.common.InfiniteScrollListener
import com.example.libraryapp.ui.common.extensions.observeStateResult
import kotlinx.android.synthetic.main.fragment_catalog.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CatalogFragment : BaseViewBindingFragment<FragmentCatalogBinding>() {
    private val viewModel: CatalogViewModel by viewModel()

    override fun inflateViewBinding(): FragmentCatalogBinding =
        FragmentCatalogBinding.inflate(layoutInflater)

    private var currentPage = 1
    private lateinit var catalogItemsAdapter: CatalogItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        catalogItemsAdapter = CatalogItemsAdapter(this.requireContext(), ArrayList()) {
            router.navigate(CatalogFragmentDirections.actionCatalogFragmentToBookFragment(it))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().window.statusBarColor = resources.getColor(R.color.purple_700)

        initializeRecycler()
        loadData()

        swipe_rv_catalog.setOnRefreshListener {
            catalogItemsAdapter.clear()
            currentPage = 1
            loadData()
            swipe_rv_catalog.isRefreshing = false
        }

        sv_search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                currentPage = 1
                catalogItemsAdapter.clear()
                loadData()
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })

    }

    private fun initializeRecycler() {
        val gridLayoutManager = GridLayoutManager(requireActivity(), 3)
        gridLayoutManager.orientation = GridLayoutManager.VERTICAL

        rv_books.apply {
            setHasFixedSize(true)
            layoutManager = gridLayoutManager
            adapter = catalogItemsAdapter
            addOnScrollListener(
                InfiniteScrollListener({ loadData() }, gridLayoutManager)
            )
        }
    }

    private fun loadData() {
        val filter = if (sv_search.query.isNullOrEmpty()) null else sv_search.query.toString()
        viewModel.loadResults(filter, currentPage, SIZE)
        viewModel.catalogBooks.observeStateResult(this,
            onFailure = {
                pb_catalog.visibility = View.INVISIBLE
                Toast.makeText(requireActivity(), it.message, Toast.LENGTH_LONG).show()
            },
            onLoading = {
                if (catalogItemsAdapter.itemCount == 0)
                    not_found_tv.visibility = View.INVISIBLE
                pb_catalog.visibility = View.VISIBLE
            },
            onSuccess = {
                pb_catalog.visibility = View.INVISIBLE
                catalogItemsAdapter.addResults(it!!)

                val isEmpty = catalogItemsAdapter.itemCount == 0
                not_found_tv.visibility = if (isEmpty) View.VISIBLE else View.GONE
                rv_books.visibility = if (isEmpty) View.GONE else View.VISIBLE
            }
        )
        currentPage++
    }

    companion object {
        const val SIZE = 18
    }

}
