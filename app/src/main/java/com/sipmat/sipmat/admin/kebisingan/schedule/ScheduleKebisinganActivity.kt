package com.sipmat.sipmat.admin.kebisingan.schedule

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.sipmat.sipmat.R
import com.sipmat.sipmat.adapter.kebisingan.ScheduleKebisiganAdapter
import com.sipmat.sipmat.databinding.ActivityScheduleKebisinganBinding
import com.sipmat.sipmat.model.PostDataResponse
import com.sipmat.sipmat.model.kebisingan.ScheduleKebisinganModel
import com.sipmat.sipmat.model.kebisingan.ScheduleKebisinganResponse
import com.sipmat.sipmat.webservice.ApiClient
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScheduleKebisinganActivity : AppCompatActivity(), AnkoLogger {
    lateinit var binding: ActivityScheduleKebisinganBinding
    var api = ApiClient.instance()
    lateinit var progressDialog: ProgressDialog
    private lateinit var mAdapter: ScheduleKebisiganAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_schedule_kebisingan)
        binding.lifecycleOwner = this
        progressDialog = ProgressDialog(this)

        binding.btntambah.setOnClickListener {
            startActivity<TambahScheduleKebisinganActivity>()
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
        binding.rvschedulekebisingan.layoutManager = LinearLayoutManager(this)
        binding.rvschedulekebisingan.setHasFixedSize(true)
        (binding.rvschedulekebisingan.layoutManager as LinearLayoutManager).orientation =
            LinearLayoutManager.VERTICAL
        api.getschedule_kebisingan(tw, tahun)
            .enqueue(object : Callback<ScheduleKebisinganResponse> {
                override fun onResponse(
                    call: Call<ScheduleKebisinganResponse>,
                    response: Response<ScheduleKebisinganResponse>
                ) {
                    try {
                        if (response.isSuccessful) {
                            val notesList = mutableListOf<ScheduleKebisinganModel>()
                            val data = response.body()
                            for (hasil in data!!.data!!) {
                                notesList.add(hasil)
                                mAdapter = ScheduleKebisiganAdapter(notesList)
                                binding.rvschedulekebisingan.adapter = mAdapter
                                mAdapter.setDialog(object : ScheduleKebisiganAdapter.Dialog {


                                    override fun onClick(position: Int, note: ScheduleKebisinganModel) {

                                        val builder = AlertDialog.Builder(this@ScheduleKebisinganActivity)
                                        builder.setMessage("Hapus Schedule ? ")
                                        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                                            loading(true)
                                            api.hapus_schedule_kebisingan(note.id!!).enqueue(object :
                                                Callback<PostDataResponse> {
                                                override fun onResponse(
                                                    call: Call<PostDataResponse>,
                                                    response: Response<PostDataResponse>
                                                ) {
                                                    try {
                                                        if (response.body()!!.sukses == 1) {
                                                            loading(false)
                                                            toast("hapus kebisingan berhasil")
                                                            getschedule(note.tw!!, note.tahun!!)
                                                        } else {
                                                            loading(false)
                                                            toast("hapus kebisingan gagal")
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

                override fun onFailure(call: Call<ScheduleKebisinganResponse>, t: Throwable) {
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