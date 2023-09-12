package com.example.pemesanan_tiket

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pemesanan_tiket.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var b: ActivityLoginBinding

    lateinit var preferences: SharedPreferences
    val PREF_NAME = "akun"
    val NAMA = "nama"
    val DEF_NAMA = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(b.root)
        supportActionBar?.hide()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        preferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        b.btnLogin.setOnClickListener {
            if (b.edtUsername.text.toString().equals("violita") && b.edtPassword.text.toString().equals("violita")) {
                val prefEditor = preferences.edit()
                prefEditor.putString(NAMA, b.edtUsername.text.toString())
                prefEditor.commit()

                startActivity(Intent(this, DashboardActivity::class.java))
                Toast.makeText(this, "Berhasil Login!", Toast.LENGTH_SHORT).show()
            } else if (b.edtUsername.text.toString().equals("") || b.edtPassword.text.toString().equals("")) {
                Toast.makeText(this, "Username atau Password tidak boleh kosong!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Username & Password Anda Salah!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}