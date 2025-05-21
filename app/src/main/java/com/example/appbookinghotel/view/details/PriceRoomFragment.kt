package com.example.appbookinghotel.view.details

import android.annotation.SuppressLint
import android.content.Intent
import android.icu.text.DecimalFormat
import android.icu.text.DecimalFormatSymbols
import android.view.View
import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.appbookinghotel.databinding.FragmentPriceRoomBinding
import com.example.appbookinghotel.view.room.ConfirmBookingActivity
import com.example.appbookinghotel.viewmodel.room.PriceRoomViewModel
import com.example.appbookinghotel.viewmodel.room.StatusPrice
import com.example.core.local.DataStoreKeys
import com.example.core.`object`.Room
import com.example.core.utils.openIntent
import com.example.core.utils.setMessage
import java.io.Serializable

class PriceRoomFragment : Fragment() {
    private lateinit var binding : FragmentPriceRoomBinding
    private val viewModel : PriceRoomViewModel by viewModels()
    private lateinit var room: Room
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentPriceRoomBinding.inflate(inflater, container, false)
        room = arguments?.getSerializable(DataStoreKeys.KEY_ROOM) as Room
        viewModel.checkDataRoom(room)
        observeListPriceRoom()
        init()
        return binding.root
    }
    private fun init()
    {
        binding.btnBookingRoom.setOnClickListener {
            val intent = Intent(requireContext(), ConfirmBookingActivity::class.java)
            intent.putExtra(DataStoreKeys.KEY_ROOM, room as Serializable)
            startActivity(intent)
        }
    }
    @SuppressLint("SetTextI18n")
    private fun getDataRoom()
    {
        viewModel.room.observe(viewLifecycleOwner) {data ->
            binding.textMoney.text = formatMoney(data.money!!) + " đ"
        }
    }
    private fun formatMoney (number: Int) : String
    {
        val comma = DecimalFormatSymbols().apply {
            groupingSeparator = ','
        }
        val format = DecimalFormat("#,###", comma)
        return format.format(number)
    }
    private fun observeListPriceRoom()
    {
        viewModel.status.observe(viewLifecycleOwner) {status ->
            when(status) {
                is StatusPrice.Normal -> {}
                is StatusPrice.Success -> {
                    getDataRoom()
                }
                is StatusPrice.Error -> {
                    requireContext().setMessage(status.error)
                }
            }
        }
    }
}