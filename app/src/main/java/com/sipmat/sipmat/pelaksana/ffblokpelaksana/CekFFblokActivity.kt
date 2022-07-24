package com.sipmat.sipmat.pelaksana.ffblokpelaksana

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.sipmat.sipmat.R
import com.sipmat.sipmat.adapter.ffblok.FFblokPelaksanaAdapter
import com.sipmat.sipmat.databinding.ActivityCekFfblokBinding
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

class CekFFblokActivity : AppCompatActivity(), AnkoLogger {

    lateinit var binding: ActivityCekFfblokBinding
    var api = ApiClient.instance()
    private lateinit var mAdapter: FFblokPelaksanaAdapter
    lateinit var progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cek_ffblok)
        binding.lifecycleOwner = this

        progressDialog = ProgressDialog(this)
    }

    override fun onStart() {
        super.onStart()
        getapatpelaksana()
    }


    fun getapatpelaksana() {
        binding.rvffblokpelaksana.layoutManager = LinearLayoutManager(this)
        binding.rvffblokpelaksana.setHasFixedSize(true)
        (binding.rvffblokpelaksana.layoutManager as LinearLayoutManager).orientation =
            LinearLayoutManager.VERTICAL
        api.ffblok_pelaksana()
            .enqueue(object : Callback<FFBlokResponse> {
                override fun onResponse(
                    call: Call<FFBlokResponse>,
                    response: Response<FFBlokResponse>
                ) {
                    try {
                        if (response.isSuccessful) {
                            val notesList = mutableListOf<FFBlokModel>()
                            val data = response.body()
                            for (hasil in data!!.data!!) {
                                notesList.add(hasil)
                                mAdapter =
                                    FFblokPelaksanaAdapter(notesList, this@CekFFblokActivity)
                                binding.rvffblokpelaksana.adapter = mAdapter
                                mAdapter.setDialog(object : FFblokPelaksanaAdapter.Dialog {


                                    override fun onClick(
                                        position: Int,
                                        note: FFBlokModel
                                    ) {
                                        val builder = AlertDialog.Builder(this@CekFFblokActivity)
                                        builder.setMessage("Cek ffblok ? ")
                                        builder.setPositiveButton("Cek ffblok") { dialog, which ->
                                            val gson = Gson()
                                            val noteJson = gson.toJson(note)
                                            startActivity<DetailCekFFblokActivity>("cekffblok" to noteJson)
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

                override fun onFailure(call: Call<FFBlokResponse>, t: Throwable) {
                    info { "dinda ${t.message}" }
                }

            })


    }

}