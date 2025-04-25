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
import com.example.feature_admin.fragment.ManagerFragment
import com.example.feature_admin.fragment.RoomManagerFragment
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
            ButtonItem(binding.buttonFavorite1, R.drawable.ic_hotel, "Đã lưu", 1),
            ButtonItem(binding.buttonReservation1,R.drawable.ic_hotel, "Đặt chỗ", 2),
            ButtonItem(binding.buttonPerson1,R.drawable.ic_hotel, "Người dùng", 3)
        )
        var selectedPosition = 0

        buttonBarItems.forEachIndexed { index, item ->
            setupButtonBarItem(item.binding, item.iconRes, item.text, selectedPosition)
            replaceFragment(RoomManagerFragment())
            item.binding.root.setOnClickListener {
                selectedPosition = index
                buttonBarItems.forEach { otherItem ->
                    setupButtonBarItem(otherItem.binding, otherItem.iconRes, otherItem.text, selectedPosition)
                }
                when (index) {
                    0 -> replaceFragment(RoomManagerFragment())
                    1 -> replaceFragment(ManagerFragment())
                    2 -> replaceFragment(RoomManagerFragment())
                    3 -> replaceFragment(RoomManagerFragment())
                }
            }
        }
    }
    private fun setupButtonBarItem(itemBinding: EditButtonItemBinding, iconRes: Int, text: String, selectedPosition: Int) {
        val position = when (itemBinding) {
            binding.buttonRoomManager -> 0
            binding.buttonFavorite1 -> 1
            binding.buttonReservation1 -> 2
            binding.buttonPerson1 -> 3
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