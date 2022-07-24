package com.sipmat.sipmat.admin.kebisingan.schedule

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.sipmat.sipmat.R

import com.sipmat.sipmat.databinding.ActivityCekKebisinganAdminBinding
import com.sipmat.sipmat.model.PostDataResponse
import com.sipmat.sipmat.model.kebisingan.HasilKebisinganModel
import com.sipmat.sipmat.webservice.ApiClient
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CekKebisinganAdminActivity : AppCompatActivity(), AnkoLogger {
    lateinit var cekkebisingan: HasilKebisinganModel
    lateinit var binding: ActivityCekKebisinganAdminBinding

    lateinit var progressDialog: ProgressDialog
    var api = ApiClient.instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cek_kebisingan_admin)
        binding.lifecycleOwner = this
        progressDialog = ProgressDialog(this)
        val gson = Gson()
        cekkebisingan =
            gson.fromJson(intent.getStringExtra("cekkebisingan"), HasilKebisinganModel::class.java)

        if (cekkebisingan.isStatus == 0 || cekkebisingan.isStatus ==2 || cekkebisingan.isStatus ==3){
            binding.btnApprove.visibility = View.GONE
        }
        if (cekkebisingan.isStatus ==1){
            binding.btnReturn.visibility = View.VISIBLE
        }


        binding.txtshift.text = "Shift : ${cekkebisingan.shift.toString()}"
        binding.txltglPemeriksa.text = "Tgl Pemeriksa : ${cekkebisingan.tanggalCek.toString()}"
        binding.txtketerangan.text = cekkebisingan.keterangan

        binding.txtkodekebisingan.text = "Kode kebisingan : ${cekkebisingan.kodeKebisingan}"
        binding.txtlokasi.text = "Lokasi : ${cekkebisingan.kebisingan!!.lokasi}"
        binding.txtdbx1.text = "${cekkebisingan.dbx1}"
        binding.txtdbx2.text = "${cekkebisingan.dbx2}"
        binding.txtdbx3.text = "${cekkebisingan.dbx3}"
        binding.txtdbrata2.text = "${cekkebisingan.dbrata2}"
        binding.txtstatus.text = "${cekkebisingan.isStatus}"
        binding.txtketerangan.text = "${cekkebisingan.keterangan}"

        binding.btnReturn.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("kebisingan")
            builder.setMessage("ACC kebisingan ? ")
            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                loading(true)
                api.return_kebisingan(cekkebisingan.id!!).enqueue(object : Callback<PostDataResponse> {
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
            builder.setTitle("kebisingan")
            builder.setMessage("ACC kebisingan ? ")
            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                loading(true)
                api.acc_kebisingan(cekkebisingan.id!!).enqueue(object : Callback<PostDataResponse> {
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