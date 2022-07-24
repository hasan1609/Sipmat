package com.sipmat.sipmat.admin.apar

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.sipmat.sipmat.R
import com.sipmat.sipmat.databinding.ActivityCekAparAdminBinding
import com.sipmat.sipmat.model.PostDataResponse
import com.sipmat.sipmat.model.ScheduleModel
import com.sipmat.sipmat.webservice.ApiClient
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CekAparAdminActivity : AppCompatActivity(),AnkoLogger {
    lateinit var cekapar: ScheduleModel
    lateinit var binding: ActivityCekAparAdminBinding

    lateinit var progressDialog: ProgressDialog
    var api = ApiClient.instance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cek_apar_admin)
        binding.lifecycleOwner = this
        progressDialog = ProgressDialog(this)
        val gson = Gson()
        cekapar =
            gson.fromJson(intent.getStringExtra("cekapar"), ScheduleModel::class.java)

        if (cekapar.isStatus == 0 || cekapar.isStatus ==2 || cekapar.isStatus ==3){
            binding.btnApprove.visibility = View.GONE
        }
        if (cekapar.isStatus ==1){
            binding.btnReturn.visibility = View.VISIBLE
        }


        binding.txtshift.text = "Shift : ${cekapar.shift.toString()}"
        binding.txltglPemeriksa.text = "Tgl Pemeriksa : ${cekapar.tanggalPemeriksa.toString()}"
        binding.txtkodeapar.text = "Kode APAR : ${cekapar.kodeApar.toString()}"
        binding.txtjenisapar.text = "Jenis APAR : ${cekapar.jenis.toString()}"
        binding.txtlokasi.text = "Lokasi APAR : ${cekapar.lokasi.toString()}"
        binding.txtkadaluarsa.text = "Tanggal Kadaluarsa : ${cekapar.tglKadaluarsa.toString()}"
        //kapasitas belum
        binding.txtkapasitas.text = cekapar.kapasitas.toString()
        binding.txttabung.text = cekapar.tabung
        binding.txtpin.text = cekapar.pin
        binding.txtsegel.text = cekapar.segel
        //Handle belum
        binding.txthandle.text = cekapar.handle
        binding.txtpressure.text = cekapar.pressure
        binding.txttuas.text = cekapar.tuas
        binding.txtselang.text = cekapar.selang
        binding.txtnozzle.text = cekapar.nozzle
        binding.txtrambusegitiga.text = cekapar.rambu
        binding.txtgantungan.text = cekapar.gantungan
        binding.txthousekeeping.text = cekapar.houskeeping
        binding.txtketerangan.text = cekapar.keteranganTambahan


        binding.btnReturn.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("APAR")
            builder.setMessage("ACC APAR ? ")
            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                loading(true)
                api.return_apar(cekapar.id!!).enqueue(object : Callback<PostDataResponse> {
                    override fun onResponse(
                        call: Call<PostDataResponse>,
                        response: Response<PostDataResponse>
                    ) {
                        try {
                            if (response.body()!!.sukses == 1) {
                                loading(false)
                                toast("return schedule berhasil")
                                finish()
                            } else {
                                loading(false)
                                Snackbar.make(it, "acc  gagal", Snackbar.LENGTH_SHORT)
                                    .show()

                            }


                        } catch (e: Exception) {
                            progressDialog.dismiss()
                            info { "dinda ${e.message}${response.code()} " }
                        }

                    }

                    override fun onFailure(call: Call<PostDataResponse>, t: Throwable) {
                        loading(false)
                        Snackbar.make(it, "Kesalahan jaringan", Snackbar.LENGTH_SHORT).show()

                    }

                })
            }

            builder.setNegativeButton(android.R.string.no) { dialog, which ->

            }

            builder.show()
        }

        binding.btnApprove.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("APAR")
            builder.setMessage("ACC APAR ? ")
            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                loading(true)
                api.acc_apar(cekapar.id!!).enqueue(object : Callback<PostDataResponse> {
                    override fun onResponse(
                        call: Call<PostDataResponse>,
                        response: Response<PostDataResponse>
                    ) {
                        try {
                            if (response.body()!!.sukses == 1) {
                                loading(false)
                                toast("acc schedule berhasil")
                                finish()
                            } else {
                                loading(false)
                                Snackbar.make(it, "acc  gagal", Snackbar.LENGTH_SHORT)
                                    .show()

                            }


                        } catch (e: Exception) {
                            progressDialog.dismiss()
                            info { "dinda ${e.message}${response.code()} " }
                        }

                    }

                    override fun onFailure(call: Call<PostDataResponse>, t: Throwable) {
                        loading(false)
                        Snackbar.make(it, "Kesalahan jaringan", Snackbar.LENGTH_SHORT).show()

                    }

                })
            }

            builder.setNegativeButton(android.R.string.no) { dialog, which ->

            }

            builder.show()

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

}