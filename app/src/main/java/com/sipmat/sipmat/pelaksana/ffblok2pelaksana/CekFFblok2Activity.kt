package com.sipmat.sipmat.pelaksana.ffblok2pelaksana

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.sipmat.sipmat.R
import com.sipmat.sipmat.adapter.ffblok.FFblokPelaksanaAdapter
import com.sipmat.sipmat.adapter.ffblok2.FFblok2PelaksanaAdapter
import com.sipmat.sipmat.databinding.ActivityCekFfblok2Binding
import com.sipmat.sipmat.databinding.ActivityCekFfblokBinding
import com.sipmat.sipmat.model.ffblok.FFBlokModel
import com.sipmat.sipmat.model.ffblok.FFBlokResponse
import com.sipmat.sipmat.model.ffblok2.FFBlok2Model
import com.sipmat.sipmat.model.ffblok2.FFBlok2Response
import com.sipmat.sipmat.webservice.ApiClient
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CekFFblok2Activity : AppCompatActivity(), AnkoLogger {

    lateinit var binding: ActivityCekFfblok2Binding
    var api = ApiClient.instance()
    private lateinit var mAdapter: FFblok2PelaksanaAdapter
    lateinit var progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cek_ffblok2)
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
        loading(true)
        api.ffblok2_pelaksana()
            .enqueue(object : Callback<FFBlok2Response> {
                override fun onResponse(
                    call: Call<FFBlok2Response>,
                    response: Response<FFBlok2Response>
                ) {
                    try {
                        if (response.isSuccessful) {
                            loading(false)
                            if(response.body()!!.data!!.isNotEmpty()) {
                            val notesList = mutableListOf<FFBlok2Model>()
                            val data = response.body()
                            for (hasil in data!!.data!!) {
                                notesList.add(hasil)
                                mAdapter =
                                    FFblok2PelaksanaAdapter(notesList, this@CekFFblok2Activity)
                                binding.rvffblokpelaksana.adapter = mAdapter
                                mAdapter.setDialog(object : FFblok2PelaksanaAdapter.Dialog {
                                    override fun onClick(
                                        position: Int,
                                        note: FFBlok2Model
                                    ) {
                                        val builder = AlertDialog.Builder(this@CekFFblok2Activity)
                                        builder.setMessage("Cek ffblok 2 ? ")
                                        builder.setPositiveButton("Cek ffblok 2") { dialog, which ->
                                            val gson = Gson()
                                            val noteJson = gson.toJson(note)
                                            startActivity<DetailCekFFblok2Activity>("cekffblok2" to noteJson)
                                        }


                                        builder.setNegativeButton("Cancel ?") { dialog, which ->

                                        }

                                        builder.show()

                                    }

                                })
                                mAdapter.notifyDataSetChanged()
                            }
                            }else{
                                binding.tvkosong.visibility = View.VISIBLE
                            }
                        } else {
                            loading(false)
                            toast("gagal mendapatkan response")
                        }
                    } catch (e: Exception) {
                        loading(false)
                        info { "dinda ${e.message}" }
                    }
                }

                override fun onFailure(call: Call<FFBlok2Response>, t: Throwable) {
                    loading(false)
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