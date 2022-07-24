package com.sipmat.sipmat.pelaksana.kebisinganpelaksana

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.sipmat.sipmat.R
import com.sipmat.sipmat.adapter.kebisingan.KebisinganPelaksanaAdapter
import com.sipmat.sipmat.databinding.ActivityCekKebisinganBinding
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

class CekKebisinganActivity : AppCompatActivity(), AnkoLogger {

    lateinit var binding: ActivityCekKebisinganBinding
    var api = ApiClient.instance()
    private lateinit var mAdapter: KebisinganPelaksanaAdapter
    lateinit var progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cek_kebisingan)
        binding.lifecycleOwner = this

        progressDialog = ProgressDialog(this)
    }

    override fun onStart() {
        super.onStart()
        getapatpelaksana()
    }


    fun getapatpelaksana() {
        binding.rvkebisinganpelaksana.layoutManager = LinearLayoutManager(this)
        binding.rvkebisinganpelaksana.setHasFixedSize(true)
        (binding.rvkebisinganpelaksana.layoutManager as LinearLayoutManager).orientation =
            LinearLayoutManager.VERTICAL
        api.getschedule_pelaksana_kebisingan()
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
                                mAdapter =
                                    KebisinganPelaksanaAdapter(notesList, this@CekKebisinganActivity)
                                binding.rvkebisinganpelaksana.adapter = mAdapter
                                mAdapter.setDialog(object : KebisinganPelaksanaAdapter.Dialog {


                                    override fun onClick(
                                        position: Int,
                                        note: ScheduleKebisinganModel
                                    ) {
                                        val builder = AlertDialog.Builder(this@CekKebisinganActivity)
                                        builder.setMessage("Cek kebisingan ? ")
                                        builder.setPositiveButton("Cek kebisingan") { dialog, which ->
                                            val gson = Gson()
                                            val noteJson = gson.toJson(note)
                                            startActivity<DetailCekKebisinganActivity>("cekkebisingan" to noteJson)
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

                override fun onFailure(call: Call<ScheduleKebisinganResponse>, t: Throwable) {
                    info { "dinda ${t.message}" }
                }

            })


    }

}