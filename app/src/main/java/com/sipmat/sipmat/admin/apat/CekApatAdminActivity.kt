package com.sipmat.sipmat.admin.apat

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
import com.sipmat.sipmat.databinding.ActivityCekApatAdminBinding
import com.sipmat.sipmat.model.PostDataResponse
import com.sipmat.sipmat.model.ScheduleModel
import com.sipmat.sipmat.model.apat.HasilApatModel
import com.sipmat.sipmat.webservice.ApiClient
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CekApatAdminActivity : AppCompatActivity(),AnkoLogger {
    lateinit var cekapat: HasilApatModel
    lateinit var binding: ActivityCekApatAdminBinding

    lateinit var progressDialog: ProgressDialog
    var api = ApiClient.instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cek_apat_admin)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cek_apat_admin)
        binding.lifecycleOwner = this
        progressDialog = ProgressDialog(this)
        val gson = Gson()
        cekapat =
            gson.fromJson(intent.getStringExtra("cekapat"), HasilApatModel::class.java)

        if (cekapat.isStatus == 0 || cekapat.isStatus ==2 || cekapat.isStatus ==3){
            binding.btnApprove.visibility = View.GONE
        }
        if (cekapat.isStatus ==1){
            binding.btnReturn.visibility = View.VISIBLE
        }


        binding.txtshift.text = "Shift : ${cekapat.shift.toString()}"
        binding.txltglPemeriksa.text = "Tgl Pemeriksa : ${cekapat.tanggalCek.toString()}"
        binding.txtkodeapat.text = "Kode apat : ${cekapat.kodeApat.toString()}"
        binding.txtlokasi.text = "Lokasi apat : ${cekapat.apat!!.lokasi.toString()}"

        binding.txtbak.text = cekapat.bak
        binding.txtpasir.text = cekapat.pasir
        binding.txtkarung.text = cekapat.karung
        binding.txtember.text = cekapat.ember
        binding.txtsekop.text = cekapat.sekop
        binding.txtgantungan.text = cekapat.gantungan
        binding.txtketerangan.text = cekapat.keterangan
        binding.txtnobak.text = cekapat.apat!!.noBak

        binding.btnReturn.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("APAR")
            builder.setMessage("ACC APAR ? ")
            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                loading(true)
                api.return_apat(cekapat.id!!).enqueue(object : Callback<PostDataResponse> {
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
            builder.setTitle("APAT")
            builder.setMessage("ACC APAT ? ")
            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                loading(true)
                api.acc_apat(cekapat.id!!).enqueue(object : Callback<PostDataResponse> {
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