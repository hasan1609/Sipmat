package com.sipmat.sipmat.admin.apat

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.sipmat.sipmat.QRcodeActivity
import com.sipmat.sipmat.R
import com.sipmat.sipmat.databinding.ActivityTambahApatBinding
import com.sipmat.sipmat.model.PostDataResponse
import com.sipmat.sipmat.model.apat.ApatModel
import com.sipmat.sipmat.webservice.ApiClient
import kotlinx.android.synthetic.main.activity_tambah_apar.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TambahApatActivity : AppCompatActivity(),AnkoLogger {
    var lokasi: String? = null
    var id: Int? = null
    var kode: String? = null
    var no_bak: String? = null
    var api = ApiClient.instance()
    lateinit var progressDialog: ProgressDialog
    var apatmodel : ApatModel? = null


    lateinit var binding : ActivityTambahApatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_tambah_apat)
        binding.lifecycleOwner = this
        progressDialog = ProgressDialog(this)

        val gson = Gson()
        apatmodel = gson.fromJson(intent.getStringExtra("apatmodel"), ApatModel::class.java)


        if (apatmodel != null) {
            binding.lyKodeapat.visibility = View.GONE
            lokasi = apatmodel!!.lokasi
            kode = apatmodel!!.kode
            no_bak = apatmodel!!.noBak
            id = apatmodel!!.id

            binding.edtlokasi.setText(lokasi)
            binding.edtnoBak.setText(no_bak)

            binding.btnedit.visibility = View.VISIBLE
            binding.btnsubmit.visibility = View.INVISIBLE
            binding.btnedit.setOnClickListener {
                val lokasi = binding.edtlokasi.text.toString().trim()
                val nobak = binding.edtnoBak.text.toString().trim()
                if (kode != null && lokasi.isNotEmpty() && nobak.isNotEmpty()) {
                    loading(true)
                    api.updateapat(
                        id!!,
                        kode!!,
                        nobak,
                        lokasi
                    ).enqueue(object : Callback<PostDataResponse> {
                        override fun onResponse(
                            call: Call<PostDataResponse>,
                            response: Response<PostDataResponse>
                        ) {
                            try {
                                if (response.body()!!.sukses == 1) {
                                    loading(false)
                                    toast("update apat berhasil")
                                    finish()
                                } else {
                                    loading(false)
                                    Snackbar.make(it, "tambah apat gagal", Snackbar.LENGTH_SHORT)
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
            binding.lyKodeapat.visibility = View.VISIBLE
            binding.scanqr.visibility = View.VISIBLE
            binding.txtkodeapat.text = "-"
            binding.scanqr.setOnClickListener {
                startActivity<QRcodeActivity>()
            }

            binding.btnsubmit.setOnClickListener {
                val lokasi = binding.edtlokasi.text.toString().trim()
                val no_bak = binding.edtnoBak.text.toString().trim()
                if (QRcodeActivity.hasilqrcode != null && lokasi.isNotEmpty() && no_bak.isNotEmpty()) {
                    loading(true)
                    api.apat(
                        QRcodeActivity.hasilqrcode!!,
                        no_bak,
                        lokasi
                    ).enqueue(object : Callback<PostDataResponse> {
                        override fun onResponse(
                            call: Call<PostDataResponse>,
                            response: Response<PostDataResponse>
                        ) {
                            try {
                                if (response.body()!!.sukses == 1) {
                                    loading(false)
                                    toast("tambah apat berhasil")
                                    finish()
                                } else {
                                    loading(false)
                                    Snackbar.make(it, "tambah apat gagal", Snackbar.LENGTH_SHORT)
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
            binding.txtkodeapat.text = QRcodeActivity.hasilqrcode.toString()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        QRcodeActivity.hasilqrcode = null
    }
}
