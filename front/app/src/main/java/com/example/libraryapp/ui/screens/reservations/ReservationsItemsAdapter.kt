package com.example.libraryapp.ui.screens.reservations

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.libraryapp.R
import com.example.libraryapp.data.model.ReservationOfBook
import com.google.android.material.chip.Chip
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ReservationsItemsAdapter(
    context: Context,
    reservationsItems: List<ReservationOfBook>?
) : RecyclerView.Adapter<ReservationsItemsAdapter.ReservationsItemsViewHolder>() {
    private var reservationsItemsResultList = ArrayList<ReservationOfBook>()
    var context: Context

    init {
        reservationsItemsResultList = reservationsItems as ArrayList<ReservationOfBook>

        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationsItemsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.reservation_of_book_item, parent, false
        )
        return ReservationsItemsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return reservationsItemsResultList.size
    }

    override fun onBindViewHolder(holder: ReservationsItemsViewHolder, position: Int) {
        holder.reservationsItemResultListItem(
            reservationsItemsResultList[position]
        )
    }

    fun addResults(reservationsItems: List<ReservationOfBook>) {
        val initPosition = reservationsItemsResultList.size
        reservationsItemsResultList.addAll(reservationsItems)
        notifyItemRangeInserted(initPosition, reservationsItemsResultList.size)
    }

    fun clear() {
        reservationsItemsResultList.clear()
        notifyDataSetChanged()
    }

    inner class ReservationsItemsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var username: TextView = itemView.findViewById(R.id.tv_reservation_username)
        private var date: TextView = itemView.findViewById(R.id.tv_reservation_startdate)
        private var id: TextView = itemView.findViewById(R.id.tv_reservation_id)
        private var returnBtn: Button = itemView.findViewById(R.id.btn_return_book)
        private var chipReturned: Chip = itemView.findViewById(R.id.chip_returned)

        fun reservationsItemResultListItem(
            bookReservationItem: ReservationOfBook
        ) {

            val localDateTime: LocalDateTime =
                LocalDateTime.parse(bookReservationItem.startDate.dropLast(1))
            val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val output: String = formatter.format(localDateTime)
            date.text = output
            id.text = bookReservationItem.id.toString()
            username.text = bookReservationItem.username

            returnBtn.visibility = View.GONE
            chipReturned.visibility = View.GONE
        }
    }
}
