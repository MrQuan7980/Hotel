package com.example.appbookinghotel.fragment

import kotlin.getValue
import android.view.View
import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.example.core.utils.openIntent
import com.example.core.local.PerformDataStore
import com.example.appbookinghotel.view.settings.SettingsActivity
import com.example.appbookinghotel.databinding.FragmentPersonBinding

class PersonFragment : Fragment() {
    private lateinit var binding: FragmentPersonBinding
    private val dataStore : PerformDataStore by lazy {
        PerformDataStore(requireContext())
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPersonBinding.inflate(inflater, container, false)
        eventHandling()
        return binding.root
    }
    private fun eventHandling()
    {
        binding.settingsAccount.setOnClickListener {
            requireContext().openIntent<SettingsActivity>()
        }
    }
}