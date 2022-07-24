package com.sipmat.sipmat.admin.kebisingan.schedule

import android.app.DatePickerDialog
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.sipmat.sipmat.R
import com.sipmat.sipmat.admin.apar.AparAdapter
import com.sipmat.sipmat.admin.pencahayaan.schedule.PencahayaanAdapter
import com.sipmat.sipmat.databinding.ActivityTambahScheduleKebisinganBinding
import com.sipmat.sipmat.model.PostDataResponse
import com.sipmat.sipmat.model.kebisingan.KebisinganModel
import com.sipmat.sipmat.model.kebisingan.KebisinganPickResponse
import com.sipmat.sipmat.model.kebisingan.KebisinganResponse
import com.sipmat.sipmat.model.kebisingan.PostScheduleKebisingan
import com.sipmat.sipmat.model.pencahayaan.PencahayaanModel
import com.sipmat.sipmat.model.pencahayaan.PencahayaanResponse
import com.sipmat.sipmat.webservice.ApiClient
import kotlinx.android.synthetic.main.activity_tambah_schedule.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class TambahScheduleKebisinganActivity : AppCompatActivity(), AnkoLogger {
    var tanggal_cek : String? = null
    lateinit var progressDialog: ProgressDialog
    var api = ApiClient.instance()
    var lokasi_kebisingan : String? = null
    var nama_kebisingan : String? = null
    var id_kebisingan : Int? = null
    lateinit var kebisinganAdapter : KebisinganAdapter
    var sb: StringBuilder? = null

    lateinit var binding : ActivityTambahScheduleKebisinganBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_tambah_schedule_kebisingan)
        binding.lifecycleOwner = this
        progressDialog = ProgressDialog(this)

        binding.txtTanggalcek.setOnClickListener {
            tanggalmulai()
        }

        binding.btntambah.setOnClickListener {
            sb = StringBuilder()
            var i = 0
            while (i < kebisinganAdapter.lista.size) {
                val spiritualTeacher = kebisinganAdapter.lista[i]
                sb!!.append(spiritualTeacher.kode)
                if (i != kebisinganAdapter.lista.size - 1) {
                    sb!!.append("n")
                }
                i++

            }
            val tahun = edttahun.text.toString().trim()
            val tw = binding.spntw.selectedItem.toString()
            if (tw.isNotEmpty() && tanggal_cek!=null && kebisinganAdapter.lista.size > 0 && tahun.isNotEmpty()){
                var body = PostScheduleKebisingan(tw,tahun,sb.toString(),tanggal_cek)
                loading(true)
                api.schedule_kebisingan(body).enqueue(object :
                    Callback<PostDataResponse> {
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
                                Snackbar.make(it, "tambah schedule gagal", Snackbar.LENGTH_SHORT)
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
            }else{
                Snackbar.make(it,"Jangan kosongi kolom",3000).show()

            }

        }
        pilihKebisingan()
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

    private fun pilihKebisingan() {
        binding.rcCbKebisingan.setHasFixedSize(true)
        binding.rcCbKebisingan.layoutManager = LinearLayoutManager(this)
        (binding.rcCbKebisingan.layoutManager as LinearLayoutManager).orientation =
            LinearLayoutManager.VERTICAL
        api.getkebisingan().enqueue(object : Callback<KebisinganResponse> {
            override fun onFailure(call: Call<KebisinganResponse>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<KebisinganResponse>,
                response: Response<KebisinganResponse>
            ) {
                if (response.isSuccessful) {
                    val notesList = mutableListOf<KebisinganModel>()
                    val data = response.body()
                    for (hasil in data!!.data!!) {
                        notesList.add(hasil)
                        kebisinganAdapter = KebisinganAdapter(notesList, this@TambahScheduleKebisinganActivity)
                        binding.rcCbKebisingan.adapter = kebisinganAdapter
                        kebisinganAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
        )
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

}