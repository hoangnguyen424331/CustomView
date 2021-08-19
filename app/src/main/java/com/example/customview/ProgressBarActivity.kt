package com.example.customview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.customview.widgets.CircularProgressBar
import kotlinx.android.synthetic.main.activity_progress_bar.*

class ProgressBarActivity : AppCompatActivity() {

    private lateinit var circularProgressBar: CircularProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress_bar)

        circularProgressBar = findViewById(R.id.circularProgress)

        buttonStart.setOnClickListener {
            setProgress()
        }

        buttonBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setProgress() {
        object : Thread() {
            override fun run() {
                for (i in 1..100) {
                    circularProgressBar.setProgress(i)
                    try {
                        sleep(100)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }.start()
    }
}
