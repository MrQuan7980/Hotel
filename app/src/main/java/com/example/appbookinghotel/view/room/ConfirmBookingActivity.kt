package com.example.appbookinghotel.view.room

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.icu.text.DecimalFormat
import android.icu.text.DecimalFormatSymbols
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.appbookinghotel.activity.MainActivity
import com.example.appbookinghotel.databinding.ActivityConfirmBookingBinding
import com.example.appbookinghotel.viewmodel.room.ConfirmBookingViewModel
import com.example.appbookinghotel.viewmodel.room.StatusConfirm
import com.example.core.local.DataStoreKeys
import com.example.core.local.PerformDataStore
import com.example.core.`object`.Booking
import com.example.core.`object`.Room
import com.example.core.utils.openIntent
import com.example.core.utils.setMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.UUID
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class ConfirmBookingActivity : AppCompatActivity() {
    private lateinit var binding : ActivityConfirmBookingBinding
    private val viewModel : ConfirmBookingViewModel by viewModels()
    private var result : Int = 0
    private var quantity : Int = 1
    private var people : Int = 0
    private var money : Int = 0
    private var adminId : String = ""
    private var city : String = ""
    private var sumMoney : Int = 0
    private var titleRoom : String = ""
    private var numberRoom : Int = 0
    private var image : String = ""
    private val dataStore : PerformDataStore by lazy {
        PerformDataStore(applicationContext)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val room = intent.getSerializableExtra(DataStoreKeys.KEY_ROOM) as? Room
        viewModel.checkRoom(room)
        observeConfirmBooking()
        eventHandling()
        getDataRoom()
        sumQuantity()
        setUpWatcher()
    }
    private fun eventHandling()
    {
        binding.iconBack.setOnClickListener { finish() }
        binding.btnBookingRoom.setOnClickListener {
            if (validate())
            {
                insertBooking()
            }
        }
        binding.dateCheckIn.setOnClickListener { showDate(binding.dateCheckIn) }
        binding.dateCheckOut.setOnClickListener { showDate(binding.dateCheckOut) }

    }
    private fun insertBooking()
    {
        try {
            lifecycleScope.launchWhenStarted {
                val id = UUID.randomUUID().toString()
                val userId = dataStore.getString(PerformDataStore.KEY_ID).first()
                val address = binding.textAddress.text.toString()
                val dateCheckIn = binding.dateCheckIn.text.toString()
                val dateCheckOut = binding.dateCheckOut.text.toString()
                val note = binding.textNote.text.toString()

                val booking = Booking(
                    adminId, id,
                    userId,
                    titleRoom,
                    numberRoom,
                    address,
                    city,
                    quantity,
                    image,
                    sumMoney,
                    dateCheckIn,
                    dateCheckOut,
                    note,
                    isCheckedIn = false,
                    isCheckedOut = false
                )
                viewModel.insertBooking(booking)
            }
        }catch (e : Exception)
        {
            Log.e("ERROR_BOOKING", "Exception: ", e)
        }
    }
    private fun getDataRoom()
    {
        viewModel.room.observe(this) { data ->
            lifecycleScope.launchWhenStarted {
                binding.textName.text = dataStore.getString(PerformDataStore.KEY_NAME).first()
                binding.textPhone.text = dataStore.getString(PerformDataStore.KEY_PHONE).first()
                binding.textAddress.text = data?.address
                binding.titleRoom.text = data?.title
                data?.maxGuests?.let { people = it }
                data?.money?.let { money = it }
                titleRoom = data?.title.orEmpty()
                numberRoom = data?.numberRoom ?: 0
                city = data?.city.orEmpty()
                adminId = data?.admin_id.toString()
                image = data?.imageOne.toString()
            }
        }
    }
    private fun sumQuantity()
    {
        updateQuantity()
        binding.buttonMinus.setOnClickListener {
            if (quantity > 1)
            {
                quantity--
                updateQuantity()
            }
            else
            {
                setMessage("Không được thấp hơn 1")
            }
        }
        binding.buttonPlus.setOnClickListener {
            quantity++
            updateQuantity()
        }
    }
    private fun updateQuantity()
    {
        binding.textQuantity.text = quantity.toString()
        totalAmount()
    }

    private fun validate(): Boolean {
        val checkInStr = binding.dateCheckIn.text.toString().trim()
        val checkOutStr = binding.dateCheckOut.text.toString().trim()
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val today = calendar.time
        val checkInDate = try { dateFormat.parse(checkInStr) } catch (e: Exception) { null }
        val checkOutDate = try { dateFormat.parse(checkOutStr) } catch (e: Exception) { null }

        if (checkInDate == null || checkOutDate == null)
        {
            setMessage("Vui lòng chọn vào ngày nhận và ngày trả")
            return false
        }
        else if (checkInDate.before(today))
        {
            setMessage("Ngày nhận phòng không được trước hôm nay")
            return false
        }
        else if (!checkOutDate.after(checkInDate))
        {
            setMessage("Ngày trả phòng phải sau ngày nhận phòng")
            return false
        }
        else if (quantity > people)
        {
            setMessage("Quá số lượng người trong phòng")
            return false
        }

        val diffMillis = checkOutDate.time - checkInDate.time
        result = TimeUnit.MILLISECONDS.toDays(diffMillis).toInt()

        return true
    }
    private fun setUpWatcher()
    {
        binding.dateCheckIn.addTextChangedListener(moneyWatcher)
        binding.dateCheckOut.addTextChangedListener(moneyWatcher)
        binding.children.setOnCheckedChangeListener { _, _ ->
            totalAmount()
        }
    }
    private val moneyWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
        override fun afterTextChanged(p0: Editable?) {
            totalAmount()
        }
    }
    @SuppressLint("SetTextI18n")
    private fun totalAmount()
    {
        if (validate())
        {
            sumMoney = result * money
            if (binding.children.isChecked)
            {
                sumMoney = (sumMoney * 0.9).toInt()
            }
            binding.textMoney.text = "${formatMoney(sumMoney)} đ"
        }
    }
    private fun formatMoney (number: Int) : String
    {
        val comma = DecimalFormatSymbols().apply {
            groupingSeparator = ','
        }
        val format = DecimalFormat("#,###", comma)
        return format.format(number)
    }
    private fun showDate (editText: EditText)
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
    private fun observeConfirmBooking()
    {
        viewModel.status.observe(this) { status ->
            when(status) {
                is StatusConfirm.Normal -> {}
                is StatusConfirm.Success -> {
                }
                is StatusConfirm.BookingSuccess -> {
                    viewModel.updateStatusRoom(numberRoom)
                    openIntent<MainActivity>()
                }
                is StatusConfirm.UpdateSuccess -> {
                    setMessage("Đặt phòng thành công")
                }
                is StatusConfirm.Error -> {
                    setMessage(status.message)
                }
            }
        }
    }
}