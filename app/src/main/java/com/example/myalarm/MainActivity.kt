package com.example.myalarm

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var editTextTime: EditText
    private lateinit var startButton: Button
    private lateinit var stopButton: Button
    private lateinit var resumeButton: Button
    private lateinit var resetButton: Button

    private var startTime: Long = 0
    private var elapsedTime: Long = 0
    private var isRunning = false
    private val handler = Handler(Looper.getMainLooper())

    private val updateTimeRunnable = object : Runnable {
        override fun run() {
            if (isRunning) {
                elapsedTime = System.currentTimeMillis() - startTime
                updateTimerDisplay()
                handler.postDelayed(this, 10)
            }
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextTime = findViewById(R.id.editTextTime)
        startButton = findViewById(R.id.start_btn)
        stopButton = findViewById(R.id.pause_button)
        resumeButton = findViewById(R.id.resume_btn)
        resetButton = findViewById(R.id.reset_btn)

        startButton.setOnClickListener {
            startTimer()
        }

        stopButton.setOnClickListener {
            stopTimer()
        }

        resumeButton.setOnClickListener {
            resumeTimer()
        }

        resetButton.setOnClickListener {
            resetTimer()
        }
    }

    private fun startTimer() {
        if (!isRunning) {
            startTime = System.currentTimeMillis() - elapsedTime
            isRunning = true
            handler.post(updateTimeRunnable)
        }
    }

    private fun stopTimer() {
        isRunning = false
        handler.removeCallbacks(updateTimeRunnable)
    }

    private fun resumeTimer() {
        if (!isRunning) {
            startTime = System.currentTimeMillis() - elapsedTime
            isRunning = true
            handler.post(updateTimeRunnable)
        }
    }

    private fun resetTimer() {
        stopTimer()
        elapsedTime = 0
        updateTimerDisplay()
    }

    @SuppressLint("DefaultLocale")
    private fun updateTimerDisplay() {
        val minutes = (elapsedTime / 60000) % 60
        val seconds = (elapsedTime / 1000) % 60
        val milliseconds = elapsedTime % 1000

        val timeString = String.format("%02d:%02d:%03d", minutes, seconds, milliseconds)
        editTextTime.setText(timeString)
    }
}

