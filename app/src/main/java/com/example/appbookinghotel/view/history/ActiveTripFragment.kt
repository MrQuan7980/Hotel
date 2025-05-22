package com.example.appbookinghotel.view.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appbookinghotel.adapter.AdapterActiveTrip
import com.example.appbookinghotel.databinding.FragmentActiveTripBinding
import com.example.appbookinghotel.viewmodel.history.ActiveTripViewModel
import com.example.appbookinghotel.viewmodel.history.StatusBooking
import com.example.core.local.PerformDataStore
import com.example.core.`object`.Booking
import com.example.core.utils.setMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first

@AndroidEntryPoint
class ActiveTripFragment : Fragment() {
    private lateinit var binding : FragmentActiveTripBinding
    private lateinit var adapter : AdapterActiveTrip
    private val viewModel : ActiveTripViewModel by activityViewModels()
    private val dataStore : PerformDataStore by lazy {
        PerformDataStore(requireContext())
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentActiveTripBinding.inflate(inflater, container,false)
        getListBooking()
        observeListBooking()
        setUpRecyclerView()
        viewModel.getListBooking()
        return binding.root
    }

    private fun setUpRecyclerView()
    {
        adapter = AdapterActiveTrip(ArrayList())
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
    }
    private fun getListBooking()
    {

        viewModel.list.observe(viewLifecycleOwner) { data ->
            lifecycleScope.launchWhenStarted {
                val id = dataStore.getString(PerformDataStore.KEY_ID).first()

                val listBooking = data.filter { booking ->
                    booking.userId == id
                }
                if (listBooking.isEmpty())
                {
                    requireContext().setMessage("Không có phòng nào gần đây")
                    binding.progress.visibility = View.VISIBLE
                }
                else
                {
                    binding.progress.visibility = View.GONE
                    adapter.submitListRoom(listBooking)
                }
            }
        }
    }
    private fun observeListBooking()
    {
        viewModel.status.observe(viewLifecycleOwner) {status ->
            when(status)
            {
                is StatusBooking.Normal -> {}
                is StatusBooking.Success -> {
                    binding.progress.visibility = View.GONE
                }
                is StatusBooking.Error -> {

                }
            }
        }
    }
}