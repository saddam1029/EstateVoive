package com.example.estatevoice

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.estatevoice.databinding.ActivityCallDetailBinding
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.concurrent.TimeUnit
import kotlin.math.log

class CallDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCallDetailBinding

    private var mediaPlayer: MediaPlayer? = null
    private var isPlaying = false
    private var isPrepared = false

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCallDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val clientId = intent.getStringExtra("clientID")

        binding.tvDetailName.text = intent.getStringExtra("name")
        binding.tvNameInfo.text = intent.getStringExtra("name")
        binding.tvDetailContact.text = intent.getStringExtra("contact")
        binding.tvBusinessInfo.text = intent.getStringExtra("business")
        binding.tvEmail.text = intent.getStringExtra("email")
        binding.tvRole.text = intent.getStringExtra("role")
        binding.tvAddressInfo.text = intent.getStringExtra("address")
        binding.tvIntent.text = intent.getStringExtra("interest")
        binding.tvNote.text = intent.getStringExtra("note")
        binding.tvSummary.text = intent.getStringExtra("summary")

        val recordingUrl = intent.getStringExtra("audio")

        if (!recordingUrl.isNullOrEmpty()) {
            setupAudioPlayer(recordingUrl)
        }

        val time = intent.getStringExtra("time")
        binding.tvTimeInfo.text = dataTime(time)

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

    }

    private fun dataTime(dateString: String?): String {
        if (dateString.isNullOrEmpty()) return "-"

        return try {
            val parsedDate = OffsetDateTime.parse(dateString)

            val formatter = DateTimeFormatter.ofPattern(
                "MMMM dd, yyyy 'at' hh:mm a",
                Locale.ENGLISH
            )

            parsedDate.format(formatter)

        } catch (e: Exception) {
            dateString // fallback if parsing fails
        }
    }



    private fun setupAudioPlayer(audioUrl: String) {

        mediaPlayer = MediaPlayer().apply {
            setDataSource(audioUrl)
            prepareAsync()

            setOnPreparedListener {
                isPrepared = true
                binding.seekBar.max = it.duration
                binding.tvTime.text = "0:00 / ${formatTime(it.duration)}"

                // ensure initial state
                binding.btnPlayPause.setImageResource(R.drawable.play)
                this@CallDetailActivity.isPlaying = false
            }

            setOnCompletionListener {
                this@CallDetailActivity.isPlaying = false
                binding.btnPlayPause.setImageResource(R.drawable.play)
                binding.seekBar.progress = 0
                binding.tvTime.text = "0:00 / ${formatTime(it.duration)}"
                handler.removeCallbacks(updateRunnable)
            }
        }

        binding.btnPlayPause.setOnClickListener {
            if (!isPrepared) return@setOnClickListener

            if (isPlaying) {
                pauseAudio()
            } else {
                playAudio()
            }
        }

        binding.seekBar.setOnSeekBarChangeListener(object :
            android.widget.SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(
                seekBar: android.widget.SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                if (fromUser && isPrepared) {
                    mediaPlayer?.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: android.widget.SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: android.widget.SeekBar?) {}
        })
    }

    // ---------------- PLAY ----------------
    private fun playAudio() {
        mediaPlayer?.start()
        isPlaying = true
        binding.btnPlayPause.setImageResource(R.drawable.pause)
        handler.post(updateRunnable)
    }

    // ---------------- PAUSE ----------------
    private fun pauseAudio() {
        mediaPlayer?.pause()
        isPlaying = false
        binding.btnPlayPause.setImageResource(R.drawable.play)
        handler.removeCallbacks(updateRunnable)
    }

    // ---------------- UPDATE UI ----------------
    private val updateRunnable = object : Runnable {
        override fun run() {
            mediaPlayer?.let {
                binding.seekBar.progress = it.currentPosition
                binding.tvTime.text =
                    "${formatTime(it.currentPosition)} / ${formatTime(it.duration)}"
                handler.postDelayed(this, 500)
            }
        }
    }

    // ---------------- TIME FORMAT ----------------
    private fun formatTime(ms: Int): String {
        val minutes = TimeUnit.MILLISECONDS.toMinutes(ms.toLong())
        val seconds =
            TimeUnit.MILLISECONDS.toSeconds(ms.toLong()) % 60
        return String.format("%d:%02d", minutes, seconds)
    }

    // ---------------- CLEANUP ----------------
    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(updateRunnable)
        mediaPlayer?.release()
        mediaPlayer = null
    }

}