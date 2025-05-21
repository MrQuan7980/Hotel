package com.example.appbookinghotel.fragment

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appbookinghotel.R
import com.example.appbookinghotel.databinding.FragmentCarBinding
import com.example.appbookinghotel.databinding.FragmentPlaneBinding
import com.example.appbookinghotel.databinding.FragmentServiceBinding
import com.example.appbookinghotel.view.service.CarFragment
import com.example.appbookinghotel.view.service.PlaneFragment

class ServiceFragment : Fragment() {
    private lateinit var binding : FragmentServiceBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentServiceBinding.inflate(inflater, container, false)

        initVideo()
        setUpFragment()
        replaceFragment(PlaneFragment())

        return binding.root
    }
    private fun initVideo()
    {
        val videoPath = "android.resource://" + context?.packageName + "/" + R.raw.plane
        binding.videoIntro.setVideoURI(Uri.parse(videoPath))
        binding.videoIntro.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true
            binding.videoIntro.start()
        }
    }
    private fun setUpFragment() {
        binding.btnPlane.setOnClickListener {
            replaceFragment(PlaneFragment())
            binding.framePlane.visibility = View.VISIBLE
            binding.frameCar.visibility = View.GONE
        }
        binding.btnCar.setOnClickListener {
            replaceFragment(CarFragment())
            binding.framePlane.visibility = View.GONE
            binding.frameCar.visibility = View.VISIBLE
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayoutDetail, fragment)
        fragmentTransaction.commit()
    }

}