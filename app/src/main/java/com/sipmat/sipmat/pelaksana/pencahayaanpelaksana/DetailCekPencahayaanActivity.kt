package com.sipmat.sipmat.pelaksana.pencahayaanpelaksana

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.sipmat.sipmat.R
import com.sipmat.sipmat.databinding.ActivityDetailCekPencahayaanBinding
import com.sipmat.sipmat.model.PostDataResponse
import com.sipmat.sipmat.model.pencahayaan.SchedulePencahayaanModel
import com.sipmat.sipmat.model.pencahayaan.UpdateSchedulePencahayaan
import com.sipmat.sipmat.session.SessionManager
import com.sipmat.sipmat.webservice.ApiClient
import org.jetbrains.anko.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class DetailCekPencahayaanActivity : AppCompatActivity(), AnkoLogger {
    lateinit var binding: ActivityDetailCekPencahayaanBinding
    var api = ApiClient.instance()
    lateinit var sessionManager: SessionManager
    lateinit var progressDialog: ProgressDialog
    companion object {
        var cekpencahayaan: SchedulePencahayaanModel? = null

    }

    var currentDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_cek_pencahayaan)
        binding.lifecycleOwner = this
        val gson = Gson()
        cekpencahayaan =
            gson.fromJson(
                intent.getStringExtra("cekpencahayaan"),
                SchedulePencahayaanModel::class.java
            )

        val sdf = SimpleDateFormat("yyyy-M-dd")
        currentDate = sdf.format(Date())
        binding.txltglPemeriksa.text = "Tgl Pemeriksa : $currentDate"


        sessionManager = SessionManager(this)
        progressDialog = ProgressDialog(this)

        binding.txtshift.text = "Shift : ${sessionManager.getNama()}"


        binding.btnscan.setOnClickListener {
            startActivity<QrCodeCekPencahayaanActivity>()
        }

        binding.btnsubmit.setOnClickListener {
            val keterangan = binding.txtketerangan.text.toString().trim()
            val lux1 = binding.txtlux1.text.toString().trim()
            val lux2 = binding.txtlux2.text.toString().trim()
            val lux3 = binding.txtlux3.text.toString().trim()
//            val luxrata2 = binding.txtluxrata2.text.toString().trim()
            val sumber_pencahayaan = binding.spnsumberpencahayaan.selectedItem.toString()

            if (keterangan.isNotEmpty() && lux1.isNotEmpty()
                && lux2.isNotEmpty() && lux3.isNotEmpty() && QrCodeCekPencahayaanActivity . kodepencahayaan != null && QrCodeCekPencahayaanActivity.lokasi != null) {
                loading(true)

                val updateschedulepencahayaan = UpdateSchedulePencahayaan(
                    keterangan,
                    cekpencahayaan!!.tw,
                    cekpencahayaan!!.tahun,
                    sessionManager.getNama().toString(),
                    cekpencahayaan!!.tanggalCek,
                    lux1.toInt(),
                    1,
                    lux2.toInt(),
                    lux3.toInt(),
//                    luxrata2.toFloat(),
                    sumber_pencahayaan,
                    cekpencahayaan!!.id,
                    currentDate
                )


                api.update_schedule_pencahayaan(updateschedulepencahayaan).enqueue(object :
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
        //CEK pencahayaan
        if (QrCodeCekPencahayaanActivity.kodepencahayaan != null && QrCodeCekPencahayaanActivity.lokasi != null) {
            binding.txtkodepencahayaan.text =
                "Kode pencahayaan : ${QrCodeCekPencahayaanActivity.kodepencahayaan.toString()}"
            binding.txtlokasi.text =
                "Lokasi Penempatan pencahayaan : ${QrCodeCekPencahayaanActivity.lokasi.toString()}"
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        cekpencahayaan = null
        QrCodeCekPencahayaanActivity.kodepencahayaan = null
        QrCodeCekPencahayaanActivity.lokasi = null
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