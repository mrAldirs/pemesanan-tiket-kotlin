package com.example.pemesanan_tiket

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.StrictMode
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.pemesanan_tiket.databinding.FragmentPembayarabBinding
import com.example.uts_dyah.PhotoHelper
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import org.json.JSONObject
import java.util.HashMap

class PembayaranFragment : DialogFragment() {
    private lateinit var b: FragmentPembayarabBinding
    lateinit var v: View
    lateinit var parent: HistoryActivity

    lateinit var urlClass: UrlClass

    lateinit var photoHelper: PhotoHelper
    var imStr = ""
    var namaFile = ""
    var fileUri = Uri.parse("")

    @Suppress("DEPRECATION")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = FragmentPembayarabBinding.inflate(layoutInflater)
        v = b.root
        parent = activity as HistoryActivity

        urlClass = UrlClass()
        photoHelper = PhotoHelper()

        try {
            val m = StrictMode::class.java.getMethod("disableDeathOnFileUriExposure")
            m.invoke(null)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        b.btnChoose.setOnClickListener {
            requestPermission()
        }

        b.btnKirim.setOnClickListener {
            AlertDialog.Builder(v.context)
                .setIcon(android.R.drawable.stat_sys_warning)
                .setTitle("Konfirmasi!")
                .setMessage("Apakah Anda yakin ingin melakukan pembayaran?")
                .setPositiveButton("Ya", DialogInterface.OnClickListener { dialogInterface, i ->
                    bayar("bayar")
                    parent.recreate()
                    dismiss()
                })
                .setNegativeButton("Tidak", DialogInterface.OnClickListener { dialogInterface, i ->
                    Toast.makeText(v.context,"Berhasil Membatalkan!", Toast.LENGTH_SHORT).show()
                })
                .show()
            true
        }

        return v
    }

    fun requestPermission() = runWithPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA ) {
        fileUri = photoHelper.getOutputMediaFileUri()
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
        startActivityForResult(intent, photoHelper.getRcCamera())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            photoHelper.getRcCamera() -> {
                when (resultCode) {
                    AppCompatActivity.RESULT_OK -> {
                        imStr = photoHelper.getBitMapToString(b.insFoto, fileUri)
                        namaFile = photoHelper.getMyFileName()
                        Toast.makeText(v.context, "Berhasil upload foto", Toast.LENGTH_SHORT).show()
                    }

                    AppCompatActivity.RESULT_CANCELED -> {
                        // kode untuk kondisi kedua jika dibatalkan
                    }
                }
            }
        }
    }

    private fun bayar(mode: String) {
        val request = object : StringRequest(
            Method.POST,urlClass.tiket,
            Response.Listener { response ->
                val jsonObject = JSONObject(response)
                val respon = jsonObject.getString("respon")
                if (respon.equals("1")) {
                    Toast.makeText(v.context, "Berhasil melakukan pembayaran!", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(v.context,"Tidak dapat terhubung ke server", Toast.LENGTH_LONG).show()
            }){
            override fun getParams(): MutableMap<String, String>? {
                val hm = HashMap<String, String>()
                when(mode) {
                    "bayar" -> {
                        hm.put("mode", "bayar")
                        hm.put("id_tiket", arguments?.get("id").toString())
                        hm.put("image",imStr)
                        hm.put("file",namaFile)
                    }
                }

                return hm
            }
        }
        val  queue = Volley.newRequestQueue(v.context)
        queue.add(request)
    }
}