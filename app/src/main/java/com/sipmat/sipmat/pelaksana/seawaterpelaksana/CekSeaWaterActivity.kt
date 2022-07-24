package com.sipmat.sipmat.pelaksana.seawaterpelaksana

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.sipmat.sipmat.R
import com.sipmat.sipmat.adapter.seawater.SeaWaterPelaksanaAdapter
import com.sipmat.sipmat.databinding.ActivityCekSeaWaterBinding
import com.sipmat.sipmat.model.seawater.HasilSeaWaterResponse
import com.sipmat.sipmat.model.seawater.SeaWaterModel
import com.sipmat.sipmat.webservice.ApiClient
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CekSeaWaterActivity : AppCompatActivity(), AnkoLogger {

    lateinit var binding: ActivityCekSeaWaterBinding
    var api = ApiClient.instance()
    private lateinit var mAdapter: SeaWaterPelaksanaAdapter
    lateinit var progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cek_sea_water)
        binding.lifecycleOwner = this

        progressDialog = ProgressDialog(this)
    }

    override fun onStart() {
        super.onStart()
        getapatpelaksana()
    }


    fun getapatpelaksana() {
        binding.rvseawaterpelaksana.layoutManager = LinearLayoutManager(this)
        binding.rvseawaterpelaksana.setHasFixedSize(true)
        (binding.rvseawaterpelaksana.layoutManager as LinearLayoutManager).orientation =
            LinearLayoutManager.VERTICAL
        api.seawater_pelaksana()
            .enqueue(object : Callback<HasilSeaWaterResponse> {
                override fun onResponse(
                    call: Call<HasilSeaWaterResponse>,
                    response: Response<HasilSeaWaterResponse>
                ) {
                    try {
                        if (response.isSuccessful) {
                            val notesList = mutableListOf<SeaWaterModel>()
                            val data = response.body()
                            for (hasil in data!!.data!!) {
                                notesList.add(hasil)
                                mAdapter =
                                    SeaWaterPelaksanaAdapter(notesList, this@CekSeaWaterActivity)
                                binding.rvseawaterpelaksana.adapter = mAdapter
                                mAdapter.setDialog(object : SeaWaterPelaksanaAdapter.Dialog {
                                    override fun onClick(
                                        position: Int,
                                        note: SeaWaterModel
                                    ) {
                                        val builder = AlertDialog.Builder(this@CekSeaWaterActivity)
                                        builder.setMessage("Cek seawater ? ")
                                        builder.setPositiveButton("Cek seawater") { dialog, which ->
                                            val gson = Gson()
                                            val noteJson = gson.toJson(note)
                                            startActivity<DetailCekSeawaterActivity>("cekseawater" to noteJson)
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

                override fun onFailure(call: Call<HasilSeaWaterResponse>, t: Throwable) {
                    info { "dinda ${t.message}" }
                }

            })


    }

}