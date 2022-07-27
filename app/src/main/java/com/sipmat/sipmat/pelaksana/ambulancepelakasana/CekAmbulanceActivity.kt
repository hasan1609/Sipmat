package com.sipmat.sipmat.pelaksana.ambulancepelakasana

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.sipmat.sipmat.R
import com.sipmat.sipmat.adapter.ambulance.AmbulancePelaksanaAdapter
import com.sipmat.sipmat.adapter.damkar.DamkarPelaksanaAdapter
import com.sipmat.sipmat.adapter.edgblok2.Edgblok2PelaksanaAdapter
import com.sipmat.sipmat.databinding.ActivityCekAmbulanceBinding
import com.sipmat.sipmat.databinding.ActivityCekDamkarBinding
import com.sipmat.sipmat.databinding.ActivityCekEdgblok2Binding
import com.sipmat.sipmat.model.ambulance.AmbulanceModel
import com.sipmat.sipmat.model.ambulance.AmbulanceResponse
import com.sipmat.sipmat.model.damkar.DamkarModel
import com.sipmat.sipmat.model.damkar.DamkarResponse
import com.sipmat.sipmat.model.edgblok2.EdgBlok2Model
import com.sipmat.sipmat.model.edgblok2.EdgBlok2Response
import com.sipmat.sipmat.webservice.ApiClient
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CekAmbulanceActivity : AppCompatActivity(), AnkoLogger {

    lateinit var binding: ActivityCekAmbulanceBinding
    var api = ApiClient.instance()
    private lateinit var mAdapter: AmbulancePelaksanaAdapter
    lateinit var progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cek_ambulance)
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
        api.ambulance_pelaksana()
            .enqueue(object : Callback<AmbulanceResponse> {
                override fun onResponse(
                    call: Call<AmbulanceResponse>,
                    response: Response<AmbulanceResponse>
                ) {
                    try {
                        if (response.isSuccessful) {
                            val notesList = mutableListOf<AmbulanceModel>()
                            val data = response.body()
                            for (hasil in data!!.data!!) {
                                notesList.add(hasil)
                                mAdapter =
                                    AmbulancePelaksanaAdapter(notesList, this@CekAmbulanceActivity)
                                binding.rvffblokpelaksana.adapter = mAdapter
                                mAdapter.setDialog(object : AmbulancePelaksanaAdapter.Dialog {
                                    override fun onClick(
                                        position: Int,
                                        note: AmbulanceModel
                                    ) {
                                        val builder = AlertDialog.Builder(this@CekAmbulanceActivity)
                                        builder.setMessage("Cek Ambulance ? ")
                                        builder.setPositiveButton("Cek damkar") { dialog, which ->
                                            val gson = Gson()
                                            val noteJson = gson.toJson(note)
                                            startActivity<DetailCekAmbulanceActivity>("ambulance" to noteJson)
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

                override fun onFailure(call: Call<AmbulanceResponse>, t: Throwable) {
                    info { "dinda ${t.message}" }
                }

            })


    }

}