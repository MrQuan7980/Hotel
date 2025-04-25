package com.example.feature_admin.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.feature_admin.R
import com.example.core.`object`.Room
import com.example.feature_admin.databinding.ItemContainerListRoomBinding
import java.text.DecimalFormat

class AdapterListRoom (private var listRoom: List<Room>) :
    RecyclerView.Adapter<AdapterListRoom.RoomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val binding = ItemContainerListRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false);
        return RoomViewHolder(binding)
    }
    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        holder.setData(listRoom[position])
    }
    override fun getItemCount(): Int {
        return listRoom.size
    }
    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newListRoom : List<Room>)
    {
        listRoom = newListRoom
        notifyDataSetChanged()
    }
    inner class RoomViewHolder (
        private val binding: ItemContainerListRoomBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun setData(room: Room)
        {
            binding.titleRoom.text = room.title
            Glide.with(binding.root.context)
                .load(room.imageOne)
                .error(R.drawable.error)
                .into(binding.pictureUrl)
            val format = formatWithMoney(room.money!!)
            binding.textMoney.text = format
            binding.textCity.text = room.city
            binding.textMaxPeople.text = room.maxGuests?.toInt().toString()
        }
        fun formatWithMoney(number : Int) : String {
            val formatter = DecimalFormat("#,###")
            return formatter.format(number)
        }
    }
}