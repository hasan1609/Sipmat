package com.sipmat.sipmat.admin.hydrant.item

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.sipmat.sipmat.QRcodeActivity
import com.sipmat.sipmat.R
import com.sipmat.sipmat.databinding.ActivityTambahHydrantBinding
import com.sipmat.sipmat.model.PostDataResponse
import com.sipmat.sipmat.model.hydrant.HydrantModel
import com.sipmat.sipmat.webservice.ApiClient
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TambahHydrantActivity : AppCompatActivity(),AnkoLogger{
    var lokasi: String? = null
    var id: Int? = null
    var kode: String? = null
    var no_box: String? = null
    var api = ApiClient.instance()
    lateinit var progressDialog: ProgressDialog
    var hydrantmodel : HydrantModel? = null


    lateinit var binding : ActivityTambahHydrantBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_tambah_hydrant)
        binding.lifecycleOwner = this
        progressDialog = ProgressDialog(this)

        val gson = Gson()
        hydrantmodel = gson.fromJson(intent.getStringExtra("HydrantModel"), HydrantModel::class.java)


        if (hydrantmodel != null) {
            binding.lyKodehydrant.visibility = View.GONE
            lokasi = hydrantmodel!!.lokasi
            kode = hydrantmodel!!.kode
            no_box = hydrantmodel!!.noBox
            id = hydrantmodel!!.id

            binding.edtlokasi.setText(lokasi)
            binding.edtnoBox.setText(no_box)

            binding.btnedit.visibility = View.VISIBLE
            binding.btnsubmit.visibility = View.INVISIBLE
            binding.btnedit.setOnClickListener {
                val lokasi = binding.edtlokasi.text.toString().trim()
                val no_box = binding.edtnoBox.text.toString().trim()
                if (kode != null && lokasi.isNotEmpty() && no_box.isNotEmpty()) {
                    loading(true)
                    api.updatehydrant(
                        kode!!,
                        no_box,
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
                                    toast("update hydrant berhasil")
                                    finish()
                                } else {
                                    loading(false)
                                    Snackbar.make(it, "tambah hydrant gagal", Snackbar.LENGTH_SHORT)
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
            binding.lyKodehydrant.visibility = View.VISIBLE
            binding.scanqr.visibility = View.VISIBLE
            binding.txtkodehydrant.text = "-"
            binding.scanqr.setOnClickListener {
                startActivity<QRcodeActivity>()
            }

            binding.btnsubmit.setOnClickListener {
                val lokasi = binding.edtlokasi.text.toString().trim()
                val no_box = binding.edtnoBox.text.toString().trim()
                if (QRcodeActivity.hasilqrcode != null && lokasi.isNotEmpty() && no_box.isNotEmpty()) {
                    loading(true)
                    api.hydrant(
                        QRcodeActivity.hasilqrcode!!,
                        no_box,
                        lokasi
                    ).enqueue(object : Callback<PostDataResponse> {
                        override fun onResponse(
                            call: Call<PostDataResponse>,
                            response: Response<PostDataResponse>
                        ) {
                            try {
                                if (response.body()!!.sukses == 1) {
                                    loading(false)
                                    toast("tambah hydrant berhasil")
                                    finish()
                                } else {
                                    loading(false)
                                    Snackbar.make(it, "tambah hydrant gagal", Snackbar.LENGTH_SHORT)
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
            binding.txtkodehydrant.text = QRcodeActivity.hasilqrcode.toString()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        QRcodeActivity.hasilqrcode = null
    }
}