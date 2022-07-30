package com.sipmat.sipmat.pelaksana.damkarpelaksana

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.sipmat.sipmat.R
import com.sipmat.sipmat.adapter.damkar.DamkarPelaksanaAdapter
import com.sipmat.sipmat.adapter.edgblok2.Edgblok2PelaksanaAdapter
import com.sipmat.sipmat.databinding.ActivityCekDamkarBinding
import com.sipmat.sipmat.databinding.ActivityCekEdgblok2Binding
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

class CekDamkarActivity : AppCompatActivity(), AnkoLogger {

    lateinit var binding: ActivityCekDamkarBinding
    var api = ApiClient.instance()
    private lateinit var mAdapter: DamkarPelaksanaAdapter
    lateinit var progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cek_damkar)
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
        api.damkar_pelaksana()
            .enqueue(object : Callback<DamkarResponse> {
                override fun onResponse(
                    call: Call<DamkarResponse>,
                    response: Response<DamkarResponse>
                ) {
                    try {
                        if (response.isSuccessful) {
                            loading(false)
                            if(response.body()!!.data!!.isNotEmpty()) {
                            val notesList = mutableListOf<DamkarModel>()
                            val data = response.body()
                            for (hasil in data!!.data!!) {
                                notesList.add(hasil)
                                mAdapter =
                                    DamkarPelaksanaAdapter(notesList, this@CekDamkarActivity)
                                binding.rvffblokpelaksana.adapter = mAdapter
                                mAdapter.setDialog(object : DamkarPelaksanaAdapter.Dialog {
                                    override fun onClick(
                                        position: Int,
                                        note: DamkarModel
                                    ) {
                                        val builder = AlertDialog.Builder(this@CekDamkarActivity)
                                        builder.setMessage("Cek damkar ? ")
                                        builder.setPositiveButton("Cek damkar") { dialog, which ->
                                            val gson = Gson()
                                            val noteJson = gson.toJson(note)
                                            startActivity<DetailCekDamkarActivity>("cekdamkar" to noteJson)
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

                override fun onFailure(call: Call<DamkarResponse>, t: Throwable) {
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