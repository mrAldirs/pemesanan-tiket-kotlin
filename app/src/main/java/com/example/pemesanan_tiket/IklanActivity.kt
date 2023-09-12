package com.example.pemesanan_tiket

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.media.AudioManager
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.view.WindowManager
import android.widget.MediaController
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.pemesanan_tiket.databinding.ActivityIklanBinding

class IklanActivity : AppCompatActivity() {
    private lateinit var b: ActivityIklanBinding

    private lateinit var mediaController: MediaController
    private lateinit var sharedPreferences: SharedPreferences
    private var currentPosition = 0
    private var isFullscreen = false

    private val PREF_VIDEO_POSITION = "pref_video_position"
    private val PREF_FULLSCREEN = "pref_fullscreen"

    private lateinit var audioManager: AudioManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityIklanBinding.inflate(layoutInflater)
        setContentView(b.root)
        supportActionBar?.hide()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        mediaController = MediaController(this)
        b.videoView.setMediaController(mediaController)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        currentPosition = sharedPreferences.getInt(PREF_VIDEO_POSITION, 0)

        isFullscreen = sharedPreferences.getBoolean(PREF_FULLSCREEN, false)
        if (isFullscreen) {
            supportActionBar?.hide()
            window.decorView.systemUiVisibility =
                (View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
        }

        val videoPath = "android.resource://" + packageName + "/" + R.raw.tiket
        b.videoView.setVideoURI(Uri.parse(videoPath))

        b.videoView.setOnCompletionListener { mediaPlayer ->
            mediaPlayer.seekTo(0)
            mediaPlayer.start()
        }
        val initialVolume = 10
        b.seekBarVolume.progress = initialVolume

        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

        b.seekBarVolume.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
                val volume = progress * maxVolume / 100
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Tidak ada tindakan yang perlu diambil saat mulai melacak sentuhan seekBar
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Tidak ada tindakan yang perlu diambil saat berhenti melacak sentuhan seekBar
            }
        })
    }

    override fun onResume() {
        super.onResume()
        b.videoView.seekTo(currentPosition)
        b.videoView.start()
    }

    override fun onPause() {
        super.onPause()
        b.videoView.pause()
        currentPosition = b.videoView.currentPosition

        val editor = sharedPreferences.edit()
        editor.putInt(PREF_VIDEO_POSITION, currentPosition)
        editor.apply()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            isFullscreen = true
            supportActionBar?.hide()
            window.decorView.systemUiVisibility =
                (View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            isFullscreen = false
            supportActionBar?.show()
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        }

        val editor = sharedPreferences.edit()
        editor.putBoolean(PREF_FULLSCREEN, isFullscreen)
        editor.apply()
    }

}