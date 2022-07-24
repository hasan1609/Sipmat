package com.sipmat.sipmat.admin.pencahayaan.item

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.sipmat.sipmat.QRcodeActivity
import com.sipmat.sipmat.R
import com.sipmat.sipmat.databinding.ActivityTambahPencahayaanBinding
import com.sipmat.sipmat.model.PostDataResponse
import com.sipmat.sipmat.model.pencahayaan.PencahayaanModel
import com.sipmat.sipmat.webservice.ApiClient
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TambahPencahayaanActivity : AppCompatActivity(), AnkoLogger {
    var lokasi: String? = null
    var id: Int? = null
    var kode: String? = null
    var api = ApiClient.instance()
    lateinit var progressDialog: ProgressDialog
    var pencahayaanmodel : PencahayaanModel? = null


    lateinit var binding : ActivityTambahPencahayaanBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_tambah_pencahayaan)
        binding.lifecycleOwner = this
        progressDialog = ProgressDialog(this)

        val gson = Gson()
        pencahayaanmodel = gson.fromJson(intent.getStringExtra("pencahayaanModel"), PencahayaanModel::class.java)


        if (pencahayaanmodel != null) {
            binding.lyKodepencahayaan.visibility = View.GONE
            lokasi = pencahayaanmodel!!.lokasi
            kode = pencahayaanmodel!!.kode
            id = pencahayaanmodel!!.id

            binding.edtlokasi.setText(lokasi)

            binding.btnedit.visibility = View.VISIBLE
            binding.btnsubmit.visibility = View.INVISIBLE
            binding.btnedit.setOnClickListener {
                val lokasi = binding.edtlokasi.text.toString().trim()
                if (kode != null && lokasi.isNotEmpty()) {
                    loading(true)
                    api.updatepencahayaan(
                        lokasi,
                        id!!
                    ).enqueue(object : Callback<PostDataResponse> {
                        override fun onResponse(
                            call: Call<PostDataResponse>,
                            response: Response<PostDataResponse>
                        ) {
                            try {
                                if (response.body()!!.sukses == 1) {
                                    loading(false)
                                    toast("update pencahayaan berhasil")
                                    finish()
                                } else {
                                    loading(false)
                                    Snackbar.make(it, "tambah pencahayaan gagal", Snackbar.LENGTH_SHORT)
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
                } else {
                    Snackbar.make(it, "Jangan kosongi kolom", 3000).show()
                }
            }

        }
        else {
            binding.btnedit.visibility = View.INVISIBLE
            binding.btnsubmit.visibility = View.VISIBLE
            binding.lyKodepencahayaan.visibility = View.VISIBLE
            binding.scanqr.visibility = View.VISIBLE
            binding.txtkodepencahayaan.text = "-"
            binding.scanqr.setOnClickListener {
                startActivity<QRcodeActivity>()
            }

            binding.btnsubmit.setOnClickListener {
                val lokasi = binding.edtlokasi.text.toString().trim()
                if (QRcodeActivity.hasilqrcode != null && lokasi.isNotEmpty()) {
                    loading(true)
                    api.pencahayaan(
                        QRcodeActivity.hasilqrcode!!,
                        lokasi
                    ).enqueue(object : Callback<PostDataResponse> {
                        override fun onResponse(
                            call: Call<PostDataResponse>,
                            response: Response<PostDataResponse>
                        ) {
                            try {
                                if (response.body()!!.sukses == 1) {
                                    loading(false)
                                    toast("tambah pencahayaan berhasil")
                                    finish()
                                } else {
                                    loading(false)
                                    Snackbar.make(it, "tambah pencahayaan gagal", Snackbar.LENGTH_SHORT)
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
                } else {
                    Snackbar.make(it, "Jangan kosongi kolom", 3000).show()
                }
            }

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

    override fun onStart() {
        super.onStart()
        if (QRcodeActivity.hasilqrcode != null) {
            binding.txtkodepencahayaan.text = QRcodeActivity.hasilqrcode.toString()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        QRcodeActivity.hasilqrcode = null
    }
}