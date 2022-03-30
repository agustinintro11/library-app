package com.example.libraryapp.ui.screens.book

import android.app.AlertDialog
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.example.libraryapp.MainActivity
import com.example.libraryapp.R
import com.example.libraryapp.databinding.FragmentBookBinding
import com.example.libraryapp.ui.common.BaseViewBindingFragment
import com.example.libraryapp.ui.common.BookColorAssigner
import com.example.libraryapp.ui.common.InfiniteScrollListener
import com.example.libraryapp.ui.common.extensions.observeStateResult
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.dialog_new_review_layout.*
import kotlinx.android.synthetic.main.fragment_book.*
import kotlinx.android.synthetic.main.reviews_layout.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class BookFragment : BaseViewBindingFragment<FragmentBookBinding>() {

    private val viewModel: BookViewModel by viewModel()

    private val args: BookFragmentArgs by navArgs()

    private var currentPage = 1
    private lateinit var reviewItemsAdapter: ReviewItemsAdapter

    override fun inflateViewBinding(): FragmentBookBinding =
        FragmentBookBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        reviewItemsAdapter = ReviewItemsAdapter(this.requireContext(), ArrayList(), null) {
            val builder = AlertDialog.Builder(requireContext())
            builder.setMessage(
                "¿Seguro que deseas eliminar tu reseña?\nEsta acción no podrá deshacerse"
            )
                .setPositiveButton("Si") { _, _ ->
                    viewModel.deleteReview(it)
                }
                .setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
            val alert = builder.create()
            alert.show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecycler()
        loadData()

        viewModel.getSessionUser()
        viewModel.sessionUser.observe(viewLifecycleOwner, {
            if (it.isAdmin) {
                btn_reservate.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.gery_inactive
                    )
                )
                btn_reservate.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.grey_active
                    )
                )

                btn_create_review.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.gery_inactive
                    )
                )
                btn_create_review.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.grey_active
                    )
                )
            }
            reviewItemsAdapter.userId = it.id!!
        })

        initializeView()

        book_toolbar.setNavigationOnClickListener {
            router.popBackStack()
        }

        btn_reservate.setOnClickListener {
            if (viewModel.sessionUser.value!!.isAdmin) Toast.makeText(
                requireActivity(),
                "No tienes permiso para realizar esta acción",
                Toast.LENGTH_SHORT
            ).show()

            else createDatePicker()
        }

        btn_create_review.setOnClickListener {
            if (viewModel.sessionUser.value!!.isAdmin) {
                Toast.makeText(
                    requireActivity(),
                    "No tienes permiso para realizar esta acción",
                    Toast.LENGTH_SHORT
                ).show()
            }else {
                val dialog = MaterialDialog(requireContext())
                    .noAutoDismiss()
                    .customView(R.layout.dialog_new_review_layout)
                dialog.view.background = null

                dialog.show {
                    btn_positive.setOnClickListener {
                        viewModel.createReview(
                            dialog_rating.rating,
                            ti_description.text.toString(),
                            args.book.isbn
                        )
                        loadData()
                        dialog.dismiss()
                    }
                }
            }

        }
        viewModel.createdReservation.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Reserva Realizada")
                    .setMessage(
                        "La reserva para el libro ${tv_title.text} con fecha de inicio ${it.startDate} se realizó correctamente"
                    )
                    .setPositiveButton("OK") { dialog, which ->
                    }
                    .show()
            }
        })

        viewModel.deletedReview.observe(viewLifecycleOwner, {
            val message = SpannableStringBuilder()
                .append("Se eliminó la reseña correctamente")

            (activity as MainActivity).showSnackbar(message, false)
            reviewItemsAdapter.removeFromList(it)
        })

        swipe_rv_reviews.setOnRefreshListener {
            reviewItemsAdapter.clear()
            currentPage = 1
            loadData()
            swipe_rv_reviews.isRefreshing = false
        }
    }

    private fun initializeView() {
        val book = args.book

        setObservers()

        val bookColorAssigner = BookColorAssigner()

        motion_layout.setTransitionListener(object : MotionLayout.TransitionListener {

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {

            }

            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {

            }

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {

            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                if (p1 == R.id.start_motion_layout) {
                    btn_reservate.extend()
                    tv_not_found_reviews.visibility = View.GONE
                } else {
                    btn_reservate.shrink()
                    val isEmpty = reviewItemsAdapter.itemCount == 0
                    tv_not_found_reviews.visibility = if (isEmpty) View.VISIBLE else View.GONE
                    rv_reviews.visibility = if (isEmpty) View.GONE else View.VISIBLE
                }
            }
        })

        book_background.background = bookColorAssigner.assignCover(book.isbn, requireContext())
        book_cover_background.background =
            bookColorAssigner.assignCover(book.isbn, requireContext())

        val dark = ContextCompat.getColor(
            requireContext(),
            bookColorAssigner.assignColor(book.isbn)["dark"]!!
        )
        val light = ContextCompat.getColor(
            requireContext(),
            bookColorAssigner.assignColor(book.isbn)["light"]!!
        )

        requireActivity().window.statusBarColor = dark

        tv_book_title.setTextColor(dark)
        tv_book_authors.setTextColor(light)

        tv_book_title.text = book.title
        tv_book_authors.text = book.authors
        tv_book_isbn.text = book.isbn
        tv_book_quantity.text = book.quantity.toString()
        tv_book_year.text = book.year.toString()

        tv_title.text = book.title
        tv_authors.text = book.authors
    }

    private fun createDatePicker() {
        val constraintsBuilder =
            CalendarConstraints.Builder()
                .setValidator(DateValidatorPointForward.now())

        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Fecha de inicio de la reserva")
                .setCalendarConstraints(constraintsBuilder.build())
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()

        datePicker.show(parentFragmentManager, "tag")

        datePicker.addOnPositiveButtonClickListener {
            val outputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }
            val builder = AlertDialog.Builder(requireContext())
            builder.setMessage(
                "¿Seguro que deseas realizar una reserva del libro ${args.book.title} para el día ${
                    outputDateFormat.format(
                        it
                    )
                }?"
            )
                .setPositiveButton("Si") { _, _ ->
                    viewModel.buttonClick(outputDateFormat.format(it), args.book.isbn)
                }
                .setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
            val alert = builder.create()
            alert.show()
        }

    }

    private fun setObservers() {
        viewModel.error.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
        })
    }

    private fun initializeRecycler() {
        val linearLayoutManager = LinearLayoutManager(requireActivity())
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL

        rv_reviews.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            adapter = reviewItemsAdapter
            addOnScrollListener(
                InfiniteScrollListener({ loadData() }, linearLayoutManager)
            )
        }

    }

    private fun loadData() {
        viewModel.loadResults(args.book.isbn, currentPage, SIZE)
        viewModel.bookReviews.observeStateResult(this,
            onFailure = {
                pb_reviews.visibility = View.INVISIBLE
                Toast.makeText(requireActivity(), it.message, Toast.LENGTH_LONG).show()
            },
            onLoading = {
                if (reviewItemsAdapter.itemCount == 0)
                    tv_not_found_reviews.visibility = View.INVISIBLE
                pb_reviews.visibility = View.VISIBLE
            },
            onSuccess = {
                pb_reviews.visibility = View.INVISIBLE
                reviewItemsAdapter.addResults(it!!)

            }
        )
        currentPage++
    }

    companion object {
        const val SIZE = 18
    }

}
