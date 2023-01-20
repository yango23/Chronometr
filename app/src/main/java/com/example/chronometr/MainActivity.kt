package com.example.chronometr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer
import java.time.temporal.ChronoField

class MainActivity : AppCompatActivity() {

    lateinit var chronotimer: Chronometer
    var running: Boolean = false
    var offset: Long = 0

    val OFFSET_KEY = "offset"
    val RUNNING_KEY = "running"
    val BASE_KEY = "base"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        chronotimer = findViewById(R.id.starttimer)

        if (savedInstanceState != null) {
            offset = savedInstanceState.getLong(OFFSET_KEY)
            running = savedInstanceState.getBoolean(RUNNING_KEY)
            if (running) {
                chronotimer.base = savedInstanceState.getLong(BASE_KEY)
                chronotimer.start()
            } else setBaseTime()
        }

        val startButton = findViewById<Button>(R.id.start_button)
        startButton.setOnClickListener {
            if(!running) {
                setBaseTime()
                chronotimer.start()
                running = true
            }
        }

        val stopButton = findViewById<Button>(R.id.stop_button)
        stopButton.setOnClickListener {
            if (running) {
                saveOffset()
                chronotimer.stop()
                running = false
            }
        }

        val restartButton = findViewById<Button>(R.id.restart_button)
        restartButton.setOnClickListener {
            offset = 0
            setBaseTime()
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putLong(OFFSET_KEY, offset)
        savedInstanceState.putBoolean(RUNNING_KEY, running)
        savedInstanceState.putLong(BASE_KEY, chronotimer.base)
        super.onSaveInstanceState(savedInstanceState)
    }

    private fun saveOffset() {
        offset = SystemClock.elapsedRealtime() - chronotimer.base
    }

    private fun setBaseTime() {
        chronotimer.base = SystemClock.elapsedRealtime() - offset
    }
}