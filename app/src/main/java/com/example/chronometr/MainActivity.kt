package com.example.chronometr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Chronometer
import com.example.chronometr.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
//    lateinit var chronotimer: Chronometer
    var running: Boolean = false
    var offset: Long = 0

    val OFFSET_KEY = "offset"
    val RUNNING_KEY = "running"
    val BASE_KEY = "base"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

//        chronotimer = findViewById(R.id.chronometer)

        if (savedInstanceState != null) {
            offset = savedInstanceState.getLong(OFFSET_KEY)
            running = savedInstanceState.getBoolean(RUNNING_KEY)
            if (running) {
                binding.chronometer.base = savedInstanceState.getLong(BASE_KEY)
                binding.chronometer.start()
            } else setBaseTime()
        }

//        val startButton = findViewById<Button>(R.id.start_button)
        binding.startButton.setOnClickListener {
            if(!running) {
                setBaseTime()
                binding.chronometer.start()
                running = true
            }
        }

//        val stopButton = findViewById<Button>(R.id.stop_button)
        binding.stopButton.setOnClickListener {
            if (running) {
                saveOffset()
                binding.chronometer.stop()
                running = false
            }
        }

//        val restartButton = findViewById<Button>(R.id.restart_button)
        binding.restartButton.setOnClickListener {
            offset = 0
            setBaseTime()
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putLong(OFFSET_KEY, offset)
        savedInstanceState.putBoolean(RUNNING_KEY, running)
        savedInstanceState.putLong(BASE_KEY, binding.chronometer.base)
        super.onSaveInstanceState(savedInstanceState)
    }

    private fun saveOffset() {
        offset = SystemClock.elapsedRealtime() - binding.chronometer.base
    }

    private fun setBaseTime() {
        binding.chronometer.base = SystemClock.elapsedRealtime() - offset
    }
}