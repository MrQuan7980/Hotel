package com.example.feature_admin.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.core.databinding.EditButtonItemBinding
import com.example.core.`object`.ButtonItem
import com.example.feature_admin.R
import com.example.feature_admin.databinding.ActivityAdminMainBinding
import com.example.feature_admin.fragment.AdminToolsFragment
import com.example.feature_admin.fragment.BookingManagementFragment
import com.example.feature_admin.fragment.RoomManagementFragment
import com.example.feature_admin.fragment.ServiceManagementFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initButtonMenuBar()

    }
    private fun initButtonMenuBar() {
        val buttonBarItems = listOf(
            ButtonItem(binding.buttonRoomManager,R.drawable.ic_hotel, "Quản lí phòng", 0),
            ButtonItem(binding.buttonService, com.example.core.R.drawable.ic_plane, "Dịch vụ", 1),
            ButtonItem(binding.buttonBookingManager, R.drawable.ic_booking_admin, "Đặt chỗ", 2),
            ButtonItem(binding.buttonGeneralManagement, R.drawable.ic_setting_admin, "Quản lí chung", 3)
        )
        var selectedPosition = 0

        buttonBarItems.forEachIndexed { index, item ->
            setupButtonBarItem(item.binding, item.iconRes, item.text, selectedPosition)
            replaceFragment(RoomManagementFragment())
            item.binding.root.setOnClickListener {
                selectedPosition = index
                buttonBarItems.forEach { otherItem ->
                    setupButtonBarItem(otherItem.binding, otherItem.iconRes, otherItem.text, selectedPosition)
                }
                when (index) {
                    0 -> replaceFragment(RoomManagementFragment())
                    1 -> replaceFragment(ServiceManagementFragment())
                    2 -> replaceFragment(BookingManagementFragment())
                    3 -> replaceFragment(AdminToolsFragment())
                }
            }
        }
    }
    private fun setupButtonBarItem(itemBinding: EditButtonItemBinding, iconRes: Int, text: String, selectedPosition: Int) {
        val position = when (itemBinding) {
            binding.buttonRoomManager -> 0
            binding.buttonService -> 1
            binding.buttonBookingManager -> 2
            binding.buttonGeneralManagement -> 3
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

        fragmentTransaction.replace(R.id.frameLayoutMain, fragment)
        fragmentTransaction.commit()
    }
}