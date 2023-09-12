package com.example.pemesanan_tiket

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import com.example.pemesanan_tiket.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private lateinit var b: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)
        supportActionBar?.hide()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        b.cvKereta.setOnClickListener {
            val intent = Intent(this, InputDataActivity::class.java)
            intent.putExtra("jns", "Kereta")
            startActivity(intent)
        }

        b.cvPesawat.setOnClickListener {
            val intent = Intent(this, InputDataActivity::class.java)
            intent.putExtra("jns", "Pesawat")
            startActivity(intent)
        }

        b.cvHotel.setOnClickListener {
            startActivity(Intent(this, InputHotelActivity::class.java))
        }

        b.cvTutorial.setOnClickListener {
            startActivity(Intent(this, IklanActivity::class.java))
        }

        b.bottomNavigationView.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item?.itemId) {
            R.id.menuHome -> {
                val fragment = fragmentManager?.findFragmentById(R.id.frameLayout)
                fragment?.let { it1 -> fragmentManager?.beginTransaction()?.remove(it1)?.commit() }
                b.frameLayout.visibility = View.GONE
            }
            R.id.menuRiwayat -> {
                b.frameLayout.visibility = View.VISIBLE
                b.frameLayout.setBackgroundColor(Color.argb(255,255,255,255))
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frameLayout, RiwayatFragment()).commit()
            }
        }
        return true
    }
}