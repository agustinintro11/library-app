package com.example.libraryapp.ui.screens.catalog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.libraryapp.R
import com.example.libraryapp.data.model.Book
import com.example.libraryapp.ui.common.BookColorAssigner


class CatalogItemsAdapter(
    context: Context,
    catalogItems: List<Book>?,
    private val onClickListener: (Book) -> Unit
) : RecyclerView.Adapter<CatalogItemsAdapter.CatalogItemsViewHolder>() {

    private var catalogItemsResultList = ArrayList<Book>()
    var context: Context

    init {
        catalogItemsResultList = catalogItems as ArrayList<Book>

        this.context = context

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogItemsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.book_item, parent, false
        )
        return CatalogItemsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return catalogItemsResultList.size
    }

    override fun onBindViewHolder(holder: CatalogItemsViewHolder, position: Int) {
        holder.catalogItemResultListItem(catalogItemsResultList[position], onClickListener)
    }

    fun addResults(catalogItems: List<Book>) {
        val initPosition = catalogItemsResultList.size
        catalogItemsResultList.addAll(catalogItems)
        notifyItemRangeInserted(initPosition, catalogItemsResultList.size)
    }

    fun clear() {
        catalogItemsResultList.clear()
        notifyDataSetChanged()
    }

    inner class CatalogItemsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var title: TextView = itemView.findViewById(R.id.tv_title)
        private var authors: TextView = itemView.findViewById(R.id.tv_authors)
        private var cover: View = itemView.findViewById(R.id.book_cover_view)
        private var backCover: View = itemView.findViewById(R.id.book_back_cover)


        fun catalogItemResultListItem(catalogItem: Book, onClickListener: (Book) -> Unit) {

            val bookColorAssigner: BookColorAssigner = BookColorAssigner()
            title.text = catalogItem.title
            authors.text = catalogItem.authors
            cover.background = bookColorAssigner.assignCover(catalogItem.isbn, context)
            backCover.background = bookColorAssigner.assignCover(catalogItem.isbn, context)

            cover.setOnClickListener { onClickListener(catalogItem) }
        }
    }
}
