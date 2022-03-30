package com.example.libraryapp.ui.screens.book

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.libraryapp.R
import com.example.libraryapp.data.model.ReviewOfBook
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.Chip
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ReviewItemsAdapter(
    context: Context,
    reviewOfBookItems: List<ReviewOfBook>?,
    userId: Int?,
    private val onDeleteClickListener: (ReviewOfBook) -> Unit
) : RecyclerView.Adapter<ReviewItemsAdapter.ReviewItemsViewHolder>() {

    var userId = 0
    private var reviewItemsResultList = ArrayList<ReviewOfBook>()
    var context: Context

    init {
        reviewItemsResultList = reviewOfBookItems as ArrayList<ReviewOfBook>
        if (userId != null) {
            this.userId = userId
        }
        this.context = context

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewItemsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.review_item, parent, false
        )
        return ReviewItemsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return reviewItemsResultList.size
    }

    override fun onBindViewHolder(holder: ReviewItemsViewHolder, position: Int) {
        holder.reviewItemResultListItem(
            userId,
            reviewItemsResultList[position],
            onDeleteClickListener
        )
    }

    fun removeFromList(review: ReviewOfBook) {
        val index = reviewItemsResultList.indexOfFirst { it.id == review.id }
        reviewItemsResultList.removeAt(index)
        notifyItemRemoved(index)
    }

    fun addResults(reviewOfBookItems: List<ReviewOfBook>) {
        val initPosition = reviewItemsResultList.size
        reviewItemsResultList.addAll(reviewOfBookItems)
        notifyItemRangeInserted(initPosition, reviewItemsResultList.size)
    }

    fun clear() {
        reviewItemsResultList.clear()
        notifyDataSetChanged()
    }

    inner class ReviewItemsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var user: TextView = itemView.findViewById(R.id.tv_review_user)
        private var description: TextView = itemView.findViewById(R.id.tv_review_description)
        private var rating: RatingBar = itemView.findViewById(R.id.ratingBar)
        private var date: Chip = itemView.findViewById(R.id.chip_review_date)
        private var card: MaterialCardView = itemView.findViewById(R.id.card)
        private var deleteBtn: Button = itemView.findViewById(R.id.btn_delete_review)

        fun reviewItemResultListItem(
            userId: Int,
            reviewOfBookItem: ReviewOfBook,
            onDeleteClickListener: (ReviewOfBook) -> Unit
        ) {

            user.text = reviewOfBookItem.username
            description.text = reviewOfBookItem.description
            rating.rating = reviewOfBookItem.rating
            val localDateTime: LocalDateTime = LocalDateTime.parse(reviewOfBookItem.created)
            val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val output: String = formatter.format(localDateTime)
            date.text = output
            val isWithin24Hours = LocalDateTime.now().minusHours(24) < localDateTime

            deleteBtn.visibility =
                if (userId == reviewOfBookItem.userId && isWithin24Hours) View.VISIBLE else View.GONE

            deleteBtn.setOnClickListener { onDeleteClickListener(reviewOfBookItem) }
        }
    }
}
