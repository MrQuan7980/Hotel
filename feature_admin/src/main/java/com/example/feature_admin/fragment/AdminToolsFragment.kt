package com.example.feature_admin.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.core.intent.IntentActivity
import com.example.core.local.PerformDataStore
import com.example.feature_admin.databinding.FragmentAdminToolsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AdminToolsFragment : Fragment() {
    private lateinit var binding : FragmentAdminToolsBinding
    private val dataStore : PerformDataStore by lazy {
        PerformDataStore(requireContext())
    }
    @Inject
    lateinit var intentActivity: IntentActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminToolsBinding.inflate(inflater, container, false)
        
        binding.btnLogOUT.setOnClickListener {
            lifecycleScope.launchWhenStarted {
                dataStore.clear()
                intentActivity.screenTransition(requireContext())
            }
        }
        return binding.root
    }
}