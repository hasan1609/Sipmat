package com.sipmat.sipmat.admin.apar

import android.app.DatePickerDialog
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.sipmat.sipmat.R
import com.sipmat.sipmat.model.AparModel
import com.sipmat.sipmat.model.AparPickResponse
import com.sipmat.sipmat.model.AparResponse
import com.sipmat.sipmat.model.PostDataResponse
import com.sipmat.sipmat.webservice.ApiClient
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.activity_tambah_schedule.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.find
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class TambahScheduleActivity : AppCompatActivity(),AnkoLogger {
    var triwulan : String? = null
    var tanggal_cek : String? = null
    lateinit var progressDialog: ProgressDialog
    var api = ApiClient.instance()
    var lokasi_apar : String? = null
    var nama_apar : String? = null
    var id_apar : Int? = null
    lateinit var aparAdapter : AparAdapter
    var sb: StringBuilder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_schedule)
        progressDialog = ProgressDialog(this)

        tw()
        txt_tanggalcek.setOnClickListener {
            tanggalmulai()
        }

        btntambah.setOnClickListener {
            sb = StringBuilder()
            var i = 0
            while (i < aparAdapter.lista.size) {
                val spiritualTeacher = aparAdapter.lista[i]
                sb!!.append(spiritualTeacher.kode)
                if (i != aparAdapter.lista.size - 1) {
                    sb!!.append("n")
                }
                i++

            }
            val tahun = edttahun.text.toString().trim()
            if (triwulan != null && tanggal_cek != null && aparAdapter.lista.size > 0 && tahun.isNotEmpty()) {
                loading(true)
                api.schedule_apar(sb.toString(), triwulan!!, tahun, tanggal_cek!!)
                    .enqueue(object : Callback<PostDataResponse> {
                        override fun onResponse(
                            call: Call<PostDataResponse>,
                            response: Response<PostDataResponse>
                        ) {
                            try {
                                if (response.body()!!.sukses == 1) {
                                    loading(false)
                                    toast("tambah schedule berhasil")
                                    finish()
                                } else {
                                    loading(false)
                                    Snackbar.make(
                                        it,
                                        "tambah schedule gagal",
                                        Snackbar.LENGTH_SHORT
                                    )
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
        pilihapar()
    }

    private fun pilihapar() {
        rc_cb_apar.setHasFixedSize(true)
        rc_cb_apar.layoutManager = LinearLayoutManager(this)
        (rc_cb_apar.layoutManager as LinearLayoutManager).orientation =
            LinearLayoutManager.VERTICAL
        api.getapar().enqueue(object : Callback<AparResponse> {
            override fun onFailure(call: Call<AparResponse>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<AparResponse>,
                response: Response<AparResponse>
            ) {
                if (response.isSuccessful) {
                    val notesList = mutableListOf<AparModel>()
                    val data = response.body()
                    for (hasil in data!!.data!!) {
                        notesList.add(hasil)
                        aparAdapter = AparAdapter(notesList, this@TambahScheduleActivity)
                        rc_cb_apar.adapter = aparAdapter
                        aparAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
        )
    }

    private fun tw() {
        val jenis_tw = arrayOf("I","II","III","IV")
        val spinner = find<Spinner>(R.id.spntw)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, jenis_tw
            )
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    triwulan = jenis_tw[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

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

            txt_tanggalcek.text = SimpleDateFormat("yyyy-MM-dd").format(cal.time)
            tanggal_cek = SimpleDateFormat("yyyy-MM-dd").format(cal.time)

        }

        DatePickerDialog(
            this, datesetLogger,
            cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
        ).show()

    }

    fun loading(status : Boolean){
        if (status){
            progressDialog.setTitle("Loading...")
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()
        }else{
            progressDialog.dismiss()
        }
    }
}