package com.example.pemesanan_tiket

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.pemesanan_tiket.databinding.PopupBinding

class PopUp : DialogFragment() {
    private lateinit var b: PopupBinding
    lateinit var v: View

    lateinit var preferences: SharedPreferences
    val PREF_NAME = "akun"
    val NAMA = "nama"
    val DEF_NAMA = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = PopupBinding.inflate(layoutInflater)
        v = b.root

        preferences = v.context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        b.nama.text = "Selamat Datang di Aplikasi "+preferences.getString(NAMA, DEF_NAMA).toString()

        b.btnNext.setOnClickListener {
            startActivity(Intent(v.context, MainActivity::class.java))
        }

        return v
    }
}