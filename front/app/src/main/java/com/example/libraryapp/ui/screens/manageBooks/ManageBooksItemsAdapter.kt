package com.example.libraryapp.ui.screens.manageBooks

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.libraryapp.R
import com.example.libraryapp.data.model.Book
import com.google.android.material.card.MaterialCardView

class ManageBooksItemsAdapter(
    context: Context,
    manageBooksItems: List<Book>?,
    private val onClickListener: (Book) -> Unit,
    private val onEditListener: (Book) -> Unit,
    private val onDeleteListener: (Book) -> Unit
) : RecyclerView.Adapter<ManageBooksItemsAdapter.ManageBooksItemsViewHolder>() {

    private var manageBooksItemsResultList = ArrayList<Book>()
    var context: Context

    init {
        manageBooksItemsResultList = manageBooksItems as ArrayList<Book>

        this.context = context

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManageBooksItemsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.admin_book_item, parent, false
        )
        return ManageBooksItemsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return manageBooksItemsResultList.size
    }

    override fun onBindViewHolder(holder: ManageBooksItemsViewHolder, position: Int) {
        holder.manageBooksItemResultListItem(
            manageBooksItemsResultList[position],
            onClickListener,
            onEditListener,
            onDeleteListener
        )
    }

    fun removeFromList(book: Book) {
        val index = manageBooksItemsResultList.indexOfFirst { it.isbn == book.isbn }
        manageBooksItemsResultList.removeAt(index)
        notifyItemRemoved(index)
    }

    fun addResults(manageBooksItems: List<Book>) {
        val initPosition = manageBooksItemsResultList.size
        manageBooksItemsResultList.addAll(manageBooksItems)
        notifyItemRangeInserted(initPosition, manageBooksItemsResultList.size)
    }

    fun clear() {
        manageBooksItemsResultList.clear()
        notifyDataSetChanged()
    }

    inner class ManageBooksItemsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var title: TextView = itemView.findViewById(R.id.tv_admin_book_title)
        private var authors: TextView = itemView.findViewById(R.id.tv_admin_book_authors)
        private var year: TextView = itemView.findViewById(R.id.tv_admin_book_year)
        private var isbn: TextView = itemView.findViewById(R.id.tv_admin_book_isbn)
        private var quantity: TextView = itemView.findViewById(R.id.tv_admin_book_quantity)
        private var cardBody: MaterialCardView = itemView.findViewById(R.id.card)

        private var btnDelete: Button = itemView.findViewById(R.id.btn_delete_book)
        private var btnEdit: Button = itemView.findViewById(R.id.btn_edit_book)

        fun manageBooksItemResultListItem(
            manageBooksItem: Book,
            onClickListener: (Book) -> Unit,
            onEditListener: (Book) -> Unit,
            onDeleteListener: (Book) -> Unit
        ) {

            title.text = manageBooksItem.title
            authors.text = manageBooksItem.authors
            year.text = manageBooksItem.year.toString()
            quantity.text = manageBooksItem.quantity.toString()
            isbn.text = "ISBN: " + manageBooksItem.isbn

            cardBody.setOnClickListener { onClickListener(manageBooksItem) }
            btnEdit.setOnClickListener { onEditListener(manageBooksItem) }
            btnDelete.setOnClickListener { onDeleteListener(manageBooksItem) }
        }
    }
}
