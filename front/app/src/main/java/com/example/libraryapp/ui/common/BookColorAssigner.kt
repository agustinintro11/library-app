package com.example.libraryapp.ui.common

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.example.libraryapp.R

class BookColorAssigner {

    var bookCovers = ArrayList<Drawable>()
    var bookColors = ArrayList<Map<String, Int>>()

    fun assignCover(isbn: String, context: Context): Drawable {
        bookCovers.add(ContextCompat.getDrawable(context, R.drawable.gradient_blue)!!)
        bookCovers.add(ContextCompat.getDrawable(context, R.drawable.gradient_brown)!!)
        bookCovers.add(ContextCompat.getDrawable(context, R.drawable.gradient_green)!!)
        bookCovers.add(ContextCompat.getDrawable(context, R.drawable.gradient_light_green)!!)
        bookCovers.add(ContextCompat.getDrawable(context, R.drawable.gradient_orange)!!)
        bookCovers.add(ContextCompat.getDrawable(context, R.drawable.gradient_pink)!!)
        bookCovers.add(ContextCompat.getDrawable(context, R.drawable.gradient_purple)!!)
        bookCovers.add(ContextCompat.getDrawable(context, R.drawable.gradient_red)!!)
        bookCovers.add(ContextCompat.getDrawable(context, R.drawable.gradient_skyblue)!!)
        bookCovers.add(ContextCompat.getDrawable(context, R.drawable.gradient_yellow)!!)

        return bookCovers[isbn[isbn.length-2].toString().toInt()]
    }

    fun assignColor(isbn: String): Map<String, Int> {
        bookColors.add(mapOf("light" to R.color.book_blue_light, "dark" to R.color.book_blue))
        bookColors.add(mapOf("light" to R.color.book_brown_light, "dark" to R.color.book_brown))
        bookColors.add(mapOf("light" to R.color.book_green_light, "dark" to R.color.book_green))
        bookColors.add(mapOf("light" to R.color.book_light_green_light, "dark" to R.color.book_light_green))
        bookColors.add(mapOf("light" to R.color.book_orange_light, "dark" to R.color.book_orange))
        bookColors.add(mapOf("light" to R.color.book_pink_light, "dark" to R.color.book_pink))
        bookColors.add(mapOf("light" to R.color.book_purple_light, "dark" to R.color.book_purple))
        bookColors.add(mapOf("light" to R.color.book_red_light, "dark" to R.color.book_red))
        bookColors.add(mapOf("light" to R.color.book_skyblue_light, "dark" to R.color.book_skyblue))
        bookColors.add(mapOf("light" to R.color.book_yellow_light, "dark" to R.color.book_yellow))
        return bookColors[isbn[isbn.length-2].toString().toInt()]
    }
}