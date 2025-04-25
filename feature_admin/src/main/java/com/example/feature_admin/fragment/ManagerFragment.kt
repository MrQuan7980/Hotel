package com.example.feature_admin.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.core.intent.IntentActivity
import com.example.feature_admin.databinding.FragmentManagerBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ManagerFragment : Fragment() {
    private lateinit var binding : FragmentManagerBinding
    @Inject
    lateinit var intentActivity: IntentActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentManagerBinding.inflate(inflater, container, false)

        binding.btnLogOUT.setOnClickListener {
            intentActivity.screenTransition(requireContext())
        }
        return binding.root
    }
}