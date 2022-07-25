package com.sipmat.sipmat.admin.edgblok2.schedule

import android.app.AlertDialog
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.sipmat.sipmat.R
import com.sipmat.sipmat.adapter.edgblok1.ScheduleEdgblok1Adapter
import com.sipmat.sipmat.adapter.edgblok2.ScheduleEdgblok2Adapter
import com.sipmat.sipmat.adapter.ffblok.ScheduleFFblokAdapter
import com.sipmat.sipmat.databinding.ActivityScheduleEdgblok1Binding
import com.sipmat.sipmat.databinding.ActivityScheduleEdgblok2Binding
import com.sipmat.sipmat.databinding.ActivityScheduleFfblokBinding
import com.sipmat.sipmat.model.PostDataResponse
import com.sipmat.sipmat.model.edgblok1.EdgBlokModel
import com.sipmat.sipmat.model.edgblok1.EdgBlokResponse
import com.sipmat.sipmat.model.edgblok2.EdgBlok2Model
import com.sipmat.sipmat.model.edgblok2.EdgBlok2Response
import com.sipmat.sipmat.model.ffblok.FFBlokModel
import com.sipmat.sipmat.model.ffblok.FFBlokResponse
import com.sipmat.sipmat.webservice.ApiClient
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScheduleEdgBlok2Activity : AppCompatActivity(), AnkoLogger {
    lateinit var binding: ActivityScheduleEdgblok2Binding
    var api = ApiClient.instance()
    lateinit var progressDialog: ProgressDialog
    private lateinit var mAdapter: ScheduleEdgblok2Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_schedule_edgblok2)
        binding.lifecycleOwner = this
        progressDialog = ProgressDialog(this)

        binding.btntambah.setOnClickListener {
            startActivity<TambahScheduleEdgblok2Activity>()
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
        binding.rvscheduleffblok.layoutManager = LinearLayoutManager(this)
        binding.rvscheduleffblok.setHasFixedSize(true)
        (binding.rvscheduleffblok.layoutManager as LinearLayoutManager).orientation =
            LinearLayoutManager.VERTICAL
        api.get_edgblok2(tw, tahun)
            .enqueue(object : Callback<EdgBlok2Response> {
                override fun onResponse(
                    call: Call<EdgBlok2Response>,
                    response: Response<EdgBlok2Response>
                ) {
                    try {
                        if (response.isSuccessful) {
                            val notesList = mutableListOf<EdgBlok2Model>()
                            val data = response.body()
                            for (hasil in data!!.data!!) {
                                notesList.add(hasil)
                                mAdapter = ScheduleEdgblok2Adapter(notesList)
                                binding.rvscheduleffblok.adapter = mAdapter
                                mAdapter.setDialog(object : ScheduleEdgblok2Adapter.Dialog {
                                    override fun onClick(position: Int, note: EdgBlok2Model) {

                                        val builder = AlertDialog.Builder(this@ScheduleEdgBlok2Activity)
                                        builder.setMessage("Hapus Schedule ? ")
                                        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                                            loading(true)
                                            api.hapus_edgblok2(note.id!!).enqueue(object :
                                                Callback<PostDataResponse> {
                                                override fun onResponse(
                                                    call: Call<PostDataResponse>,
                                                    response: Response<PostDataResponse>
                                                ) {
                                                    try {
                                                        if (response.body()!!.sukses == 1) {
                                                            loading(false)
                                                            toast("hapus Edg blok 2 berhasil")
                                                            getschedule(note.tw!!, note.tahun!!)
                                                        } else {
                                                            loading(false)
                                                            toast("hapus edg blok gagal")
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

                override fun onFailure(call: Call<EdgBlok2Response>, t: Throwable) {
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