package com.example.libraryapp.ui.screens.returnBook

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.libraryapp.R
import com.example.libraryapp.data.model.Book
import com.google.android.material.card.MaterialCardView

class ReturnBookItemsAdapter(
    context: Context,
    returnBookItems: List<Book>?,
    private val onClickListener: (Book) -> Unit,
) : RecyclerView.Adapter<ReturnBookItemsAdapter.ReturnBookItemsViewHolder>() {

    private var returnBookItemsResultList = ArrayList<Book>()
    var context: Context

    init {
        returnBookItemsResultList = returnBookItems as ArrayList<Book>

        this.context = context

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReturnBookItemsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.return_book_item, parent, false
        )
        return ReturnBookItemsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return returnBookItemsResultList.size
    }

    override fun onBindViewHolder(holder: ReturnBookItemsViewHolder, position: Int) {
        holder.returnBookItemResultListItem(
            returnBookItemsResultList[position],
            onClickListener
        )
    }

    fun removeFromList(book: Book) {
        val index = returnBookItemsResultList.indexOfFirst { it.isbn == book.isbn }
        returnBookItemsResultList.removeAt(index)
        notifyItemRemoved(index)
    }

    fun addResults(returnBookItems: List<Book>) {
        val initPosition = returnBookItemsResultList.size
        returnBookItemsResultList.addAll(returnBookItems)
        notifyItemRangeInserted(initPosition, returnBookItemsResultList.size)
    }

    fun clear() {
        returnBookItemsResultList.clear()
        notifyDataSetChanged()
    }

    inner class ReturnBookItemsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var title: TextView = itemView.findViewById(R.id.tv_return_book_title)
        private var authors: TextView = itemView.findViewById(R.id.tv_return_book_authors)
        private var isbn: TextView = itemView.findViewById(R.id.tv_return_book_isbn)
        private var body: ConstraintLayout = itemView.findViewById(R.id.return_book_body)

        fun returnBookItemResultListItem(
            returnBookItem: Book,
            onClickListener: (Book) -> Unit
        ) {

            title.text = returnBookItem.title
            authors.text = returnBookItem.authors
            isbn.text = "ISBN: " + returnBookItem.isbn

            body.setOnClickListener { onClickListener(returnBookItem) }
        }
    }
}
