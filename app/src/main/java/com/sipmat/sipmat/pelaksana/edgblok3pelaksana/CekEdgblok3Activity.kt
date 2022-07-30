package com.sipmat.sipmat.pelaksana.edgblok3pelaksana

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.sipmat.sipmat.R
import com.sipmat.sipmat.adapter.edgblok1.Edgblok1PelaksanaAdapter
import com.sipmat.sipmat.adapter.edgblok2.Edgblok2PelaksanaAdapter
import com.sipmat.sipmat.adapter.edgblok3.Edgblok3PelaksanaAdapter
import com.sipmat.sipmat.adapter.ffblok2.FFblok2PelaksanaAdapter
import com.sipmat.sipmat.databinding.*
import com.sipmat.sipmat.model.edgblok1.EdgBlokModel
import com.sipmat.sipmat.model.edgblok1.EdgBlokResponse
import com.sipmat.sipmat.model.edgblok2.EdgBlok2Model
import com.sipmat.sipmat.model.edgblok2.EdgBlok2Response
import com.sipmat.sipmat.model.edgblok3.EdgBlok3Model
import com.sipmat.sipmat.model.edgblok3.EdgBlok3Response
import com.sipmat.sipmat.model.ffblok2.FFBlok2Model
import com.sipmat.sipmat.model.ffblok2.FFBlok2Response
import com.sipmat.sipmat.pelaksana.ffblok2pelaksana.DetailCekFFblok2Activity
import com.sipmat.sipmat.webservice.ApiClient
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CekEdgblok3Activity : AppCompatActivity(), AnkoLogger {

    lateinit var binding: ActivityCekEdgblok3Binding
    var api = ApiClient.instance()
    private lateinit var mAdapter: Edgblok3PelaksanaAdapter
    lateinit var progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cek_edgblok3)
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
        api.edgblok3_pelaksana()
            .enqueue(object : Callback<EdgBlok3Response> {
                override fun onResponse(
                    call: Call<EdgBlok3Response>,
                    response: Response<EdgBlok3Response>
                ) {
                    try {
                        if (response.isSuccessful) {
                            loading(false)
                            if(response.body()!!.data!!.isNotEmpty()) {
                            val notesList = mutableListOf<EdgBlok3Model>()
                            val data = response.body()
                            for (hasil in data!!.data!!) {
                                notesList.add(hasil)
                                mAdapter =
                                    Edgblok3PelaksanaAdapter(notesList, this@CekEdgblok3Activity)
                                binding.rvffblokpelaksana.adapter = mAdapter
                                mAdapter.setDialog(object : Edgblok3PelaksanaAdapter.Dialog {
                                    override fun onClick(
                                        position: Int,
                                        note: EdgBlok3Model
                                    ) {
                                        val builder = AlertDialog.Builder(this@CekEdgblok3Activity)
                                        builder.setMessage("Cek edg blok 3 ? ")
                                        builder.setPositiveButton("Cek edg blok 3") { dialog, which ->
                                            val gson = Gson()
                                            val noteJson = gson.toJson(note)
                                            startActivity<DetailCekEdgblok3Activity>("cekedgblok3" to noteJson)
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

                override fun onFailure(call: Call<EdgBlok3Response>, t: Throwable) {
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