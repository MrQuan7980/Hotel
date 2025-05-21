package com.example.appbookinghotel.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.appbookinghotel.R
import com.example.appbookinghotel.databinding.ActivityMainBinding
import com.example.appbookinghotel.fragment.BookingFragment
import com.example.appbookinghotel.fragment.PersonFragment
import com.example.appbookinghotel.fragment.HistoryFragment
import com.example.appbookinghotel.fragment.ServiceFragment
import com.example.core.databinding.EditButtonItemBinding
import com.example.core.`object`.ButtonItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initButtonMenuBar()
    }
    private fun initButtonMenuBar() {
        val buttonBarItems = listOf(
            ButtonItem(binding.buttonHome, com.example.core.R.drawable.ic_bed, "Đặt phòng", 0),
            ButtonItem(binding.buttonFavorite, com.example.core.R.drawable.ic_plane, "Dịch vụ", 1),
            ButtonItem(binding.buttonReservation, com.example.core.R.drawable.ic_clock, "Lịch sử", 2),
            ButtonItem(binding.buttonPerson, com.example.core.R.drawable.ic_person, "Người dùng", 3)
        )
        var selectedPosition = 0

        buttonBarItems.forEachIndexed { index, item ->
            setupButtonBarItem(item.binding, item.iconRes, item.text, selectedPosition)
            replaceFragment(BookingFragment())
            item.binding.root.setOnClickListener {
                selectedPosition = index
                buttonBarItems.forEach { otherItem ->
                    setupButtonBarItem(otherItem.binding, otherItem.iconRes, otherItem.text, selectedPosition)
                }
                when (index) {
                    0 -> replaceFragment(BookingFragment())
                    1 -> replaceFragment(ServiceFragment())
                    2 -> replaceFragment(HistoryFragment())
                    3 -> replaceFragment(PersonFragment())
                }
            }
        }
    }
    private fun setupButtonBarItem(itemBinding: EditButtonItemBinding, iconRes: Int, text: String, selectedPosition: Int) {
        val position = when (itemBinding) {
            binding.buttonHome -> 0
            binding.buttonFavorite -> 1
            binding.buttonReservation -> 2
            binding.buttonPerson -> 3
            else -> -1
        }
        val isSelected = position == selectedPosition
        val colors = intArrayOf(
            ContextCompat.getColor(this, R.color.grey),
            ContextCompat.getColor(this, R.color.blue)
        )

        itemBinding.apply {
            icon.setImageResource(iconRes)
            icon.setColorFilter(colors[if (isSelected) 1 else 0])
            title.text = text
            title.setTextColor(colors[if (isSelected) 1 else 0])
            indicator.visibility = if (isSelected) View.VISIBLE else View.INVISIBLE
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.frameLayoutMainUser, fragment)
        fragmentTransaction.commit()
    }
}