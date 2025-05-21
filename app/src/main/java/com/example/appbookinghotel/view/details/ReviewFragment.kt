package com.example.appbookinghotel.view.details

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appbookinghotel.R
import com.example.appbookinghotel.adapter.AdapterReview
import com.example.appbookinghotel.callback.ReviewClickListener
import com.example.appbookinghotel.databinding.FragmentReviewBinding
import com.example.appbookinghotel.viewmodel.details.ReviewRoomViewModel
import com.example.appbookinghotel.viewmodel.details.StatusReview
import com.example.core.local.DataStoreKeys
import com.example.core.local.PerformDataStore
import com.example.core.`object`.Review
import com.example.core.utils.checkIsEmpty
import com.example.core.utils.formatDate
import com.example.core.utils.randomId
import com.example.core.utils.setMessage
import com.example.core.utils.showViewSuccess
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first

@AndroidEntryPoint
class ReviewFragment : Fragment(), ReviewClickListener{
    private lateinit var binding : FragmentReviewBinding
    private val viewModel : ReviewRoomViewModel by activityViewModels()
    private lateinit var adapterReview : AdapterReview
    private var rating : Int = 0
    private var input : String = ""
    private var userId : String = ""
    private val dataStore : PerformDataStore by lazy {
        PerformDataStore(requireContext())
    }
    private var id = randomId()
    private var numberRoom : Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReviewBinding.inflate(inflater, container, false)
        numberRoom = arguments?.getInt(DataStoreKeys.KEY_ROOM) ?: -1
        observeListReviewStatus()
        loadRecyclerView()
        dataReviewChange()
        init()
        return binding.root
    }
    private fun init()
    {
        binding.btnReviews.setOnClickListener {
            if (validateReview())
            {
                lifecycleScope.launchWhenStarted {
                    userId = dataStore.getString(PerformDataStore.KEY_ID).first()
                    val userName = dataStore.getString(PerformDataStore.KEY_NAME).first()
                    val date = requireContext().formatDate()
                    val review = Review(id, numberRoom, userId, userName, date, input, rating)

                    viewModel.postReview(review)
                }
            }
        }
    }
    private fun validateReview() : Boolean
    {
        rating = binding.rating.rating.toInt()
        input = binding.inputContentReview.text.toString()
        return when {
            requireContext().checkIsEmpty(input) -> {
                requireContext().setMessage(getString(R.string.inputReview))
                return false
            }
            rating <= 1 -> {
                requireContext().setMessage(getString(R.string.textReview))
                return false
            }
            else -> true
        }
    }
    private fun dataReviewChange()
    {
        viewModel.review.observe(viewLifecycleOwner) { review ->
            adapterReview.dataChange(review)
        }
    }
    private fun loadRecyclerView()
    {
        lifecycleScope.launchWhenStarted {
            val id = dataStore.getString(PerformDataStore.KEY_ID).first()
            adapterReview = AdapterReview(ArrayList(), this@ReviewFragment, id, requireContext())
            binding.listEvaluation.layoutManager = LinearLayoutManager(context)
            binding.listEvaluation.adapter = adapterReview
            viewModel.getListReview(numberRoom)
        }
    }
    private fun observeListReviewStatus()
    {
        viewModel.status.observe(viewLifecycleOwner){ status ->
            when (status)
            {
                is StatusReview.Normal -> {}
                is StatusReview.Success -> success()
                is StatusReview.PostSuccess -> {
                    binding.rating.rating = 0F
                    binding.inputContentReview.setText("")
                }
                is StatusReview.Update -> {
                    requireContext().setMessage("Xóa thành công")
                    adapterReview.dataChange(status.data)
                }
                is StatusReview.Error -> {
                    requireContext().setMessage(status.error)
                }
            }
        }
    }
    override fun onClickDelete(id: String) {
        viewModel.deleteReview(id)
    }
    fun success()
    {
        requireContext().showViewSuccess(binding.listEvaluation, binding.progressEvaluation, true)
    }
}
