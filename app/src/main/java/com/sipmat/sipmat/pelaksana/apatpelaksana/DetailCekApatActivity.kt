package com.sipmat.sipmat.pelaksana.apatpelaksana

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.sipmat.sipmat.R
import com.sipmat.sipmat.databinding.ActivityDetailCekApatBinding
import com.sipmat.sipmat.model.PostDataResponse
import com.sipmat.sipmat.model.apat.ScheduleApatPelaksanaModel
import com.sipmat.sipmat.model.postdata.UpdateScheduleApat
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

class DetailCekApatActivity : AppCompatActivity(),AnkoLogger {
    lateinit var binding: ActivityDetailCekApatBinding
    var api = ApiClient.instance()
    lateinit var sessionManager: SessionManager
    lateinit var progressDialog: ProgressDialog

    companion object {
        var cekapat: ScheduleApatPelaksanaModel? = null

    }

    var currentDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_cek_apat)
        binding.lifecycleOwner = this

        val gson = Gson()
        cekapat =
            gson.fromJson(intent.getStringExtra("cekapat"), ScheduleApatPelaksanaModel::class.java)

        val sdf = SimpleDateFormat("yyyy-M-dd")
        currentDate = sdf.format(Date())
        binding.txltglPemeriksa.text = "Tgl Pemeriksa : $currentDate"


        sessionManager = SessionManager(this)
        progressDialog = ProgressDialog(this)

        binding.txtshift.text = "Shift : ${sessionManager.getNama()}"


        binding.btnscan.setOnClickListener {
            startActivity<QrCodeCekApatActivity>()
        }

        binding.btnsubmit.setOnClickListener {
            val keterangan = binding.edtketerangan.text.toString().trim()
            if (QrCodeCekApatActivity.kodeapat != null && QrCodeCekApatActivity.no_bak != null && QrCodeCekApatActivity.lokasi != null) {
                loading(true)
                val spnbak = binding.spnbak.selectedItem
                val spnember = binding.spnember.selectedItem
                val spngantungan = binding.spngantungan.selectedItem
                val spnkarung = binding.spnkarung.selectedItem
                val spnpasir = binding.spnpasir.selectedItem
                val spnsekop = binding.spnsekop.selectedItem

                val updatescheduleapat = UpdateScheduleApat(
                    keterangan,
                    cekapat!!.tw,
                    cekapat!!.tahun,
                    spnember.toString(),
                    spngantungan.toString(),
                    sessionManager.getNama(),
                    cekapat!!.tanggalCek,
                    spnbak!!.toString(),
                    1,
                    spnkarung.toString(),
                    spnpasir.toString(),
                    cekapat!!.id,
                    spnsekop.toString()
                )

                api.update_schedule_apat(updatescheduleapat).enqueue(object :
                    Callback<PostDataResponse> {
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


            }

        }

    }

    override fun onStart() {
        super.onStart()
        //CEK APAR
        if (QrCodeCekApatActivity.kodeapat != null && QrCodeCekApatActivity.no_bak != null && QrCodeCekApatActivity.lokasi != null ) {
            binding.txtkodeApat.text = "Kode APAT : ${QrCodeCekApatActivity.kodeapat.toString()}"
            binding.txtnobak.text = "No Bak APAT : ${QrCodeCekApatActivity.no_bak.toString()}"
            binding.txtlokasi.text = "Lokasi Penempatan APAT : ${QrCodeCekApatActivity.lokasi.toString()}"
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        cekapat = null
        QrCodeCekApatActivity.kodeapat = null
        QrCodeCekApatActivity.no_bak = null
        QrCodeCekApatActivity.lokasi = null
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