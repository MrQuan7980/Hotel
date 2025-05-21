package com.example.appbookinghotel.view.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.appbookinghotel.databinding.FragmentIntroduceBinding
import com.example.appbookinghotel.viewmodel.details.IntroduceViewModel
import com.example.appbookinghotel.viewmodel.details.StatusDetail
import com.example.appbookinghotel.viewmodel.details.StatusIntroduce
import com.example.core.utils.setMessage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroduceFragment : Fragment() {
    private lateinit var binding : FragmentIntroduceBinding
    private val viewModel : IntroduceViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIntroduceBinding.inflate(inflater, container, false)

        observeIntroduceStatus()
        viewModel.getIntroduce()
        getIntroduce()
        return binding.root
    }
    private fun getIntroduce()
    {
        viewModel.introduce.observe(viewLifecycleOwner) { data ->
            Glide.with(this).load(data.imageOne).into(binding.imageIntroduceOne)
            Glide.with(this).load(data.imageTwo).into(binding.imageIntroduceTwo)
            Glide.with(this).load(data.imageThree).into(binding.imageIntroduceThree)
            Glide.with(this).load(data.imageFour).into(binding.imageIntroduceFour)
            Glide.with(this).load(data.imageFive).into(binding.imageIntroduceFive)

            binding.textIntroduceOne.text = data.textOne
            binding.textIntroduceTwo.text = data.textTwo
            binding.textIntroduceThree.text = data.textThree
            binding.textIntroduceFour.text = data.textFour
            binding.textIntroduceFive.text = data.textFive
        }
    }
    private fun observeIntroduceStatus()
    {
        viewModel.status.observe(viewLifecycleOwner) {status ->
            when(status) {
                is StatusIntroduce.Normal -> {}
                is StatusIntroduce.Success -> {

                }
                is StatusIntroduce.Error -> {
                    requireContext().setMessage(status.error)
                }
            }
        }
    }
}