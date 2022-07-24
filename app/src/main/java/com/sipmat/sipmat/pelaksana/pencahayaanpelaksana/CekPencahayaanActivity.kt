package com.sipmat.sipmat.pelaksana.pencahayaanpelaksana

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.sipmat.sipmat.R
import com.sipmat.sipmat.adapter.pencahayaan.PencahayaanPelaksanaAdapter
import com.sipmat.sipmat.databinding.ActivityCekPencahayaanBinding
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

class CekPencahayaanActivity : AppCompatActivity(), AnkoLogger {

    lateinit var binding: ActivityCekPencahayaanBinding
    var api = ApiClient.instance()
    private lateinit var mAdapter: PencahayaanPelaksanaAdapter
    lateinit var progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cek_pencahayaan)
        binding.lifecycleOwner = this

        progressDialog = ProgressDialog(this)
    }

    override fun onStart() {
        super.onStart()
        getapatpelaksana()
    }


    fun getapatpelaksana() {
        binding.rvpencahayaanpelaksana.layoutManager = LinearLayoutManager(this)
        binding.rvpencahayaanpelaksana.setHasFixedSize(true)
        (binding.rvpencahayaanpelaksana.layoutManager as LinearLayoutManager).orientation =
            LinearLayoutManager.VERTICAL
        api.getschedule_pelaksana_pencahayaan()
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
                                mAdapter =
                                    PencahayaanPelaksanaAdapter(notesList, this@CekPencahayaanActivity)
                                binding.rvpencahayaanpelaksana.adapter = mAdapter
                                mAdapter.setDialog(object : PencahayaanPelaksanaAdapter.Dialog {


                                    override fun onClick(
                                        position: Int,
                                        note: SchedulePencahayaanModel
                                    ) {
                                        val builder = AlertDialog.Builder(this@CekPencahayaanActivity)
                                        builder.setMessage("Cek pencahayaan ? ")
                                        builder.setPositiveButton("Cek pencahayaan") { dialog, which ->
                                            val gson = Gson()
                                            val noteJson = gson.toJson(note)
                                            startActivity<DetailCekPencahayaanActivity>("cekpencahayaan" to noteJson)
                                        }


                                        builder.setNegativeButton("Cancel ?") { dialog, which ->

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

}