package com.example.pemesanan_tiket

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.fragment.app.Fragment
import com.example.pemesanan_tiket.databinding.FragmentRiwayatBinding

class RiwayatFragment : Fragment() {
    private lateinit var b: FragmentRiwayatBinding
    lateinit var v: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = FragmentRiwayatBinding.inflate(layoutInflater)
        v = b.root

        b.cvTransportasi.setOnClickListener {
            val intent = Intent(v.context, HistoryActivity::class.java)
            intent.putExtra("jns", "tiket")
            v.context.startActivity(intent)
        }

        b.cvHotel.setOnClickListener {
            val intent = Intent(v.context, HistoryActivity::class.java)
            intent.putExtra("jns", "hotel")
            v.context.startActivity(intent)
        }

        val mediaController = MediaController(v.context)
        mediaController.setAnchorView(b.videoView)

        val videoPath = "android.resource://" + requireActivity().packageName + "/" + R.raw.tiket
        b.videoView.setVideoURI(Uri.parse(videoPath))
        b.videoView.start()

        b.videoView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.setVolume(0f, 0f)
            mediaPlayer.isLooping = true
        }

        val webIntent: Intent = Uri.parse("https://shopee.co.id/dysstoreid").let { webpage ->
            Intent(Intent.ACTION_VIEW, webpage)
        }

        return v
    }
}