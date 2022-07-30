package com.sipmat.sipmat.pelaksana.hydrantpelaksana

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.sipmat.sipmat.R
import com.sipmat.sipmat.adapter.apat.ApatPelaksanaAdapter
import com.sipmat.sipmat.adapter.hydrant.HydrantPelaksanaAdapter
import com.sipmat.sipmat.databinding.ActivityCekApatBinding
import com.sipmat.sipmat.databinding.ActivityCekHydrantBinding
import com.sipmat.sipmat.model.apat.ScheduleApatPelaksanaModel
import com.sipmat.sipmat.model.apat.ScheduleApatPelaksanaResponse
import com.sipmat.sipmat.model.hydrant.ScheduleHydrantPelaksanaModel
import com.sipmat.sipmat.model.hydrant.ScheduleHydrantPelaksanaResponse
import com.sipmat.sipmat.pelaksana.apatpelaksana.DetailCekApatActivity
import com.sipmat.sipmat.webservice.ApiClient
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CekHydrantActivity : AppCompatActivity(), AnkoLogger {

    lateinit var binding: ActivityCekHydrantBinding
    var api = ApiClient.instance()
    private lateinit var mAdapter: HydrantPelaksanaAdapter
    lateinit var progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cek_hydrant)
        binding.lifecycleOwner = this

        progressDialog = ProgressDialog(this)
    }

    override fun onStart() {
        super.onStart()
        getapatpelaksana()
    }


    fun getapatpelaksana() {
        binding.rvhydrantpelaksana.layoutManager = LinearLayoutManager(this)
        binding.rvhydrantpelaksana.setHasFixedSize(true)
        (binding.rvhydrantpelaksana.layoutManager as LinearLayoutManager).orientation =
            LinearLayoutManager.VERTICAL
        loading(true)
        api.getschedule_pelaksana_hydrant()
            .enqueue(object : Callback<ScheduleHydrantPelaksanaResponse> {
                override fun onResponse(
                    call: Call<ScheduleHydrantPelaksanaResponse>,
                    response: Response<ScheduleHydrantPelaksanaResponse>
                ) {
                    try {
                        if (response.isSuccessful) {
                            loading(false)
                            if(response.body()!!.data!!.isNotEmpty()) {
                            val notesList = mutableListOf<ScheduleHydrantPelaksanaModel>()
                            val data = response.body()
                            for (hasil in data!!.data!!) {
                                notesList.add(hasil)
                                mAdapter =
                                    HydrantPelaksanaAdapter(notesList, this@CekHydrantActivity)
                                binding.rvhydrantpelaksana.adapter = mAdapter
                                mAdapter.setDialog(object : HydrantPelaksanaAdapter.Dialog {
                                    override fun onClick(
                                        position: Int,
                                        note: ScheduleHydrantPelaksanaModel
                                    ) {
                                        val builder = AlertDialog.Builder(this@CekHydrantActivity)
                                        builder.setMessage("Cek Hydrant ? ")
                                        builder.setPositiveButton("Cek Hydrant") { dialog, which ->
                                            val gson = Gson()
                                            val noteJson = gson.toJson(note)
                                            startActivity<DetailCekHydrantActivity>("cekhydrant" to noteJson)
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

                override fun onFailure(call: Call<ScheduleHydrantPelaksanaResponse>, t: Throwable) {
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