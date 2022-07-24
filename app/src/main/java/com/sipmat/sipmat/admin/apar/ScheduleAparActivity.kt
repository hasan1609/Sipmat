package com.sipmat.sipmat.admin.apar

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.sipmat.sipmat.R
import com.sipmat.sipmat.adapter.ScheduleAparAdapter
import com.sipmat.sipmat.model.*
import com.sipmat.sipmat.webservice.ApiClient
import kotlinx.android.synthetic.main.activity_schedule_apar.*
import kotlinx.android.synthetic.main.activity_schedule_apar.btntambah
import org.jetbrains.anko.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScheduleAparActivity : AppCompatActivity(),AnkoLogger {
    private lateinit var mAdapter: ScheduleAparAdapter
    lateinit var progressDialog: ProgressDialog
    var api = ApiClient.instance()
    var triwulan : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_apar)
        progressDialog = ProgressDialog(this)

        tw()

        btntambah.setOnClickListener {
            startActivity<TambahScheduleActivity>()
        }

        btncari.setOnClickListener {
            val tahun = edttahun.text.toString().trim()
            if (triwulan!=null && tahun.isNotEmpty()){
                getschedule(triwulan!!,tahun)
            }
        }
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

    fun getschedule(tw : String,tahun : String){
        rvscheduleapar.layoutManager = LinearLayoutManager(this)
        rvscheduleapar.setHasFixedSize(true)
        (rvscheduleapar.layoutManager as LinearLayoutManager).orientation =
            LinearLayoutManager.VERTICAL
        api.getapar_pick(tw, tahun)
            .enqueue(object : Callback<ScheduleResponse> {
                override fun onResponse(
                    call: Call<ScheduleResponse>,
                    response: Response<ScheduleResponse>
                ) {
                    try {
                        if (response.isSuccessful) {
                            val notesList = mutableListOf<ScheduleModel>()
                            val data = response.body()
                            for (hasil in data!!.data!!) {
                                notesList.add(hasil)
                                mAdapter = ScheduleAparAdapter(notesList, this@ScheduleAparActivity)
                                rvscheduleapar.adapter = mAdapter
                                mAdapter.setDialog(object : ScheduleAparAdapter.Dialog{

                                    override fun onClick(position: Int, note: ScheduleModel) {
                                        val builder = AlertDialog.Builder(this@ScheduleAparActivity)
                                        builder.setMessage("Hapus Schedule ? ")
                                        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                                            loading(true)
                                            api.hapus_schedule_apar(note.id!!).enqueue(object :
                                                Callback<PostDataResponse> {
                                                override fun onResponse(
                                                    call: Call<PostDataResponse>,
                                                    response: Response<PostDataResponse>
                                                ) {
                                                    try {
                                                        if (response.body()!!.sukses == 1) {
                                                            loading(false)
                                                            toast("hapus apar berhasil")
                                                            getschedule(note.tw!!,note.tahun!!)
                                                        } else {
                                                            loading(false)
                                                            toast("hapus apar gagal")
                                                        }


                                                    }catch (e : Exception){
                                                        progressDialog.dismiss()
                                                        info { "dinda ${e.message }${response.code()} " }
                                                    }

                                                }

                                                override fun onFailure(call: Call<PostDataResponse>, t: Throwable) {
                                                    loading(false)
                                                    toast("kesalahan jaringan")

                                                }

                                            })

                                        }

                                        builder.setNegativeButton(android.R.string.no) { dialog, which ->
                                            toast("tidak")
                                        }

                                        builder.show()


                                    }

                                })
                                mAdapter.notifyDataSetChanged()
                            }
                        } else {
                            toast("gagal mendapatkan response")
                        }
                    } catch (e: Exception) {
                        info { "dinda ${e.message}" }
                    }
                }

                override fun onFailure(call: Call<ScheduleResponse>, t: Throwable) {
                    info { "dinda ${t.message}" }
                }

            })



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