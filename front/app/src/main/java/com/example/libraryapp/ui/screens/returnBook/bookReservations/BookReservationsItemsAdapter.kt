package com.example.libraryapp.ui.screens.returnBook.bookReservations

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
import com.example.libraryapp.data.model.ReservationOfBook
import com.google.android.material.chip.Chip
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class BookReservationsItemsAdapter(
    context: Context,
    bookReservationItems: List<ReservationOfBook>?,
    private val onClickListener: (ReservationOfBook) -> Unit,
) : RecyclerView.Adapter<BookReservationsItemsAdapter.BookReservationItemsViewHolder>() {

    private var bookReservationItemsResultList = ArrayList<ReservationOfBook>()
    var context: Context

    init {
        bookReservationItemsResultList = bookReservationItems as ArrayList<ReservationOfBook>

        this.context = context

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BookReservationItemsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.reservation_of_book_item, parent, false
        )
        return BookReservationItemsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return bookReservationItemsResultList.size
    }

    override fun onBindViewHolder(holder: BookReservationItemsViewHolder, position: Int) {
        holder.bookReservationItemResultListItem(
            bookReservationItemsResultList[position],
            onClickListener
        )
    }

    fun removeFromList(reservationOfBook: ReservationOfBook) {
        val index = bookReservationItemsResultList.indexOfFirst { it.id == reservationOfBook.id }
        bookReservationItemsResultList.removeAt(index)
        notifyItemRemoved(index)
    }

    fun refreshItem(reservationOfBook: ReservationOfBook) {
        val index = bookReservationItemsResultList.indexOfFirst { it.id == reservationOfBook.id }
        bookReservationItemsResultList[index] = reservationOfBook
        notifyItemChanged(index)
    }

    fun addResults(bookReservationItems: List<ReservationOfBook>) {
        val initPosition = bookReservationItemsResultList.size
        bookReservationItemsResultList.addAll(bookReservationItems)
        notifyItemRangeInserted(initPosition, bookReservationItemsResultList.size)
    }

    fun clear() {
        bookReservationItemsResultList.clear()
        notifyDataSetChanged()
    }

    inner class BookReservationItemsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var username: TextView = itemView.findViewById(R.id.tv_reservation_username)
        private var date: TextView = itemView.findViewById(R.id.tv_reservation_startdate)
        private var id: TextView = itemView.findViewById(R.id.tv_reservation_id)
        private var returnBtn: Button = itemView.findViewById(R.id.btn_return_book)
        private var chipReturned: Chip = itemView.findViewById(R.id.chip_returned)

        fun bookReservationItemResultListItem(
            bookReservationItem: ReservationOfBook,
            onClickListener: (ReservationOfBook) -> Unit
        ) {

            val localDateTime: LocalDateTime = LocalDateTime.parse(bookReservationItem.startDate.dropLast(1))
            val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val output: String = formatter.format(localDateTime)
            date.text = output
            id.text = bookReservationItem.id.toString()
            username.text = bookReservationItem.username

            returnBtn.visibility = if(bookReservationItem.delivered) View.GONE else View.VISIBLE
            chipReturned.visibility = if(bookReservationItem.delivered) View.VISIBLE else View.GONE

            returnBtn.setOnClickListener { onClickListener(bookReservationItem) }
        }
    }
}
