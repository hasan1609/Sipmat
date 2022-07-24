package com.sipmat.sipmat.pelaksana.kebisinganpelaksana

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.sipmat.sipmat.R
import com.sipmat.sipmat.databinding.ActivityDetailCekKebisinganBinding
import com.sipmat.sipmat.model.PostDataResponse
import com.sipmat.sipmat.model.kebisingan.PostScheduleKebisingan
import com.sipmat.sipmat.model.kebisingan.ScheduleKebisinganModel
import com.sipmat.sipmat.model.kebisingan.UpdateScheduleKebisingan
import com.sipmat.sipmat.session.SessionManager
import com.sipmat.sipmat.webservice.ApiClient
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class DetailCekKebisinganActivity : AppCompatActivity(), AnkoLogger {
    lateinit var binding: ActivityDetailCekKebisinganBinding
    var api = ApiClient.instance()
    lateinit var sessionManager: SessionManager
    lateinit var progressDialog: ProgressDialog

    companion object {
        var cekkebisingan: ScheduleKebisinganModel? = null

    }

    var currentDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_cek_kebisingan)
        binding.lifecycleOwner = this

        val gson = Gson()
        cekkebisingan =
            gson.fromJson(
                intent.getStringExtra("cekkebisingan"),
                ScheduleKebisinganModel::class.java
            )

        val sdf = SimpleDateFormat("yyyy-M-dd")
        currentDate = sdf.format(Date())
        binding.txltglPemeriksa.text = "Tgl Pemeriksa : $currentDate"


        sessionManager = SessionManager(this)
        progressDialog = ProgressDialog(this)

        binding.txtshift.text = "Shift : ${sessionManager.getNama()}"


        binding.btnscan.setOnClickListener {
            startActivity<QrCodeCekKebisinganActivity>()
        }

        binding.btnsubmit.setOnClickListener {
            val keterangan = binding.txtketerangan.text.toString().trim()
            val dbx1 = binding.txtdbx1.text.toString().trim()
            val dbx2 = binding.txtdbx2.text.toString().trim()
            val dbx3 = binding.txtdbx3.text.toString().trim()
            val dbrata2 = binding.txtdbrata2.text.toString().trim()
            val status = binding.txtstatus.text.toString().trim()
            if (keterangan.isNotEmpty() && dbx1.isNotEmpty()
                && dbx2.isNotEmpty() && dbx3.isNotEmpty() && dbrata2.isNotEmpty() && status.isNotEmpty() && QrCodeCekKebisinganActivity . kodekebisingan != null && QrCodeCekKebisinganActivity.lokasi != null) {
            loading(true)

            val updateschedulekebisingan = UpdateScheduleKebisingan(
                keterangan,
                cekkebisingan!!.tw,
                cekkebisingan!!.tahun,
                dbrata2,
                sessionManager.getNama().toString(),
                cekkebisingan!!.tanggalCek,
                null,
                1,
                dbx2,
                dbx3,
                dbx1,
                cekkebisingan!!.id,
                status
            )

            api.update_schedule_kebisingan(updateschedulekebisingan).enqueue(object :
                Callback<PostDataResponse> {
                override fun onResponse(
                    call: Call<PostDataResponse>,
                    response: Response<PostDataResponse>
                ) {
                    if (response.isSuccessful) {
                        loading(false)
                        if (response.body()!!.sukses == 1) {
                            finish()
                            toast("Tunggu approve admin")
                        } else {
                            finish()
                            toast("silahkan coba lagi")
                        }
                    } else {
                        loading(false)
                        toast("Response gagal")
                    }
                }

                override fun onFailure(call: Call<PostDataResponse>, t: Throwable) {
                    loading(false)
                    toast("Jaringan error")
                    info { "dinda errror ${t.message}" }
                }

            })


        }else{
            toast("Jangan kosongi kolom")
            }

        }

    }

    override fun onStart() {
        super.onStart()
        //CEK kebisingan
        if (QrCodeCekKebisinganActivity.kodekebisingan != null && QrCodeCekKebisinganActivity.lokasi != null) {
            binding.txtkodekebisingan.text =
                "Kode kebisingan : ${QrCodeCekKebisinganActivity.kodekebisingan.toString()}"
            binding.txtlokasi.text =
                "Lokasi Penempatan kebisingan : ${QrCodeCekKebisinganActivity.lokasi.toString()}"
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        cekkebisingan = null
        QrCodeCekKebisinganActivity.kodekebisingan = null
        QrCodeCekKebisinganActivity.lokasi = null
    }

    fun loading(status: Boolean) {
        if (status) {
            progressDialog.setTitle("Loading...")
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()
        } else {
            progressDialog.dismiss()
        }
    }
}