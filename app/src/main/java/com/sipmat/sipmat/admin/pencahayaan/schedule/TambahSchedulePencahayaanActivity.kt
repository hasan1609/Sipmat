package com.sipmat.sipmat.admin.pencahayaan.schedule

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
import com.sipmat.sipmat.admin.kebisingan.schedule.KebisinganAdapter
import com.sipmat.sipmat.databinding.ActivityTambahSchedulePencahayaanBinding
import com.sipmat.sipmat.model.AparModel
import com.sipmat.sipmat.model.AparResponse
import com.sipmat.sipmat.model.PostDataResponse
import com.sipmat.sipmat.model.pencahayaan.PencahayaanModel
import com.sipmat.sipmat.model.pencahayaan.PencahayaanPickResponse
import com.sipmat.sipmat.model.pencahayaan.PencahayaanResponse
import com.sipmat.sipmat.model.pencahayaan.PostSchedulePencahayaanResponse
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

class TambahSchedulePencahayaanActivity : AppCompatActivity(), AnkoLogger {
    var tanggal_cek : String? = null
    lateinit var progressDialog: ProgressDialog
    var api = ApiClient.instance()
    var lokasi_pencahayaan : String? = null
    var nama_pencahayaan : String? = null
    var id_pencahayaan : Int? = null
    lateinit var pencahayaanAdapter : PencahayaanAdapter
    var sb: StringBuilder? = null

    lateinit var binding : ActivityTambahSchedulePencahayaanBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_tambah_schedule_pencahayaan)
        binding.lifecycleOwner = this
        progressDialog = ProgressDialog(this)

        binding.txtTanggalcek.setOnClickListener {
            tanggalmulai()
        }

        binding.btntambah.setOnClickListener {
            sb = StringBuilder()
            var i = 0
            while (i < pencahayaanAdapter.lista.size) {
                val spiritualTeacher = pencahayaanAdapter.lista[i]
                sb!!.append(spiritualTeacher.kode)
                if (i != pencahayaanAdapter.lista.size - 1) {
                    sb!!.append("n")
                }
                i++

            }
            val tahun = edttahun.text.toString().trim()
            val tw = binding.spntw.selectedItem.toString()
            if (tw.isNotEmpty() && tanggal_cek!=null && pencahayaanAdapter.lista.size > 0 && tahun.isNotEmpty()){
                var body = PostSchedulePencahayaanResponse(tw,tahun,sb.toString(),tanggal_cek)
                loading(true)
                api.schedule_pencahayaan(body).enqueue(object :
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
        pilihPencahayaan()

    }

    private fun pilihPencahayaan() {
        binding.rcCbPencahayaan.setHasFixedSize(true)
        binding.rcCbPencahayaan.layoutManager = LinearLayoutManager(this)
        (binding.rcCbPencahayaan.layoutManager as LinearLayoutManager).orientation =
            LinearLayoutManager.VERTICAL
        api.getpencahayaan().enqueue(object : Callback<PencahayaanResponse> {
            override fun onFailure(call: Call<PencahayaanResponse>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<PencahayaanResponse>,
                response: Response<PencahayaanResponse>
            ) {
                if (response.isSuccessful) {
                    val notesList = mutableListOf<PencahayaanModel>()
                    val data = response.body()
                    for (hasil in data!!.data!!) {
                        notesList.add(hasil)
                        pencahayaanAdapter = PencahayaanAdapter(notesList, this@TambahSchedulePencahayaanActivity)
                        binding.rcCbPencahayaan.adapter = pencahayaanAdapter
                        pencahayaanAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
        )
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