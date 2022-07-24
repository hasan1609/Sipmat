package com.sipmat.sipmat.pelaksana.aparpelaksana

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.sipmat.sipmat.R
import com.sipmat.sipmat.pelaksana.aparpelaksana.QrCoderCekAparActivity.Companion.jenis
import com.sipmat.sipmat.pelaksana.aparpelaksana.QrCoderCekAparActivity.Companion.kadaluarsa
import com.sipmat.sipmat.pelaksana.aparpelaksana.QrCoderCekAparActivity.Companion.kodeapar
import com.sipmat.sipmat.pelaksana.aparpelaksana.QrCoderCekAparActivity.Companion.lokasi
import com.sipmat.sipmat.databinding.ActivityCekAparBinding
import com.sipmat.sipmat.model.PostDataResponse
import com.sipmat.sipmat.model.ScheduleAparPelaksanaModel
import com.sipmat.sipmat.model.postdata.PostScheduleApar
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

class CekAparActivity : AppCompatActivity(), AnkoLogger {
    lateinit var binding: ActivityCekAparBinding
    var api = ApiClient.instance()
    lateinit var sessionManager: SessionManager
    lateinit var progressDialog: ProgressDialog
    companion object {
        var cekapar: ScheduleAparPelaksanaModel? = null
    }
    var currentDate: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cek_apar)
        binding.lifecycleOwner = this

        val gson = Gson()
        cekapar =
            gson.fromJson(intent.getStringExtra("cekapar"), ScheduleAparPelaksanaModel::class.java)

        val sdf = SimpleDateFormat("yyyy-M-dd")
        currentDate = sdf.format(Date())
        binding.txltglPemeriksa.text = "Tgl Pemeriksa : $currentDate"
        sessionManager = SessionManager(this)
        progressDialog = ProgressDialog(this)
        binding.txtshift.text = "Shift : ${sessionManager.getNama()}"
        binding.btnscan.setOnClickListener {
            startActivity<QrCoderCekAparActivity>()
        }
        binding.btnsubmit.setOnClickListener {
            val keterangan = binding.edtketerangan.text.toString().trim()
            val kapasitas = binding.edtkapasitas.text.toString().trim()
            if (kodeapar != null && jenis != null && lokasi != null && kadaluarsa != null) {
                loading(true)
                val spntabung = binding.spntabung.selectedItem
                val spnpin = binding.spnpin.selectedItem
                val spnsegel = binding.spnsegel.selectedItem
                val spnhandle = binding.spnhandle.selectedItem
                val spnpressure = binding.spnpressure.selectedItem
                val spnselang = binding.spnselang.selectedItem
                val spnnoozle = binding.spnnoozle.selectedItem
                val spnrambusegitiga = binding.spnrambusegitiga.selectedItem
                val spngantungan = binding.spngantungan.selectedItem
                val spnhousekeeping = binding.spnhousekeeping.selectedItem
                val spntuas = binding.spntuas.selectedItem


                val updatescheduleapar =
                    PostScheduleApar(
                        cekapar!!.tw,
                        cekapar!!.tahun,
                        spntuas.toString(),
                        spnrambusegitiga.toString(),
                        spngantungan.toString(),
                        currentDate,
                        sessionManager.getNama(),
                        cekapar!!.tanggalCek,
                        spnpressure.toString(),
                        spntabung.toString(),
                        spnnoozle.toString(),
                        1,
                        spnpin.toString(),
                        keterangan,
                        spnsegel.toString(),
                        cekapar!!.id,
                        spnhousekeeping.toString(),
                        spnselang.toString(),
                        kapasitas.toInt(),
                        spnhandle.toString()
                    )

                api.update_schedule_apar(updatescheduleapar).enqueue(object :Callback<PostDataResponse>{
                    override fun onResponse(
                        call: Call<PostDataResponse>,
                        response: Response<PostDataResponse>
                    ) {
                        if (response.isSuccessful){
                            loading(false)
                            if (response.body()!!.sukses ==1){
                                finish()
                                toast("Tunggu approve admin")
                            }else{
                                finish()
                                toast("silahkan coba lagi")
                            }
                        }else{
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
                toast("silahkan scan APAT terlebih dahulu")
            }

        }
    }

    override fun onStart() {
        super.onStart()
        //CEK APAR
        if (kodeapar != null && jenis != null && lokasi != null && kadaluarsa != null) {
            binding.txtkodeapar.text = "Kode APAR : ${kodeapar.toString()}"
            binding.txtjenisapar.text = "Jenis APAR : ${jenis.toString()}"
            binding.txtlokasi.text = "Lokasi Penempatan APAR : ${lokasi.toString()}"
            binding.txtkadaluarsa.text = "Tanggal Kadaluarsa : ${kadaluarsa.toString()}"
        }

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

    override fun onBackPressed() {
        super.onBackPressed()
        QrCoderCekAparActivity.kodeapar = null
        QrCoderCekAparActivity.jenis = null
        QrCoderCekAparActivity.lokasi = null
        QrCoderCekAparActivity.kadaluarsa = null
    }

}