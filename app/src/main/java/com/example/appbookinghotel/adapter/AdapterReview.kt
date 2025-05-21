package com.example.appbookinghotel.adapter

import android.view.View
import android.view.ViewGroup
import android.content.Context
import android.view.LayoutInflater
import com.example.appbookinghotel.R
import android.annotation.SuppressLint
import com.example.core.`object`.Review
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.example.appbookinghotel.callback.ReviewClickListener
import com.example.appbookinghotel.databinding.ItemContainerReviewsBinding

class AdapterReview(private var listReview: List<Review>,
                    private val onClick: ReviewClickListener,
                    private val userId: String, private val context: Context)
    : RecyclerView.Adapter<AdapterReview.ReviewHolder>(){
    inner class ReviewHolder (
        val binding : ItemContainerReviewsBinding
    ) : RecyclerView.ViewHolder(binding.root)
    {
        fun setData (review : Review)
        {
            binding.rating.rating = review.star.toFloat()
            binding.name.text = review.userName
            binding.comment.text = review.comment
            binding.date.text = review.date

            binding.icInfo.setOnClickListener {
                val view = LayoutInflater.from(context).inflate(R.layout.background_custum_review_item, null)
                val dialog = AlertDialog.Builder(context).setView(view).create()

                view.findViewById<AppCompatButton>(R.id.btnDelete).setOnClickListener {
                    onClick.onClickDelete(review.id!!)
                    dialog.dismiss()
                }
                view.findViewById<AppCompatButton>(R.id.btnCancel).setOnClickListener {
                    dialog.dismiss()
                }

                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                dialog.show()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun dataChange (newData : List<Review>)
    {
        listReview = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewHolder {
        val binding = ItemContainerReviewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listReview.size
    }

    override fun onBindViewHolder(holder: ReviewHolder, position: Int) {
        holder.setData(listReview[position])

        val review = listReview[position]

        if (review.userId == userId)
        {
            holder.binding.icInfo.visibility = View.VISIBLE
        }
        else
        {
            holder.binding.icInfo.visibility = View.GONE
        }
    }
}