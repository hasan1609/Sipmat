package com.sipmat.sipmat.admin.pencahayaan.schedule

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.sipmat.sipmat.R
import com.sipmat.sipmat.adapter.pencahayaan.SchedulePencahayaanAdapter
import com.sipmat.sipmat.databinding.ActivitySchedulePencahayaanBinding
import com.sipmat.sipmat.model.PostDataResponse
import com.sipmat.sipmat.model.pencahayaan.SchedulePencahayaanModel
import com.sipmat.sipmat.model.pencahayaan.SchedulePencahayaanResponse
import com.sipmat.sipmat.webservice.ApiClient
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SchedulePencahayaanActivity : AppCompatActivity(), AnkoLogger {
    lateinit var binding: ActivitySchedulePencahayaanBinding
    var api = ApiClient.instance()
    lateinit var progressDialog: ProgressDialog
    private lateinit var mAdapter: SchedulePencahayaanAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_schedule_pencahayaan)
        binding.lifecycleOwner = this
        progressDialog = ProgressDialog(this)

        binding.btntambah.setOnClickListener {
            startActivity<TambahSchedulePencahayaanActivity>()
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
        binding.rvschedulepencahayaan.layoutManager = LinearLayoutManager(this)
        binding.rvschedulepencahayaan.setHasFixedSize(true)
        (binding.rvschedulepencahayaan.layoutManager as LinearLayoutManager).orientation =
            LinearLayoutManager.VERTICAL
        api.getschedule_pencahayaan(tw, tahun)
            .enqueue(object : Callback<SchedulePencahayaanResponse> {
                override fun onResponse(
                    call: Call<SchedulePencahayaanResponse>,
                    response: Response<SchedulePencahayaanResponse>
                ) {
                    try {
                        if (response.isSuccessful) {
                            val notesList = mutableListOf<SchedulePencahayaanModel>()
                            val data = response.body()
                            for (hasil in data!!.data!!) {
                                notesList.add(hasil)
                                mAdapter = SchedulePencahayaanAdapter(notesList)
                                binding.rvschedulepencahayaan.adapter = mAdapter
                                mAdapter.setDialog(object : SchedulePencahayaanAdapter.Dialog {


                                    override fun onClick(position: Int, note: SchedulePencahayaanModel) {

                                        val builder = AlertDialog.Builder(this@SchedulePencahayaanActivity)
                                        builder.setMessage("Hapus Schedule ? ")
                                        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                                            loading(true)
                                            api.hapus_schedule_pencahayaan(note.id!!).enqueue(object :
                                                Callback<PostDataResponse> {
                                                override fun onResponse(
                                                    call: Call<PostDataResponse>,
                                                    response: Response<PostDataResponse>
                                                ) {
                                                    try {
                                                        if (response.body()!!.sukses == 1) {
                                                            loading(false)
                                                            toast("hapus pencahayaan berhasil")
                                                            getschedule(note.tw!!, note.tahun!!)
                                                        } else {
                                                            loading(false)
                                                            toast("hapus pencahayaan gagal")
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

                override fun onFailure(call: Call<SchedulePencahayaanResponse>, t: Throwable) {
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