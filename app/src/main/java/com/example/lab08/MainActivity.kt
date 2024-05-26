package com.example.lab08

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.lab08.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    val pizzaSizes = arrayOf("Please Select Size","Small",
        "Medium","Large","Extra Larga")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.scheduleBtn.setOnClickListener {

            //create a new intent (operasi buka page baru)
            //explicite intent dari main_activity ke thanksActivity
            var intent = Intent( this, ThanksActivity::class.java)

            //nak pass data ke page baru
            intent.putExtra( "name",binding.nameEditText.text.toString())
            intent.putExtra("phone",binding.phoneEditText.text.toString())
            intent.putExtra("size",binding.sizeTextView.text.toString())
            intent.putExtra("date",binding.dateTextView.text.toString())
            intent.putExtra("time",binding.timeTextView.text.toString())

            startActivity(intent)
        }
        binding.sizeSeekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {

            //bila keebar bergerak/ nilai berubah
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.sizeTextView.text = pizzaSizes[progress]
            }
            
            //seekbar mula ditekan
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                binding.sizeTextView.text = "Seeker mula ditekan"
            }
            
             //bila seekbar habis ditekan (padam TODO kalau tidak perlukan apa-apa
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                
            }

        })

        binding.dateBtn.setOnClickListener {
            //get current date
            val c = Calendar.getInstance()
            val day = c.get(Calendar.DAY_OF_MONTH)
            val month = c.get(Calendar.MONTH)
            val year = c.get(Calendar.YEAR)

            //kod untuk mmbina dialog
            //DatePicker ada 6 argument
            //1) di mana dia akan keluar(this) => ia akan keluar di MainActivity
            //2)Design / style datePicker => Default style yg dipanggil theme overlay
            //3) Listener => setelah tarikh dipilih what should be execute
            //4) Default year
            //5) default month
            //6) Default date
            val myDatePicker =
                DatePickerDialog(this,
                    android.R.style.ThemeOverlay,
                    DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                        binding.dateTextView.text = "$dayOfMonth/${month+1}/$year"
                    },
                    year,
                    month,
                    day
                )
            myDatePicker.show()
        }

        binding.timeBtn.setOnClickListener {

            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minutes = c.get(Calendar.MINUTE)

           //membina timePicker
            //1) where the timepicker is build? this -> inside MainActivity
            //2)Listener -> when the time is selected execute the code
            //3)default hour
            //4)default minutes
            //5) 24 hours or 12 hours
            val myTimePicker = TimePickerDialog(
                this,
                TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    val hourFormatted = String.format("%02d",hourOfDay)
                    val minuteFormat = String.format("02d",minute)
                    binding.timeTextView.text = "$hourFormatted:$minuteFormat";
                },
                hour,
                minutes,
                true

            )
            myTimePicker.show()
        }
        }
    }
