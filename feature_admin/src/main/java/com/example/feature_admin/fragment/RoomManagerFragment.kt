package com.example.feature_admin.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.utils.openIntent
import com.example.core.utils.setMessage
import com.example.core.utils.showView
import com.example.feature_admin.adapter.AdapterListRoom
import com.example.feature_admin.databinding.FragmentAdminRoomManagerBinding
import com.example.feature_admin.view.AdminAddRoomActivity
import com.example.feature_admin.viewmodel.ListRoomStatus
import com.example.feature_admin.viewmodel.RoomListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoomManagerFragment : Fragment() {
    private lateinit var binding: FragmentAdminRoomManagerBinding
    private lateinit var adapterListRoom: AdapterListRoom
    private val viewModelRoom : RoomListViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminRoomManagerBinding.inflate(inflater, container, false)
        eventHandling()
        loadRoomList()
        changeList()
        checkRenderAPI()
        return binding.root
    }
    private fun eventHandling()
    {
        binding.buttonAdd.setOnClickListener {
            requireContext().openIntent<AdminAddRoomActivity>()
        }
    }
    private fun checkRenderAPI()
    {
        if (viewModelRoom.room.value.isNullOrEmpty())
        {
            viewModelRoom.showListRoom()
        }
        else
        {
            viewModelRoom.checkListData()
        }
        observeListRoomStatus()
    }
    private fun loadRoomList()
    {
        adapterListRoom = AdapterListRoom(ArrayList())
        binding.recyclerViewListRoom.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewListRoom.adapter = adapterListRoom
    }
    private fun changeList()
    {
        viewModelRoom.room.observe(viewLifecycleOwner   ) { rooms ->
            adapterListRoom.submitList(rooms)
        }
    }
    private fun observeListRoomStatus()
    {
        viewModelRoom.status.observe(viewLifecycleOwner) { status ->
            when (status) {
                is ListRoomStatus.Normal -> {}
                is ListRoomStatus.Success -> {
                    uIVisibility(false, false)
                }
                is ListRoomStatus.Data -> {
                    adapterListRoom.submitList(status.data)
                }
                is ListRoomStatus.Error -> {
                    requireContext().setMessage(status.message)
                    uIVisibility(false, true)
                }
            }
        }
    }

    private fun uIVisibility(isLoading : Boolean, isEmpty: Boolean)
    {
        requireContext().showView(binding.recyclerViewListRoom, if (isEmpty) View.GONE else View.VISIBLE)
        requireContext().showView(binding.noData, if (isEmpty) View.VISIBLE else View.GONE)
        requireContext().showView(binding.progressHotel, if (isLoading) View.VISIBLE else View.GONE)
    }
}