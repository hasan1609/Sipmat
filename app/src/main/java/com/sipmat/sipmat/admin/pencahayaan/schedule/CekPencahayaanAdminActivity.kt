package com.sipmat.sipmat.admin.pencahayaan.schedule

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.sipmat.sipmat.R
import com.sipmat.sipmat.databinding.ActivityCekPencahayaanAdminBinding
import com.sipmat.sipmat.model.PostDataResponse
import com.sipmat.sipmat.model.pencahayaan.HasilPencahayaanModel
import com.sipmat.sipmat.webservice.ApiClient
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CekPencahayaanAdminActivity : AppCompatActivity(), AnkoLogger {
    lateinit var cekpencahayaan: HasilPencahayaanModel
    lateinit var binding: ActivityCekPencahayaanAdminBinding

    lateinit var progressDialog: ProgressDialog
    var api = ApiClient.instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cek_pencahayaan_admin)
        binding.lifecycleOwner = this
        progressDialog = ProgressDialog(this)
        val gson = Gson()
        cekpencahayaan =
            gson.fromJson(intent.getStringExtra("cekpencahayaan"), HasilPencahayaanModel::class.java)

        if (cekpencahayaan.isStatus == 0 || cekpencahayaan.isStatus ==2 || cekpencahayaan.isStatus ==3){
            binding.btnApprove.visibility = View.GONE
        }
        if (cekpencahayaan.isStatus ==1){
            binding.btnReturn.visibility = View.VISIBLE
        }


        binding.txtshift.text = "Shift : ${cekpencahayaan.shift.toString()}"
        binding.txltglPemeriksa.text = "Tgl Pemeriksa : ${cekpencahayaan.tanggalCek.toString()}"
        binding.txtketerangan.text = cekpencahayaan.keterangan

        binding.txtkodepencahayaan.text = "Kode pencahayaan : ${cekpencahayaan.kodePencahayaan}"
        binding.txtlokasi.text = "Lokasi : ${cekpencahayaan.pencahayaan!!.lokasi}"
        binding.txtdbx1.text = "${cekpencahayaan.lux1}"
        binding.txtdbx2.text = "${cekpencahayaan.lux2}"
        binding.txtdbx3.text = "${cekpencahayaan.lux3}"
        binding.txtdbrata2.text = "${cekpencahayaan.luxrata2}"
        binding.txtnilaiMinimumLux.text = "${cekpencahayaan.nilaiMinimumLux}"
        binding.txtsumbercahaya.text = "${cekpencahayaan.sumberPencahayaan}"
        binding.txtketerangan.text = "${cekpencahayaan.keterangan}"

        binding.btnReturn.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("pencahayaan")
            builder.setMessage("Return pencahayaan ? ")
            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                loading(true)
                api.return_pencahayaan(cekpencahayaan.id!!).enqueue(object :
                    Callback<PostDataResponse> {
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
                                Snackbar.make(it, "return   gagal", Snackbar.LENGTH_SHORT)
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
            builder.setTitle("pencahayaan")
            builder.setMessage("ACC pencahayaan ? ")
            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                loading(true)
                api.acc_pencahayaan(cekpencahayaan.id!!).enqueue(object : Callback<PostDataResponse> {
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