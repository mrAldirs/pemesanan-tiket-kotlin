package com.example.pemesanan_tiket

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterHotel(val data: List<HashMap<String,String>>, val parent: HistoryActivity) :
    RecyclerView.Adapter<AdapterHotel.HolderDataHotel>(){
    class HolderDataHotel(v : View) : RecyclerView.ViewHolder(v) {
        val nm = v.findViewById<TextView>(R.id.tvNama)
        val tgl = v.findViewById<TextView>(R.id.tvDate)
        val kls = v.findViewById<TextView>(R.id.tvKelas)
        val hrg = v.findViewById<TextView>(R.id.tvHargaTiket)
        val plg = v.findViewById<TextView>(R.id.tvPelanggan)
        val htl = v.findViewById<TextView>(R.id.hotelNama)
        val ktp = v.findViewById<TextView>(R.id.btnKtp)
        val loc = v.findViewById<TextView>(R.id.btnLokasi)
        val hps = v.findViewById<TextView>(R.id.btnHapus)
        val cd = v.findViewById<LinearLayout>(R.id.card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderDataHotel {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item_hotel,parent,false)
        return HolderDataHotel(v)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: HolderDataHotel, position: Int) {
        val data = data.get(position)
        holder.nm.setText(data.get("nama"))
        holder.tgl.setText(data.get("tgl"))
        holder.kls.setText(data.get("kelas"))
        holder.plg.setText(data.get("pelanggan")+" Orang")
        holder.htl.setText(data.get("hotel"))
        holder.hrg.setText("Rp."+data.get("total"))

        holder.ktp.setOnClickListener {
            val dialog = Ktp()

            val bundle = Bundle()
            bundle.putString("ktp", data.get("ktp").toString())
            dialog.arguments = bundle

            dialog.show(parent.supportFragmentManager, "PembayaranFragment")
        }

        holder.hps.setOnClickListener {
            AlertDialog.Builder(parent)
                .setIcon(R.drawable.ic_co_present)
                .setTitle("Peringatan")
                .setMessage("Apakah Anda ingin menghapus data ini?")
                .setPositiveButton("Ya", DialogInterface.OnClickListener { dialogInterface, i ->
                    parent.hapusHotel(data.get("id_hotel").toString())
                })
                .setNegativeButton("Tidak", DialogInterface.OnClickListener { dialogInterface, i ->
                })
                .show()
            true
        }

        holder.loc.setOnClickListener {
            val intent = Intent(parent, MapsActivity::class.java)
            intent.putExtra("alm", data.get("alamat").toString())
            parent.startActivity(intent)
        }
    }
}