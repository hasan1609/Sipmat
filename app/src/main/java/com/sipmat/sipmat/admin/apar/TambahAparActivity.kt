package com.sipmat.sipmat.admin.apar

import android.app.DatePickerDialog
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.google.android.material.snackbar.Snackbar
import com.sipmat.sipmat.QRcodeActivity
import com.sipmat.sipmat.R
import com.sipmat.sipmat.model.PostDataResponse
import com.sipmat.sipmat.webservice.ApiClient
import kotlinx.android.synthetic.main.activity_tambah_apar.*
import org.jetbrains.anko.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class TambahAparActivity : AppCompatActivity(), AnkoLogger {
    var jenis_apar: String? = null
    var lokasi: String? = null
    var id: Int? = null
    var kode: String? = null
    var api = ApiClient.instance()
    lateinit var progressDialog: ProgressDialog
    var tanggal_pengisian: String? = null
    var bundle: Bundle? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_apar)
        progressDialog = ProgressDialog(this)
        bundle = intent.extras

        if (bundle != null) {
            ly_kodeapar.visibility = View.GONE
            jenis_apar = bundle!!.getString("jenis")
            lokasi = bundle!!.getString("lokasi")
            tanggal_pengisian = bundle!!.getString("tanggal")
            id = bundle!!.getInt("id")
            kode = bundle!!.getString("kode")

            jenis_apar()
            edtlokasi.setText(lokasi)
            tglpengisian.setText(tanggal_pengisian)

            btnedit.visibility = View.VISIBLE
            btnsubmit.visibility = View.INVISIBLE
            btnedit.setOnClickListener {
                val lokasi = edtlokasi.text.toString().trim()
                if (jenis_apar != null && kode != null && lokasi.isNotEmpty() && tanggal_pengisian != null) {
                    loading(true)
                    api.updateapar(
                        id!!,
                        kode!!,
                        jenis_apar!!,
                        lokasi,
                        tanggal_pengisian!!
                    ).enqueue(object : Callback<PostDataResponse> {
                        override fun onResponse(
                            call: Call<PostDataResponse>,
                            response: Response<PostDataResponse>
                        ) {
                            try {
                                if (response.body()!!.sukses == 1) {
                                    loading(false)
                                    toast("update apar berhasil")
                                    finish()
                                } else {
                                    loading(false)
                                    Snackbar.make(it, "tambah apar gagal", Snackbar.LENGTH_SHORT)
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
            btnedit.visibility = View.INVISIBLE
            btnsubmit.visibility = View.VISIBLE
            jenis_apar()
            txtkodeapar.text = "-"
            ly_kodeapar.visibility = View.VISIBLE
            scanqr.setOnClickListener {
                startActivity<QRcodeActivity>()
            }

            btnsubmit.setOnClickListener {
                val lokasi = edtlokasi.text.toString().trim()
                if (jenis_apar != null && QRcodeActivity.hasilqrcode != null && lokasi.isNotEmpty() && tanggal_pengisian != null) {
                    loading(true)
                    api.tambahapar(
                        QRcodeActivity.hasilqrcode!!,
                        jenis_apar!!,
                        lokasi,
                        tanggal_pengisian!!
                    ).enqueue(object : Callback<PostDataResponse> {
                        override fun onResponse(
                            call: Call<PostDataResponse>,
                            response: Response<PostDataResponse>
                        ) {
                            try {
                                if (response.body()!!.sukses == 1) {
                                    loading(false)
                                    toast("tambah apar berhasil")
                                    finish()
                                } else {
                                    loading(false)
                                    Snackbar.make(it, "tambah apar gagal", Snackbar.LENGTH_SHORT)
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

        tglpengisian.setOnClickListener {
            tanggalmulai()
        }


    }

    private fun tanggalmulai() {
        val cal = Calendar.getInstance()
        val calend = Calendar.getInstance()
        val datesetLogger = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            calend.set(Calendar.YEAR, year)
            calend.set(Calendar.MONTH, month)
            calend.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            tglpengisian.text = SimpleDateFormat("yyyy-MM-dd").format(cal.time)
            tanggal_pengisian = SimpleDateFormat("yyyy-MM-dd").format(cal.time)

        }

        DatePickerDialog(
            this, datesetLogger,
            cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
        ).show()

    }

    fun jenis_apar() {
        if (bundle != null) {
            val jenisapar = arrayOf(jenis_apar!!, "CO2", "Dray powder")
            val spinner = find<Spinner>(R.id.spnjenis)
            if (spinner != null) {
                val adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item, jenisapar
                )
                spinner.adapter = adapter

                spinner.onItemSelectedListener = object :
                    AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View, position: Int, id: Long
                    ) {
                        jenis_apar = jenisapar[position]
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {
                        // write code to perform some action
                    }
                }

            }
        } else {
            val jenisapar = arrayOf("CO2", "Dray powder")
            val spinner = find<Spinner>(R.id.spnjenis)
            if (spinner != null) {
                val adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item, jenisapar
                )
                spinner.adapter = adapter

                spinner.onItemSelectedListener = object :
                    AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View, position: Int, id: Long
                    ) {
                        jenis_apar = jenisapar[position]
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {
                        // write code to perform some action
                    }
                }

            }
        }


    }

    override fun onStart() {
        super.onStart()
        if (QRcodeActivity.hasilqrcode != null) {
            txtkodeapar.text = QRcodeActivity.hasilqrcode.toString()
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