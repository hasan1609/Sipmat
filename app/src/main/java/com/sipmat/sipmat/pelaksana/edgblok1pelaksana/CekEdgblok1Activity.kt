package com.sipmat.sipmat.pelaksana.edgblok1pelaksana

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
import com.sipmat.sipmat.adapter.ffblok2.FFblok2PelaksanaAdapter
import com.sipmat.sipmat.databinding.ActivityCekEdgblok1Binding
import com.sipmat.sipmat.model.edgblok1.EdgBlokModel
import com.sipmat.sipmat.model.edgblok1.EdgBlokResponse
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

class CekEdgblok1Activity : AppCompatActivity(), AnkoLogger {

    lateinit var binding: ActivityCekEdgblok1Binding
    var api = ApiClient.instance()
    private lateinit var mAdapter: Edgblok1PelaksanaAdapter
    lateinit var progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cek_edgblok1)
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
        api.edgblok1_pelaksana()
            .enqueue(object : Callback<EdgBlokResponse> {
                override fun onResponse(
                    call: Call<EdgBlokResponse>,
                    response: Response<EdgBlokResponse>
                ) {
                    try {
                        if (response.isSuccessful) {
                            loading(false)
                            if(response.body()!!.data!!.isNotEmpty()) {
                            val notesList = mutableListOf<EdgBlokModel>()
                            val data = response.body()
                            for (hasil in data!!.data!!) {
                                notesList.add(hasil)
                                mAdapter =
                                    Edgblok1PelaksanaAdapter(notesList, this@CekEdgblok1Activity)
                                binding.rvffblokpelaksana.adapter = mAdapter
                                mAdapter.setDialog(object : Edgblok1PelaksanaAdapter.Dialog {
                                    override fun onClick(
                                        position: Int,
                                        note: EdgBlokModel
                                    ) {
                                        val builder = AlertDialog.Builder(this@CekEdgblok1Activity)
                                        builder.setMessage("Cek edg blok 1 ? ")
                                        builder.setPositiveButton("Cek edg blok 1") { dialog, which ->
                                            val gson = Gson()
                                            val noteJson = gson.toJson(note)
                                            startActivity<DetailCekEdgblok1Activity>("cekedgblok1" to noteJson)
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

                override fun onFailure(call: Call<EdgBlokResponse>, t: Throwable) {
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