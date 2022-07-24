package com.sipmat.sipmat.pelaksana.hydrantpelaksana

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.sipmat.sipmat.R
import com.sipmat.sipmat.databinding.ActivityDetailCekApatBinding
import com.sipmat.sipmat.databinding.ActivityDetailCekHydrantBinding
import com.sipmat.sipmat.model.PostDataResponse
import com.sipmat.sipmat.model.hydrant.ScheduleHydrantPelaksanaModel
import com.sipmat.sipmat.model.postdata.UpdateScheduleApat
import com.sipmat.sipmat.model.postdata.UpdateScheduleHydrant
import com.sipmat.sipmat.pelaksana.apatpelaksana.QrCodeCekApatActivity
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

class DetailCekHydrantActivity : AppCompatActivity(),AnkoLogger {
    lateinit var binding: ActivityDetailCekHydrantBinding
    var api = ApiClient.instance()
    lateinit var sessionManager: SessionManager
    lateinit var progressDialog: ProgressDialog

    companion object {
        var cekhydrant: ScheduleHydrantPelaksanaModel? = null

    }

    var currentDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_cek_hydrant)
        binding.lifecycleOwner = this

        val gson = Gson()
        cekhydrant =
            gson.fromJson(intent.getStringExtra("cekhydrant"), ScheduleHydrantPelaksanaModel::class.java)

        val sdf = SimpleDateFormat("yyyy-M-dd")
        currentDate = sdf.format(Date())
        binding.txltglPemeriksa.text = "Tgl Pemeriksa : $currentDate"


        sessionManager = SessionManager(this)
        progressDialog = ProgressDialog(this)

        binding.txtshift.text = "Shift : ${sessionManager.getNama()}"


        binding.btnscan.setOnClickListener {
            startActivity<QrCodeCekHydrantActivity>()
        }

        binding.btnsubmit.setOnClickListener {
            val keterangan = binding.edtketerangan.text.toString().trim()
            if (QrCodeCekHydrantActivity.kodehydrant != null && QrCodeCekHydrantActivity.no != null && QrCodeCekHydrantActivity.lokasi != null) {
                loading(true)
                val spnflashing = binding.spnflashing.selectedItem
                val spnmainvalve = binding.spnmainvalce.selectedItem
                val spndischarge = binding.spndischarge.selectedItem
                val spnkondisibox = binding.spnkondisiBox.selectedItem
                val spnkuncibox = binding.spnkunciBox.selectedItem
                val spnkuncif = binding.spnkuncif.selectedItem
                val spnselang = binding.spnselang.selectedItem
                val spnnozzle = binding.spnnozzle.selectedItem
                val spnhousekeeping = binding.spnhousekeeping.selectedItem

                val updateschedulehydrant = UpdateScheduleHydrant(
                    spnnozzle.toString(),
                    keterangan,
                    cekhydrant!!.tw,
                    spnkuncibox.toString(),
                    cekhydrant!!.tahun,
                    sessionManager.getNama().toString(),
                    cekhydrant!!.tanggalCek,
                    spnmainvalve.toString(),
                    spnkuncif.toString(),
                    1,
                    spnhousekeeping.toString(),
                    spndischarge.toString(),
                    spnkondisibox.toString(),
                    cekhydrant!!.id,
                    spnflashing.toString(),
                    spnselang.toString()
                )

                api.update_schedule_hydrant(updateschedulehydrant).enqueue(object :
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
        //CEK Hydrant
        if (QrCodeCekHydrantActivity.kodehydrant != null && QrCodeCekHydrantActivity.no != null && QrCodeCekHydrantActivity.lokasi != null ) {
            binding.txtkodehydrant.text = "Kode hydrant : ${QrCodeCekHydrantActivity.kodehydrant.toString()}"
            binding.txtno.text = "No Box hydrant : ${QrCodeCekHydrantActivity.no.toString()}"
            binding.txtlokasi.text = "Lokasi Penempatan hydrant : ${QrCodeCekHydrantActivity.lokasi.toString()}"
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        cekhydrant = null
        QrCodeCekHydrantActivity.kodehydrant = null
        QrCodeCekHydrantActivity.no = null
        QrCodeCekHydrantActivity.lokasi = null
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