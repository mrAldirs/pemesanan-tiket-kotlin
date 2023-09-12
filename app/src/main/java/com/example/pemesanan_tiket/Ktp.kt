package com.example.pemesanan_tiket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.pemesanan_tiket.databinding.KtpBinding
import com.squareup.picasso.Picasso

class Ktp : DialogFragment() {
    private lateinit var b: KtpBinding
    lateinit var v: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = KtpBinding.inflate(layoutInflater)
        v = b.root

        Picasso.get().load(arguments?.get("ktp").toString()).into(b.ktp)

        return v
    }
}