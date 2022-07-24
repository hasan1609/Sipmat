package com.sipmat.sipmat.pelaksana.apatpelaksana

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.sipmat.sipmat.R
import com.sipmat.sipmat.adapter.apat.ApatPelaksanaAdapter
import com.sipmat.sipmat.databinding.ActivityCekApatBinding
import com.sipmat.sipmat.model.apat.ScheduleApatPelaksanaModel
import com.sipmat.sipmat.model.apat.ScheduleApatPelaksanaResponse
import com.sipmat.sipmat.webservice.ApiClient
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CekApatActivity : AppCompatActivity(), AnkoLogger {

    lateinit var binding: ActivityCekApatBinding
    var api = ApiClient.instance()
    private lateinit var mAdapter: ApatPelaksanaAdapter
    lateinit var progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cek_apat)
        binding.lifecycleOwner = this

        progressDialog = ProgressDialog(this)
    }

    override fun onStart() {
        super.onStart()
        getapatpelaksana()
    }



    fun getapatpelaksana() {
        binding.rvapatpelaksana.layoutManager = LinearLayoutManager(this)
        binding.rvapatpelaksana.setHasFixedSize(true)
        (binding.rvapatpelaksana.layoutManager as LinearLayoutManager).orientation =
            LinearLayoutManager.VERTICAL
        api.getschedule_pelaksana_apat()
            .enqueue(object : Callback<ScheduleApatPelaksanaResponse> {
                override fun onResponse(
                    call: Call<ScheduleApatPelaksanaResponse>,
                    response: Response<ScheduleApatPelaksanaResponse>
                ) {
                    try {
                        if (response.isSuccessful) {
                            val notesList = mutableListOf<ScheduleApatPelaksanaModel>()
                            val data = response.body()
                            for (hasil in data!!.data!!) {
                                notesList.add(hasil)
                                mAdapter = ApatPelaksanaAdapter(notesList, this@CekApatActivity)
                                binding.rvapatpelaksana.adapter = mAdapter
                                mAdapter.setDialog(object : ApatPelaksanaAdapter.Dialog {
                                    override fun onClick(
                                        position: Int,
                                        note: ScheduleApatPelaksanaModel
                                    ) {
                                        val builder = AlertDialog.Builder(this@CekApatActivity)
                                        builder.setMessage("Cek apat ? ")
                                        builder.setPositiveButton("Cek APAT") { dialog, which ->
                                            val gson = Gson()
                                            val noteJson = gson.toJson(note)
                                            startActivity<DetailCekApatActivity>("cekapat" to noteJson)
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

                override fun onFailure(call: Call<ScheduleApatPelaksanaResponse>, t: Throwable) {
                    info { "dinda ${t.message}" }
                }

            })


    }


}