package com.sipmat.sipmat.admin.hydrant.schedule

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.sipmat.sipmat.R
import com.sipmat.sipmat.databinding.ActivityCekHydrantAdminBinding
import com.sipmat.sipmat.databinding.ActivityCekHydrantBinding
import com.sipmat.sipmat.model.PostDataResponse
import com.sipmat.sipmat.model.hydrant.HasilHydrantModel
import com.sipmat.sipmat.webservice.ApiClient
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CekHydrantAdminActivity : AppCompatActivity(),AnkoLogger {
    lateinit var cekhydrant: HasilHydrantModel
    lateinit var binding: ActivityCekHydrantAdminBinding

    lateinit var progressDialog: ProgressDialog
    var api = ApiClient.instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cek_hydrant_admin)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cek_hydrant_admin)
        binding.lifecycleOwner = this
        progressDialog = ProgressDialog(this)
        val gson = Gson()
        cekhydrant =
            gson.fromJson(intent.getStringExtra("cekhydrant"), HasilHydrantModel::class.java)

        if (cekhydrant.isStatus == 0 || cekhydrant.isStatus ==2 || cekhydrant.isStatus ==3){
            binding.btnApprove.visibility = View.GONE
        }
        if (cekhydrant.isStatus ==1){
            binding.btnReturn.visibility = View.VISIBLE
        }


        binding.txtshift.text = "Shift : ${cekhydrant.shift.toString()}"
        binding.txltglPemeriksa.text = "Tgl Pemeriksa : ${cekhydrant.tanggalCek.toString()}"
        binding.txtkodehydrant.text = "Kode hydrant : ${cekhydrant.kodeHydrant.toString()}"
        binding.txtno.text = "No Box Hydrant : ${cekhydrant.hydrant!!.noBox.toString()}"
        binding.txtlokasi.text = "Lokasi hydrant : ${cekhydrant.hydrant!!.lokasi.toString()}"

        binding.txtflashing.text = cekhydrant.flushing
        binding.txtmainvalve.text = cekhydrant.mainValve
        binding.txtdischarge.text = cekhydrant.discharge
        binding.txtkondisibox.text = cekhydrant.kondisiBox
        binding.txtkuncibox.text = cekhydrant.kunciBox
        binding.txtkuncif.text = cekhydrant.kunciF
        binding.txtselang.text = cekhydrant.selang
        binding.txtnozzle.text = cekhydrant.noozle
        binding.txthousekeeping.text = cekhydrant.houseKeeping
        binding.txtketerangan.text = cekhydrant.keterangan

        binding.btnReturn.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("hydrant")
            builder.setMessage("ACC hydrant ? ")
            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                loading(true)
                api.return_hydrant(cekhydrant.id!!).enqueue(object : Callback<PostDataResponse> {
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
            builder.setTitle("hydrant")
            builder.setMessage("ACC hydrant ? ")
            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                loading(true)
                api.acc_hydrant(cekhydrant.id!!).enqueue(object : Callback<PostDataResponse> {
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