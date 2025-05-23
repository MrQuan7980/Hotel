package com.example.core.`object`

import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment

data class NavigationItem(
    val image: ImageView,
    val view: ImageView,
    val room: String?
)

data class NavigationFragment(
    val imageClick : AppCompatButton,
    val fragment : Fragment,
    val view : ImageView
)

data class NavigationBooking (
    val btnClick : TextView,
    val fragment: Fragment,
    val view : ImageView
)