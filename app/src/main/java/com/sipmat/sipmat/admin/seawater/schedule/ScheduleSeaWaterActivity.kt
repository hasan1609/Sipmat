package com.sipmat.sipmat.admin.seawater.schedule

import android.app.DatePickerDialog
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.sipmat.sipmat.R
import com.sipmat.sipmat.adapter.seawater.ScheduleSeaWaterAdapter
import com.sipmat.sipmat.databinding.ActivityScheduleSeaWaterBinding
import com.sipmat.sipmat.model.PostDataResponse
import com.sipmat.sipmat.model.seawater.PostSeaWaterSchedule
import com.sipmat.sipmat.model.seawater.SeaWaterModel
import com.sipmat.sipmat.model.seawater.SeaWaterResponse
import com.sipmat.sipmat.webservice.ApiClient
import kotlinx.android.synthetic.main.activity_tambah_schedule.*
import org.jetbrains.anko.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class ScheduleSeaWaterActivity : AppCompatActivity(), AnkoLogger {
    lateinit var binding: ActivityScheduleSeaWaterBinding
    var api = ApiClient.instance()
    lateinit var progressDialog: ProgressDialog
    private lateinit var mAdapter: ScheduleSeaWaterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_schedule_sea_water)
        binding.lifecycleOwner = this
        progressDialog = ProgressDialog(this)

        binding.btntambah.setOnClickListener {
            startActivity<TambahScheduleSeaWaterActivity>()
        }
        binding.btncari.setOnClickListener {
            val tahun = binding.edttahun.text.toString().trim()
            val tw = binding.spntw.selectedItem.toString()
            if (tahun.isNotEmpty()) {
                getschedule(tw, tahun)
            }
        }

    }


    fun getschedule(tw: String, tahun: String) {
        binding.rvscheduleseawater.layoutManager = LinearLayoutManager(this)
        binding.rvscheduleseawater.setHasFixedSize(true)
        (binding.rvscheduleseawater.layoutManager as LinearLayoutManager).orientation =
            LinearLayoutManager.VERTICAL
        api.get_seawater(tw, tahun)
            .enqueue(object : Callback<SeaWaterResponse> {
                override fun onResponse(
                    call: Call<SeaWaterResponse>,
                    response: Response<SeaWaterResponse>
                ) {
                    try {
                        if (response.isSuccessful) {
                            val notesList = mutableListOf<SeaWaterModel>()
                            val data = response.body()
                            for (hasil in data!!.data!!) {
                                notesList.add(hasil)
                                mAdapter = ScheduleSeaWaterAdapter(notesList)
                                binding.rvscheduleseawater.adapter = mAdapter
                                mAdapter.setDialog(object : ScheduleSeaWaterAdapter.Dialog {
                                    override fun onClick(position: Int, note: SeaWaterModel) {

                                        val builder = AlertDialog.Builder(this@ScheduleSeaWaterActivity)
                                        builder.setMessage("Hapus Schedule ? ")
                                        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                                            loading(true)
                                            api.hapus_seawater(note.id!!).enqueue(object :
                                                Callback<PostDataResponse> {
                                                override fun onResponse(
                                                    call: Call<PostDataResponse>,
                                                    response: Response<PostDataResponse>
                                                ) {
                                                    try {
                                                        if (response.body()!!.sukses == 1) {
                                                            loading(false)
                                                            toast("hapus seawater berhasil")
                                                            getschedule(note.tw!!, note.tahun!!)
                                                        } else {
                                                            loading(false)
                                                            toast("hapus seawater gagal")
                                                        }


                                                    } catch (e: Exception) {
                                                        progressDialog.dismiss()
                                                        info { "dinda ${e.message}${response.code()} " }
                                                    }

                                                }

                                                override fun onFailure(
                                                    call: Call<PostDataResponse>,
                                                    t: Throwable
                                                ) {
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

                override fun onFailure(call: Call<SeaWaterResponse>, t: Throwable) {
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