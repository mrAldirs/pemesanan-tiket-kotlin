package com.example.pemesanan_tiket

import android.R
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
import com.example.pemesanan_tiket.databinding.ActivityInputDataBinding
import org.json.JSONObject
import java.util.Calendar
import java.util.HashMap

class EditTiketActivity : AppCompatActivity() {
    private lateinit var b: ActivityInputDataBinding
    lateinit var urlClass: UrlClass

    val strAsal = arrayOf("Jakarta", "Semarang", "Surabaya", "Bali")
    val strTujuan = arrayOf("Jakarta", "Semarang", "Surabaya", "Bali")
    val strKelas = arrayOf("Eksekutif", "Bisnis", "Ekonomi")
    var sAsal = ""
    var sTujuan = ""
    var sKelas= ""
    var hargaDewasa = 0
    var hargaAnak = 0
    var hargaKelas = 0

    var countAnak = 0
    var countDewasa = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityInputDataBinding.inflate(layoutInflater)
        setContentView(b.root)
        supportActionBar?.hide()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        urlClass = UrlClass()

        setSpinnerAdapter()
        setJmlPenumpang()

        b.inputTanggal.setOnClickListener {
            showDatePickerDialog()
        }

        b.btnCheckout.setOnClickListener {
            if (sAsal == "Jakarta" && sTujuan == "Jakarta"
                || sAsal == "Semarang" && sTujuan == "Semarang"
                || sAsal == "Surabaya" && sTujuan == "Surabaya"
                || sAsal == "Bali" && sTujuan == "Bali"
            ) {
                Toast.makeText(this, "Asal dan Tujuan tidak boleh sama!", Toast.LENGTH_LONG).show()
            } else {
                AlertDialog.Builder(this)
                    .setIcon(R.drawable.ic_input_get)
                    .setTitle("Konfirmasi!")
                    .setMessage("Apakah Anda sudah yakin ingin melakukan pemesanan sekarang?")
                    .setPositiveButton("Ya", DialogInterface.OnClickListener { dialogInterface, i ->
                        edit()
                    })
                    .setNegativeButton("Tidak", DialogInterface.OnClickListener { dialogInterface, i ->
                        Toast.makeText(this,"Berhasil Membatalkan!", Toast.LENGTH_SHORT).show()
                    })
                    .show()
                true
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
        val adapterAsal =
            ArrayAdapter<CharSequence>(this, R.layout.simple_spinner_item, strAsal)
        adapterAsal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        b.spBerangkat.adapter = adapterAsal

        val adapterTujuan =
            ArrayAdapter<CharSequence>(this, R.layout.simple_spinner_item, strTujuan)
        adapterTujuan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        b.spTujuan.adapter = adapterTujuan

        val adapterKelas =
            ArrayAdapter<CharSequence>(this, R.layout.simple_spinner_item, strKelas)
        adapterKelas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        b.spKelas.adapter = adapterKelas

        b.spBerangkat.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                sAsal = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        b.spTujuan.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                sTujuan = parent.getItemAtPosition(position).toString()
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
        b.imageAdd1.setOnClickListener {
            countAnak = countAnak + 1
            b.tvJmlAnak.text = countAnak.toString()
        }

        //min anak
        b.imageMinus1.setOnClickListener {
            if (countAnak > 0) {
                countAnak = countAnak - 1
                b.tvJmlAnak.text = countAnak.toString()
            }
        }

        //add dewasa
        b.imageAdd2.setOnClickListener {
            countDewasa = countDewasa + 1
            b.tvJmlDewasa.text = countDewasa.toString()
        }

        //min dewasa
        b.imageMinus2.setOnClickListener {
            if (countDewasa > 0) {
                countDewasa = countDewasa - 1
                b.tvJmlDewasa.text = countDewasa.toString()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        detail()
    }

    fun detail() {
        val request = object : StringRequest(
            Method.POST,urlClass.show,
            Response.Listener { response ->
                val jsonObject = JSONObject(response)
                val nama = jsonObject.getString("nama")
                val tgl = jsonObject.getString("tgl")
                val asal = jsonObject.getString("asal")
                val tujuan = jsonObject.getString("tujuan")
                val kelas = jsonObject.getString("kelas")
                val no_hp = jsonObject.getString("no_hp")

                b.inputNama.setText(nama)
                b.inputTanggal.setText(tgl)
                b.inputTelepon.setText(no_hp)
            },
            Response.ErrorListener { error ->
                Toast.makeText(this,"Tidak dapat terhubung ke server", Toast.LENGTH_LONG).show()
            }){
            override fun getParams(): MutableMap<String, String>? {
                val hm = HashMap<String,String>()
                var paket : Bundle? = intent.extras
                hm.put("mode", "detail")
                hm.put("id_tiket", paket?.getString("id").toString())

                return hm
            }
        }
        val  queue = Volley.newRequestQueue(this)
        queue.add(request)
    }

    private fun edit() {
        val request = object : StringRequest(
            Method.POST,urlClass.tiket,
            Response.Listener { response ->
                val jsonObject = JSONObject(response)
                val respon = jsonObject.getString("respon")
                if (respon.equals("1")) {
                    val intent = Intent(this, HistoryActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    startActivity(intent)
                    Toast.makeText(this, "Berhasil mengirim data pemesanan tiket!", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this,"Tidak dapat terhubung ke server", Toast.LENGTH_LONG).show()
            }){
            override fun getParams(): MutableMap<String, String>? {
                val hm = HashMap<String, String>()
                if (sAsal == "Jakarta" && sTujuan == "Semarang") {
                    hargaDewasa = 400000
                    hargaAnak = 40000
                } else if (sAsal == "Jakarta" && sTujuan == "Surabaya") {
                    hargaDewasa = 700000
                    hargaAnak = 60000
                } else if (sAsal == "Jakarta" && sTujuan == "Bali") {
                    hargaDewasa = 1000000
                    hargaAnak = 80000
                } else if (sAsal == "Semarang" && sTujuan == "Jakarta") {
                    hargaDewasa = 400000
                    hargaAnak = 40000
                } else if (sAsal == "Semarang" && sTujuan == "Surabaya") {
                    hargaDewasa = 500000
                    hargaAnak = 60000
                } else if (sAsal == "Semarang" && sTujuan == "Bali") {
                    hargaDewasa = 700000
                    hargaAnak = 80000
                } else if (sAsal == "Surabaya" && sTujuan == "Jakarta") {
                    hargaDewasa = 700000
                    hargaAnak = 80000
                } else if (sAsal == "Surabaya" && sTujuan == "Semarang") {
                    hargaDewasa = 500000
                    hargaAnak = 20000
                } else if (sAsal == "Surabaya" && sTujuan == "Bali") {
                    hargaDewasa = 500000
                    hargaAnak = 40000
                } else if (sAsal == "Bali" && sTujuan == "Jakarta") {
                    hargaDewasa = 1000000
                    hargaAnak = 80000
                } else if (sAsal == "Bali" && sTujuan == "Semarang") {
                    hargaDewasa = 700000
                    hargaAnak = 60000
                } else if (sAsal == "Bali" && sTujuan == "Surabaya") {
                    hargaDewasa = 500000
                    hargaAnak = 40000
                }

                //set Kelas Penumpang
                if (sKelas == "Eksekutif") {
                    hargaKelas = 400000
                } else if (sKelas.equals("Bisnis", ignoreCase = true)) {
                    hargaKelas = 250000
                } else if (sKelas.equals("Ekonomi", ignoreCase = true)) {
                    hargaKelas = 100000
                }

                var paket : Bundle? = intent.extras
                hm.put("mode", "edit")
                hm.put("id_tiket", paket?.getString("id").toString())
                hm.put("nama", b.inputNama.text.toString())
                hm.put("asal", b.spBerangkat.selectedItem.toString())
                hm.put("tujuan", b.spTujuan.selectedItem.toString())
                hm.put("kelas", b.spKelas.selectedItem.toString())
                hm.put("no_hp", b.inputTelepon.text.toString())
                hm.put("penumpang", (countAnak + countDewasa).toString())
                hm.put("total", ((hargaDewasa * countDewasa) + (hargaAnak * countAnak) + hargaKelas).toString())
                hm.put("tgl", b.inputTanggal.text.toString())

                return hm
            }
        }
        val  queue = Volley.newRequestQueue(this)
        queue.add(request)
    }
}