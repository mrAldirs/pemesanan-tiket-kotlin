package com.example.pemesanan_tiket

import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.pemesanan_tiket.databinding.ActivityHistoryBinding
import org.json.JSONArray
import org.json.JSONObject

class HistoryActivity : AppCompatActivity() {
    lateinit var b: ActivityHistoryBinding
    lateinit var urlClass: UrlClass

    val data = mutableListOf<HashMap<String,String>>()
    lateinit var adapterT: AdapterTiket
    lateinit var adapterH: AdapterHotel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(b.root)
        supportActionBar?.hide()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        urlClass = UrlClass()
        adapterT = AdapterTiket(data, this)
        adapterH = AdapterHotel(data, this)

        var paket : Bundle? = intent.extras
        var jns = paket?.getString("jns").toString()
        if (jns.equals("tiket")) {
            b.rvHistory.layoutManager = LinearLayoutManager(this)
            b.rvHistory.adapter = adapterT

            showDataTiket("")
        } else if (jns.equals("hotel")) {
            b.rvHistory.layoutManager = LinearLayoutManager(this)
            b.rvHistory.adapter = adapterH

            showDataHotel("")
        }

        b.btnCari.setOnClickListener {
            if (jns.equals("tiket")) {
                showDataTiket(b.textCari.text.toString().trim())
            } else if (jns.equals("hotel")) {
                showDataHotel(b.textCari.text.toString().trim())
            }
        }
    }

    private fun showDataTiket(nama: String) {
        val request = object : StringRequest(
            Method.POST,urlClass.show,
            Response.Listener { response ->
                data.clear()
                val jsonArray = JSONArray(response)
                for (x in 0..(jsonArray.length()-1)){
                    val jsonObject = jsonArray.getJSONObject(x)
                    var  frm = HashMap<String,String>()
                    frm.put("id_tiket",jsonObject.getString("id_tiket"))
                    frm.put("nama",jsonObject.getString("nama"))
                    frm.put("tgl",jsonObject.getString("tgl"))
                    frm.put("kelas",jsonObject.getString("kelas"))
                    frm.put("total",jsonObject.getString("total"))
                    frm.put("asal",jsonObject.getString("asal"))
                    frm.put("tujuan",jsonObject.getString("tujuan"))
                    frm.put("status_bayar",jsonObject.getString("status_bayar"))

                    data.add(frm)
                }
                adapterT.notifyDataSetChanged()
            },
            Response.ErrorListener { error ->
                Toast.makeText(this,"Tidak dapat terhubung ke server", Toast.LENGTH_LONG).show()
            }){
            override fun getParams(): MutableMap<String, String>? {
                val hm = HashMap<String,String>()
                hm.put("mode","show_data_tiket")
                hm.put("tujuan", nama)

                return hm
            }
        }
        val queue = Volley.newRequestQueue(this)
        queue.add(request)
    }

    private fun showDataHotel(hotel: String) {
        val request = object : StringRequest(
            Method.POST,urlClass.show,
            Response.Listener { response ->
                data.clear()
                val jsonArray = JSONArray(response)
                for (x in 0..(jsonArray.length()-1)){
                    val jsonObject = jsonArray.getJSONObject(x)
                    var  frm = HashMap<String,String>()
                    frm.put("id_hotel",jsonObject.getString("id_hotel"))
                    frm.put("nama",jsonObject.getString("nama"))
                    frm.put("tgl",jsonObject.getString("tgl"))
                    frm.put("kelas",jsonObject.getString("kelas"))
                    frm.put("total",jsonObject.getString("total"))
                    frm.put("hotel",jsonObject.getString("hotel"))
                    frm.put("pelanggan",jsonObject.getString("pelanggan"))
                    frm.put("alamat",jsonObject.getString("alamat"))
                    frm.put("ktp",jsonObject.getString("ktp"))

                    data.add(frm)
                }
                adapterH.notifyDataSetChanged()
            },
            Response.ErrorListener { error ->
                Toast.makeText(this,"Tidak dapat terhubung ke server", Toast.LENGTH_LONG).show()
            }){
            override fun getParams(): MutableMap<String, String>? {
                val hm = HashMap<String,String>()
                hm.put("mode","show_data_hotel")
                hm.put("hotel", hotel)

                return hm
            }
        }
        val queue = Volley.newRequestQueue(this)
        queue.add(request)
    }

    fun hapusTiket(id: String) {
        val request = object : StringRequest(
            Method.POST,urlClass.tiket,
            Response.Listener { response ->
                val jsonObject = JSONObject(response)
                val respon = jsonObject.getString("respon")
                if (respon.equals("1")) {
                    Toast.makeText(this, "Berhasil menghapus data!", Toast.LENGTH_SHORT).show()
                    recreate()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this,"Tidak dapat terhubung ke server", Toast.LENGTH_LONG).show()
            }){
            override fun getParams(): MutableMap<String, String>? {
                val hm = java.util.HashMap<String, String>()
                hm.put("mode", "delete")
                hm.put("id_tiket", id)

                return hm
            }
        }
        val  queue = Volley.newRequestQueue(this)
        queue.add(request)
    }

    fun hapusHotel(id: String) {
        val request = object : StringRequest(
            Method.POST,urlClass.hotel,
            Response.Listener { response ->
                val jsonObject = JSONObject(response)
                val respon = jsonObject.getString("respon")
                if (respon.equals("1")) {
                    Toast.makeText(this, "Berhasil menghapus data!", Toast.LENGTH_SHORT).show()
                    recreate()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this,"Tidak dapat terhubung ke server", Toast.LENGTH_LONG).show()
            }){
            override fun getParams(): MutableMap<String, String>? {
                val hm = java.util.HashMap<String, String>()
                hm.put("mode", "delete")
                hm.put("id_hotel", id)

                return hm
            }
        }
        val  queue = Volley.newRequestQueue(this)
        queue.add(request)
    }
}