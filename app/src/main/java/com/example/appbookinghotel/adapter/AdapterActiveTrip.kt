package com.example.appbookinghotel.adapter

import android.annotation.SuppressLint
import android.icu.text.DecimalFormat
import android.icu.text.DecimalFormatSymbols
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appbookinghotel.databinding.ItemContainerListActiveTripBinding
import com.example.core.`object`.Booking
import com.example.core.`object`.Room

class AdapterActiveTrip (private var listBooking : List<Booking>)
    : RecyclerView.Adapter<AdapterActiveTrip.Holder> (){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemContainerListActiveTripBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.setData(listBooking[position])
    }

    override fun getItemCount(): Int {
        return listBooking.size
    }
    fun submitListRoom(newList : List<Booking>)
    {
        listBooking = newList
        notifyDataSetChanged()
    }
    inner class Holder (
        private val binding : ItemContainerListActiveTripBinding
    ) : RecyclerView.ViewHolder (binding.root) {
        @SuppressLint("SetTextI18n")
        fun setData(booking: Booking)
        {
            Glide.with(binding.root.context).load(booking.image).into(binding.imageRoom)
            binding.titleRoom.text = booking.title
            binding.textCity.text = booking.city
            binding.textNumberRoom.text = "Số phòng : ${booking.numberRoom}"
            binding.textMoney.text = "Tổng : ${formatMoney(booking.money!!)}đ"
            binding.textPeople.text = " / ${booking.people} người"
        }
        private fun formatMoney (number: Int) : String
        {
            val comma = DecimalFormatSymbols().apply {
                groupingSeparator = ','
            }
            val format = DecimalFormat("#,###", comma)
            return format.format(number)
        }
    }
}