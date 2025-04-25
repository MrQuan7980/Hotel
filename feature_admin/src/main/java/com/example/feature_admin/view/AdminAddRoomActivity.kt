package com.example.feature_admin.view

import android.app.DatePickerDialog
import android.net.Uri
import android.os.Bundle
import android.widget.EditText
import androidx.activity.viewModels
import com.example.feature_admin.R
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.core.local.PerformDataStore
import com.example.core.`object`.Room
import com.example.core.utils.checkIsEmpty
import com.example.core.utils.openIntent
import com.example.core.utils.setMessage
import com.example.feature_admin.activity.AdminMainActivity
import com.example.feature_admin.databinding.ActivityAdminAddRoomBinding
import com.example.feature_admin.viewmodel.AddRoomState
import com.example.feature_admin.viewmodel.AddRoomViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class AdminAddRoomActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminAddRoomBinding
    private var user_id: String = ""
    private var roomName: String = ""
    private var numberRoom: Int = 0
    private var city: String = ""
    private var location: String = ""
    private var imageOne: String = ""
    private var imageTwo: String = ""
    private var imageThree: String = ""
    private var imageFour: String = ""
    private var imageFive: String = ""
    private var date: String = ""
    private var money: Int = 0
    private var people: Int = 0
    private var discount: Float = 0.0F
    private var area: Int = 0
    private val dataStore: PerformDataStore by lazy {
        PerformDataStore(applicationContext)
    }
    private val addRoomViewModel: AddRoomViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminAddRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycleScope.launchWhenStarted {
            dataStore.getString(PerformDataStore.KEY_ID).collect { id ->
                user_id = id
            }
        }
        initVideo()
        handleAddRoomEvent()
        observeAddRoomStatus()
    }
    private fun handleAddRoomEvent() {
        binding.inputDate.setOnClickListener {
            showDateDialog(binding.inputDate)
        }
        binding.btnAdminAddRoom.setOnClickListener {
            if (validateInputDate()) {
                addRoomDatabase()
            }
        }
    }
    private fun addRoomDatabase() {
        val room = Room(
            user_id,
            roomName,
            numberRoom,
            location,
            city,
            area,
            people,
            money,
            discount,
            status = false,
            date,
            imageOne,
            imageTwo,
            imageThree,
            imageFour,
            imageFive,
            isPaid = false
        )
        addRoomViewModel.addRoom(room)
    }
    private fun validateInputDate(): Boolean {
        val title = binding.inputTitle.text.toString().trim()
        val number = binding.inputNumberRoom.text.toString().toIntOrNull()
        val cityInput = binding.inputCity.text.toString().trim()
        val locationInput = binding.inputLocation.text.toString().trim()
        val img1 = binding.imageRoomOne.text.toString().trim()
        val img2 = binding.imageRoomTwo.text.toString().trim()
        val img3 = binding.imageRoomThree.text.toString().trim()
        val img4 = binding.imageRoomFour.text.toString().trim()
        val img5 = binding.imageRoomFive.text.toString().trim()
        val dateStr = binding.inputDate.text.toString().trim()
        val moneyInput = binding.inputMoney.text.toString().toIntOrNull()
        val peopleInput = binding.inputPeople.text.toString().toIntOrNull()
        val discountInput = binding.inputDiscount.text.toString().toFloatOrNull()
        val areaInput = binding.inputArea.text.toString().toIntOrNull()
        val image = listOf(img1, img2, img3, img4, img5)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val currentDate = Calendar.getInstance().time
        val parsedDate = try { dateFormat.parse(dateStr) } catch (e: Exception) { null }
        return when {
            checkIsEmpty(dateStr) -> showMessage(R.string.inputDate)
            checkIsEmpty(title) -> showMessage(R.string.inputTitle)
            number == null || number <= 0 -> showMessage(R.string.inputNumberRoom)
            checkIsEmpty(cityInput) -> showMessage(R.string.inputCity)
            checkIsEmpty(locationInput) -> showMessage(R.string.inputLocation)
            image.any { checkIsEmpty(it) } -> showMessage(R.string.inputImage)
            parsedDate == null || parsedDate.before(currentDate) -> showMessage(R.string.inputDateFormat)
            moneyInput == null || moneyInput <= 0 -> showMessage(R.string.inputMoney)
            peopleInput == null || peopleInput <= 0 -> showMessage(R.string.inputPeople)
            areaInput == null || areaInput <= 0 -> showMessage(R.string.inputArea)
            discountInput == null -> showMessage(R.string.inputDiscount)
            else -> {
                roomName = title
                numberRoom = number
                city = cityInput
                location = locationInput
                imageOne = img1
                imageTwo = img2
                imageThree = img3
                imageFour = img4
                imageFive = img5
                date = dateStr
                money = moneyInput
                people = peopleInput
                discount = discountInput
                area = areaInput
                true
            }
        }
    }
    private fun observeAddRoomStatus()
    {
        addRoomViewModel.status.observe (this) { status ->
            when(status) {
                is AddRoomState.Normal -> {}
                is AddRoomState.Success -> {openIntent<AdminMainActivity>()}
                is AddRoomState.Error -> setMessage(status.message.toString())
            }
        }
    }
    private fun initVideo()
    {
        val videoPath = "android.resource://" + this?.packageName + "/" + R.raw.room
        binding.video.setVideoURI(Uri.parse(videoPath))
        binding.video.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true
            binding.video.start()
        }
    }
    private fun showDateDialog (editText: EditText)
    {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                editText.setText(dateFormat.format(calendar.time))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }
    private fun showMessage(resId: Int): Boolean {
        setMessage(getString(resId))
        return false
    }
}
