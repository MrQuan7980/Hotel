package com.example.appbookinghotel.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.appbookinghotel.R
import com.example.appbookinghotel.databinding.FragmentHistoryBinding
import com.example.appbookinghotel.view.history.ActiveTripFragment
import com.example.appbookinghotel.view.history.CancelledTripFragment
import com.example.appbookinghotel.view.history.PastTripFragment
import com.example.core.`object`.NavigationBooking
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : Fragment() {
    private lateinit var binding : FragmentHistoryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)

        setUpBooking()

        replaceFragment(ActiveTripFragment())
        return binding.root
    }
    private fun setUpBooking()
    {
        val bookings = listOf(
            NavigationBooking(binding.btnActiveTrip, ActiveTripFragment(), binding.viewActiveTrip),
            NavigationBooking(binding.btnPastTrip, PastTripFragment(), binding.viewPastTrip),
            NavigationBooking(binding.btnCanceled, CancelledTripFragment(), binding.viewCanceled)
        )

        val allView = bookings.map { it.view }

        bookings.forEach { data ->
            clickFragment(data.btnClick, data.fragment, data.view, allView)
        }

    }
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment, fragment)
        fragmentTransaction.commit()
    }

    private fun clickFragment(btn : TextView, fragment: Fragment, view : ImageView, all : List<ImageView>)
    {
        btn.setOnClickListener {
            replaceFragment(fragment)
            all.forEach { it.visibility = if (view == it) View.VISIBLE else View.GONE}
        }
    }
}