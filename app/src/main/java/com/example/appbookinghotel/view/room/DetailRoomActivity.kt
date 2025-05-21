package com.example.appbookinghotel.view.room

import android.view.View
import android.os.Bundle
import com.bumptech.glide.Glide
import android.widget.ImageView
import androidx.activity.viewModels
import com.example.appbookinghotel.R
import androidx.fragment.app.Fragment
import android.icu.text.DecimalFormat
import com.example.core.`object`.Room
import com.example.core.utils.setMessage
import com.example.core.local.DataStoreKeys
import dagger.hilt.android.AndroidEntryPoint
import com.example.core.`object`.NavigationItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.core.`object`.NavigationFragment
import com.example.appbookinghotel.view.details.ReviewFragment
import com.example.appbookinghotel.view.details.PriceRoomFragment
import com.example.appbookinghotel.view.details.IntroduceFragment
import com.example.appbookinghotel.viewmodel.details.StatusDetail
import com.example.appbookinghotel.viewmodel.details.DetailsViewModel
import com.example.appbookinghotel.view.details.CharacteristicsFragment
import com.example.appbookinghotel.databinding.ActivityDetailRoomBinding

@AndroidEntryPoint
class DetailRoomActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailRoomBinding
    private val viewModel : DetailsViewModel by viewModels()
    private var number : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeListRoomStatus()
        checkRoom()
        init()
    }
    private fun init()
    {
        binding.iconBack.setOnClickListener { finish() }
    }
    private fun checkRoom()
    {
        val room =  intent.getSerializableExtra(DataStoreKeys.KEY_ROOM) as Room
        viewModel.receivedDataRoom(room)
    }
    private fun receivedRoom()
    {
        viewModel.room.observe(this) { room ->
            number = room.numberRoom!!
            binding.titleRoom.text = room.title.toString()
            binding.textNumberRoom.text = room.numberRoom.toString()
            binding.textMoney.text = formatMoney(room.money!!)
            binding.textArea.text = room.area.toString()
            binding.textPeople.text = room.maxGuests.toString()
            binding.textDate.text = room.date.toString()
            binding.textAddress.text = room.address.toString()
            Glide.with(this).load(room.imageOne).into(binding.imageRoom)
            Glide.with(this).load(room.imageOne).into(binding.imageOne)
            Glide.with(this).load(room.imageTwo).into(binding.imageTwo)
            Glide.with(this).load(room.imageThree).into(binding.imageThree)
            Glide.with(this).load(room.imageFour).into(binding.imageFour)
            Glide.with(this).load(room.imageFive).into(binding.imageFive)
            setupImageRoom(room)
        }
    }
    private fun setupImageRoom(room : Room)
    {
        val review = ReviewFragment().apply {
            arguments = Bundle().apply { putInt(DataStoreKeys.KEY_ROOM, number) }
        }
        val booking = PriceRoomFragment().apply {
            arguments = Bundle().apply { putSerializable(DataStoreKeys.KEY_ROOM, room) }
        }
        val fragments = listOf(
            NavigationFragment(binding.btnCharacteristics, CharacteristicsFragment(), binding.viewCharacteristics),
            NavigationFragment(binding.btnIntroduce, IntroduceFragment(), binding.viewIntroduce),
            NavigationFragment(binding.btnEvaluation, review, binding.viewEvaluation),
            NavigationFragment(binding.btnBooking, booking, binding.viewBooking),

        )
        val visibility = fragments.map { it.view }
        fragments.forEach { data ->
            clickFragment(data.imageClick, data.view, data.fragment, visibility)
        }

        val images = listOf(
            NavigationItem(binding.imageOne, binding.viewOne, room.imageOne),
            NavigationItem(binding.imageTwo, binding.viewTwo, room.imageTwo),
            NavigationItem(binding.imageThree, binding.viewThree, room.imageThree),
            NavigationItem(binding.imageFour, binding.viewFour, room.imageFour),
            NavigationItem(binding.imageFive, binding.viewFive, room.imageFive)
        )
        val allView = images.map { it.view }
        images.forEach { group ->
            clickImage(group.image, binding.imageRoom, group.room, group.view, allView)
        }
    }
    private fun observeListRoomStatus()
    {
        viewModel.statusDetail.observe(this) {status ->
            when(status) {
                is StatusDetail.Normal -> {}
                is StatusDetail.Success -> {
                    receivedRoom()
                }
                is StatusDetail.Error -> {
                    setMessage(status.error)
                }
            }
        }
    }
    private fun formatMoney (number: Int) : String {
        val format = DecimalFormat("#,###")
        return format.format(number)
    }
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayoutDetail, fragment)
        fragmentTransaction.commit()
    }
    private fun clickImage(imageClick: ImageView, imageShow: ImageView, imageRoom: String?, viewShow : ImageView, allView : List<ImageView>)
    {
        imageClick.setOnClickListener {
            Glide.with(this).load(imageRoom).into(imageShow)
            allView.forEach { it.visibility = if (it == viewShow) View.VISIBLE else View.GONE }
        }
    }
    private fun clickFragment(btnClick : AppCompatButton, viewShow : ImageView, fragment : Fragment, viewGone : List<ImageView>)
    {
        btnClick.setOnClickListener {
            replaceFragment(fragment)
            viewGone.forEach { it.visibility = if (it == viewShow) View.VISIBLE else View.GONE }
        }
    }
}