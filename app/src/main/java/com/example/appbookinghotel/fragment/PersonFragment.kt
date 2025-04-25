package com.example.appbookinghotel.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.appbookinghotel.databinding.FragmentPersonBinding
import com.example.appbookinghotel.view.SignInActivity
import com.example.core.local.PerformDataStore
import kotlin.getValue

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

        lifecycleScope.launchWhenStarted {
            dataStore.getString(PerformDataStore.KEY_NAME).collect { name ->
                binding.textViewName.text = name
            }
        }
        eventHandling()
        return binding.root
    }
    private fun eventHandling()
    {
        binding.buttonLogOut.setOnClickListener {
            lifecycleScope.launchWhenStarted {
                dataStore.clear()

                val intent = Intent(requireContext(), SignInActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            }
        }
    }
}