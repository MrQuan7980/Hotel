package com.example.appbookinghotel.adapter

import android.annotation.SuppressLint
import android.icu.text.DecimalFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appbookinghotel.callback.RoomClickListener
import com.example.appbookinghotel.databinding.ItemContainerListRoomUserBinding
import com.example.appbookinghotel.databinding.ItemRoomBinding
import com.example.core.`object`.Room
import com.example.feature_admin.R

class AdapterListRoomUser (private var listRoom : List<Room>, private val onClick : RoomClickListener) :
    RecyclerView.Adapter<AdapterListRoomUser.ListRoomHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListRoomHolder {
        val binding = ItemRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListRoomHolder(binding)
    }

    override fun getItemCount(): Int {
        return listRoom.size
    }

    override fun onBindViewHolder(holder: ListRoomHolder, position: Int) {
        holder.setData(listRoom[position])

        val room = listRoom[position]
        holder.itemView.setOnClickListener {
            onClick.onRoomClick(room)
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun submitListRoom(newListRoom : List<Room>)
    {
        listRoom = newListRoom
        notifyDataSetChanged()
    }
    inner class ListRoomHolder (private val binding : ItemRoomBinding) : RecyclerView.ViewHolder (binding.root) {
        fun setData (room: Room)
        {
            val money = formatMoney(room.money!!)
            binding.titleRoom.text = room.title
            Glide.with(binding.root.context)
                .load(room.imageOne)
                .error(R.drawable.error)
                .into(binding.pictureUrl)
            binding.textMoney.text = money
            binding.textMaxPeople.text = room.maxGuests.toString()
            binding.textCity.text = room.city
        }
        fun formatMoney (number: Int) : String {
            val format = DecimalFormat("#,###")
            return format.format(number)
        }

        //    inner class RoomViewHolder (
//        private val binding : ItemContainerListRoomUserBinding
//    ) : RecyclerView.ViewHolder(binding.root)
//    {
//        @SuppressLint("SetTextI18n")
//        fun setData (room: Room)
//        {
//            val money = formatMoney(room.money!!)
//            binding.titleRoom.text = room.title
//            Glide.with(binding.root.context)
//                .load(room.imageOne)
//                .error(R.drawable.error)
//                .into(binding.picUrl)
//            binding.textMoney.text = money
//            binding.people.text = room.maxGuests.toString()
//            binding.textArea.text = room.area?.toInt().toString()
//        }
//        fun formatMoney (number: Int) : String {
//            val format = DecimalFormat("#,###")
//            return format.format(number)
//        }
//    }
        @SuppressLint("NotifyDataSetChanged")
        fun submitList(newListRoom : List<Room>)
        {
            listRoom = newListRoom
            notifyDataSetChanged()
        }
    }
}