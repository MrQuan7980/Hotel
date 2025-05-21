package com.example.appbookinghotel.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appbookinghotel.R
import com.example.appbookinghotel.adapter.AdapterListRoomUser
import com.example.appbookinghotel.callback.RoomClickListener
import com.example.appbookinghotel.databinding.FragmentBookingBinding
import com.example.appbookinghotel.view.room.DetailRoomActivity
import com.example.appbookinghotel.viewmodel.room.StatusListRoom
import com.example.appbookinghotel.viewmodel.room.UserRoomViewModel
import com.example.core.local.DataStoreKeys
import com.example.core.local.PerformDataStore
import com.example.core.`object`.Room
import com.example.core.utils.setMessage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookingFragment : Fragment(), RoomClickListener{
    private lateinit var binding : FragmentBookingBinding
    private lateinit var adapterListRoomUser: AdapterListRoomUser
    private val dataStore : PerformDataStore by lazy {
        PerformDataStore(requireContext())
    }
    private val viewModel : UserRoomViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookingBinding.inflate(inflater, container,false);
        initVideo()
        settingAdapter()
        observeListRoomStatus()
        loadListRoom()
        return binding.root
    }
    private fun initVideo()
    {
        val videoPath = "android.resource://" + context?.packageName + "/" + R.raw.video
        binding.videoIntro.setVideoURI(Uri.parse(videoPath))
        binding.videoIntro.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true
            binding.videoIntro.start()
        }
    }
    private fun settingAdapter()
    {
        adapterListRoomUser = AdapterListRoomUser(ArrayList(), this)
        binding.recyclerViewListHotel.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewListHotel.adapter = adapterListRoomUser
    }
    private fun loadListRoom()
    {
        viewModel.showListRoom()
        viewModel.room.observe(viewLifecycleOwner) { room ->
            adapterListRoomUser.submitListRoom(room)
        }
    }
    private fun observeListRoomStatus()
    {
        viewModel.status.observe(viewLifecycleOwner) { status ->
            when(status) {
                is StatusListRoom.Normal -> {
                    viewVisibility(false, false)
                }
                is StatusListRoom.Success -> {
                    viewVisibility(true, false)
                }
                is StatusListRoom.Error -> {
                    requireContext().setMessage(status.message)
                    viewVisibility(false, true)
                }
            }
        }
    }
    private fun viewVisibility(
        isData: Boolean,
        isText: Boolean
    )
    {
        binding.recyclerViewListHotel.visibility = if (isData) View.VISIBLE else View.GONE
        binding.progressHotel.visibility = if (isData) View.GONE else View.VISIBLE
        binding.noData.visibility = if (isText) View.VISIBLE else View.GONE
    }

    override fun onRoomClick(room: Room) {
        val intent = Intent(requireContext(), DetailRoomActivity::class.java)
        intent.putExtra(DataStoreKeys.KEY_ROOM, room)
        startActivity(intent)
    }
}