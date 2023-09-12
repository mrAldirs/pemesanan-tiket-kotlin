package com.example.pemesanan_tiket

import android.R
import android.app.Activity
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.apk_pn.Helper.MediaHelper
import com.example.pemesanan_tiket.databinding.ActivityInputDataBinding
import com.example.pemesanan_tiket.databinding.ActivityInputHotelBinding
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.HashMap
import java.util.Locale

class InputHotelActivity : AppCompatActivity() {
    private lateinit var b: ActivityInputHotelBinding
    lateinit var urlClass: UrlClass

    val strHotel = arrayOf("Hotel Kediri", "Hotel Semarang", "Hotel Surabaya", "Hotel Bali")
    val strKelas = arrayOf("Eksekutif", "Bisnis", "Ekonomi")
    var sHotel = ""
    var sKelas= ""
    var alamat= ""
    var hargaKelas = 0
    var harga = 0
    var countJumlah = 0

    lateinit var mediaHealper: MediaHelper
    var imStr = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityInputHotelBinding.inflate(layoutInflater)
        setContentView(b.root)
        supportActionBar?.hide()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        mediaHealper = MediaHelper(this)
        urlClass = UrlClass()

        setSpinnerAdapter()
        setJmlPenumpang()

        b.inputTanggal.setOnClickListener {
            showDatePickerDialog()
        }

        b.btnChoose.setOnClickListener {
            val intent = Intent()
            intent.setType("image/*")
            intent.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(intent, mediaHealper.RcGallery())
        }

        b.btnCheckout.setOnClickListener {
            AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_input_get)
                .setTitle("Konfirmasi!")
                .setMessage("Apakah Anda sudah yakin ingin melakukan pemesanan sekarang?")
                .setPositiveButton("Ya", DialogInterface.OnClickListener { dialogInterface, i ->
                    create()
                })
                .setNegativeButton("Tidak", DialogInterface.OnClickListener { dialogInterface, i ->
                    Toast.makeText(this,"Berhasil Membatalkan!", Toast.LENGTH_SHORT).show()
                })
                .show()
            true
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == mediaHealper.RcGallery()){
                imStr = mediaHealper.getBitmapToString(data!!.data,b.insFoto)
            }
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val date = "$year-${month + 1}-$dayOfMonth"
                b.inputTanggal.setText(date)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }

    private fun setSpinnerAdapter() {
        val adapterHotel =
            ArrayAdapter<CharSequence>(this, R.layout.simple_spinner_item, strHotel)
        adapterHotel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        b.spHotel.adapter = adapterHotel

        val adapterKelas =
            ArrayAdapter<CharSequence>(this, R.layout.simple_spinner_item, strKelas)
        adapterKelas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        b.spKelas.adapter = adapterKelas

        b.spHotel.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                sHotel = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        b.spKelas.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                sKelas = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun setJmlPenumpang() {
        //add anak
        b.imageAdd2.setOnClickListener {
            countJumlah = countJumlah + 1
            b.tvJml.text = countJumlah.toString()
        }

        //min anak
        b.imageMinus2.setOnClickListener {
            if (countJumlah > 0) {
                countJumlah = countJumlah - 1
                b.tvJml.text = countJumlah.toString()
            }
        }
    }

    private fun create() {
        val request = object : StringRequest(
            Method.POST,urlClass.hotel,
            Response.Listener { response ->
                val jsonObject = JSONObject(response)
                val respon = jsonObject.getString("respon")
                if (respon.equals("1")) {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    startActivity(intent)
                    Toast.makeText(this, "Berhasil mengirim data pemesanan Hotel!", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this,"Tidak dapat terhubung ke server", Toast.LENGTH_LONG).show()
            }){
            override fun getParams(): MutableMap<String, String>? {
                val hm = HashMap<String, String>()
                if (sHotel == "Hotel Kediri") {
                    harga = 150000
                    alamat = "Bandar Kidul, Kediri, Jawa Timur"
                } else if (sHotel == "Hotel Semarang") {
                    harga = 450000
                    alamat = "Panggung Kidul, Kota Semarang, Jawa Tengah"
                } else if (sHotel == "Hotel Surabaya") {
                    harga = 500000
                    alamat = "Kota Surabaya"
                } else if (sHotel == "Hotel Bali") {
                    harga = 600000
                    alamat = "Kuta, Kabupaten Badung, Bali"
                }

                if (sKelas == "Eksekutif") {
                    hargaKelas = 400000
                } else if (sKelas.equals("Bisnis", ignoreCase = true)) {
                    hargaKelas = 250000
                } else if (sKelas.equals("Ekonomi", ignoreCase = true)) {
                    hargaKelas = 100000
                }

                val nmFile ="IMG"+ SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(
                    Date()
                )+".jpg"

                hm.put("mode", "insert")
                hm.put("nama", b.inputNama.text.toString())
                hm.put("hotel", b.spHotel.selectedItem.toString())
                hm.put("kelas", b.spKelas.selectedItem.toString())
                hm.put("no_hp", b.inputTelepon.text.toString())
                hm.put("pelanggan", countJumlah.toString())
                hm.put("alamat", alamat)
                hm.put("total", ((harga + hargaKelas) * countJumlah).toString())
                hm.put("tgl", b.inputTanggal.text.toString())
                hm.put("image",imStr)
                hm.put("file",nmFile)

                return hm
            }
        }
        val  queue = Volley.newRequestQueue(this)
        queue.add(request)
    }
}