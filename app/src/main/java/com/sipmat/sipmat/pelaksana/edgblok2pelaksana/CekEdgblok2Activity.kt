package com.sipmat.sipmat.pelaksana.edgblok2pelaksana

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.sipmat.sipmat.R
import com.sipmat.sipmat.adapter.edgblok1.Edgblok1PelaksanaAdapter
import com.sipmat.sipmat.adapter.edgblok2.Edgblok2PelaksanaAdapter
import com.sipmat.sipmat.adapter.ffblok2.FFblok2PelaksanaAdapter
import com.sipmat.sipmat.databinding.ActivityCekEdgblok1Binding
import com.sipmat.sipmat.databinding.ActivityCekEdgblok2Binding
import com.sipmat.sipmat.databinding.ActivityCekFfblok2Binding
import com.sipmat.sipmat.databinding.ActivityHasilEdgblok2Binding
import com.sipmat.sipmat.model.edgblok1.EdgBlokModel
import com.sipmat.sipmat.model.edgblok1.EdgBlokResponse
import com.sipmat.sipmat.model.edgblok2.EdgBlok2Model
import com.sipmat.sipmat.model.edgblok2.EdgBlok2Response
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

class CekEdgblok2Activity : AppCompatActivity(), AnkoLogger {

    lateinit var binding: ActivityCekEdgblok2Binding
    var api = ApiClient.instance()
    private lateinit var mAdapter: Edgblok2PelaksanaAdapter
    lateinit var progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cek_edgblok2)
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
        api.edgblok2_pelaksana()
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
                                mAdapter =
                                    Edgblok2PelaksanaAdapter(notesList, this@CekEdgblok2Activity)
                                binding.rvffblokpelaksana.adapter = mAdapter
                                mAdapter.setDialog(object : Edgblok2PelaksanaAdapter.Dialog {
                                    override fun onClick(
                                        position: Int,
                                        note: EdgBlok2Model
                                    ) {
                                        val builder = AlertDialog.Builder(this@CekEdgblok2Activity)
                                        builder.setMessage("Cek edg blok 2 ? ")
                                        builder.setPositiveButton("Cek edg blok 2") { dialog, which ->
                                            val gson = Gson()
                                            val noteJson = gson.toJson(note)
                                            startActivity<DetailCekEdgblok2Activity>("cekedgblok2" to noteJson)
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

                override fun onFailure(call: Call<EdgBlok2Response>, t: Throwable) {
                    info { "dinda ${t.message}" }
                }

            })


    }

}