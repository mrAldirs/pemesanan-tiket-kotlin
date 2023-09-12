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

class AdapterTiket(val data: List<HashMap<String,String>>, val parent: HistoryActivity) :
    RecyclerView.Adapter<AdapterTiket.HolderDataTiket>(){
    class HolderDataTiket(v : View) : RecyclerView.ViewHolder(v) {
        val nm = v.findViewById<TextView>(R.id.tvNama)
        val tgl = v.findViewById<TextView>(R.id.tvDate)
        val kls = v.findViewById<TextView>(R.id.tvKelas)
        val sts = v.findViewById<TextView>(R.id.statusBayar)
        val hrg = v.findViewById<TextView>(R.id.tvHargaTiket)
        val kd1 = v.findViewById<TextView>(R.id.tvKode1)
        val kd2 = v.findViewById<TextView>(R.id.tvKode2)
        val brk = v.findViewById<TextView>(R.id.tvKeberangkatan)
        val tjn = v.findViewById<TextView>(R.id.tvTujuan)
        val qr = v.findViewById<TextView>(R.id.btnQr)
        val byr = v.findViewById<TextView>(R.id.btnBayar)
        val hps = v.findViewById<TextView>(R.id.btnHapus)
        val edt = v.findViewById<TextView>(R.id.btnEdit)
        val cd = v.findViewById<LinearLayout>(R.id.card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderDataTiket {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item_history,parent,false)
        return HolderDataTiket(v)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: HolderDataTiket, position: Int) {
        val data = data.get(position)
        holder.nm.setText(data.get("nama"))
        holder.tgl.setText(data.get("tgl"))
        holder.kls.setText(data.get("kelas"))
        holder.sts.setText(data.get("status_bayar"))
        holder.hrg.setText("Rp."+data.get("total"))
        holder.brk.setText(data.get("asal"))
        holder.tjn.setText(data.get("tujuan"))

        if (data.get("status_bayar").toString().equals("Sudah bayar")) {
            holder.byr.visibility = View.GONE
            holder.edt.visibility = View.GONE
        }

        holder.qr.setOnClickListener {
            val intent = Intent(it.context, QrActivity::class.java)
            intent.putExtra("id", data.get("id_tiket").toString())
            parent.startActivity(intent)
        }

        holder.byr.setOnClickListener {
            val dialog = PembayaranFragment()

            val bundle = Bundle()
            bundle.putString("id", data.get("id_tiket").toString())
            dialog.arguments = bundle

            dialog.show(parent.supportFragmentManager, "PembayaranFragment")
        }

        holder.hps.setOnClickListener {
            AlertDialog.Builder(parent)
                .setIcon(R.drawable.ic_co_present)
                .setTitle("Peringatan")
                .setMessage("Apakah Anda ingin menghapus data ini?")
                .setPositiveButton("Ya", DialogInterface.OnClickListener { dialogInterface, i ->
                    parent.hapusTiket(data.get("id_tiket").toString())
                })
                .setNegativeButton("Tidak", DialogInterface.OnClickListener { dialogInterface, i ->
                })
                .show()
            true
        }

        holder.edt.setOnClickListener {
            val intent = Intent(parent, EditTiketActivity::class.java)
            intent.putExtra("id", data.get("id_tiket").toString())
            parent.startActivity(intent)
        }

        if (data.get("asal").toString().equals("Surabaya")) {
            holder.kd1.setText("SBY")
        } else if (data.get("asal").toString().equals("Jakarta")) {
            holder.kd1.setText("JKT")
        } else if (data.get("asal").toString().equals("Semarang")) {
            holder.kd1.setText("SMG")
        } else if (data.get("asal").toString().equals("Bali")) {
            holder.kd1.setText("BLI")
        }

        if (data.get("tujuan").toString().equals("Surabaya")) {
            holder.kd2.setText("SBY")
        } else if (data.get("tujuan").toString().equals("Jakarta")) {
            holder.kd2.setText("JKT")
        } else if (data.get("tujuan").toString().equals("Semarang")) {
            holder.kd2.setText("SMG")
        } else if (data.get("tujuan").toString().equals("Bali")) {
            holder.kd2.setText("BLI")
        }
    }
}