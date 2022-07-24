package com.sipmat.sipmat.admin.hydrant.item

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.sipmat.sipmat.R
import com.sipmat.sipmat.adapter.hydrant.ItemHydrantAdapter
import com.sipmat.sipmat.databinding.ActivityItemHydrantBinding
import com.sipmat.sipmat.model.PostDataResponse
import com.sipmat.sipmat.model.hydrant.HydrantModel
import com.sipmat.sipmat.model.hydrant.ItemHydrantResponse
import com.sipmat.sipmat.webservice.ApiClient
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ItemHydrantActivity : AppCompatActivity(),AnkoLogger {
    lateinit var binding : ActivityItemHydrantBinding
    private lateinit var mAdapter: ItemHydrantAdapter
    var api = ApiClient.instance()
    lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_item_hydrant)
        binding.lifecycleOwner = this
        progressDialog = ProgressDialog(this)

        binding.btntambah.setOnClickListener {
            startActivity<TambahHydrantActivity>()
        }

    }

    override fun onStart() {
        super.onStart()
        gethydrant()
    }

    fun gethydrant(){
        binding.rvitemhydrant.layoutManager = LinearLayoutManager(this)
        binding.rvitemhydrant.setHasFixedSize(true)
        (binding.rvitemhydrant.layoutManager as LinearLayoutManager).orientation =
            LinearLayoutManager.VERTICAL
        api.itemhydrant()
            .enqueue(object : Callback<ItemHydrantResponse> {
                override fun onResponse(
                    call: Call<ItemHydrantResponse>,
                    response: Response<ItemHydrantResponse>
                ) {
                    try {
                        if (response.isSuccessful) {
                            val notesList = mutableListOf<HydrantModel>()
                            val data = response.body()
                            for (hasil in data!!.data!!) {
                                notesList.add(hasil)
                                mAdapter = ItemHydrantAdapter(notesList, this@ItemHydrantActivity)
                                binding.rvitemhydrant.adapter = mAdapter
                                mAdapter.setDialog(object : ItemHydrantAdapter.Dialog{
                                    override fun onClick(position: Int, note : HydrantModel) {
                                        val builder = AlertDialog.Builder(this@ItemHydrantActivity)
                                        builder.setMessage("Hapus Hydrant ? ")
                                        builder.setPositiveButton("Edit Hydrant") { dialog, which ->
                                            val gson = Gson()
                                            val noteJson = gson.toJson(note)
                                            startActivity<TambahHydrantActivity>("HydrantModel" to noteJson)

                                        }


                                        builder.setNegativeButton("Hapus Hydrant ?") { dialog, which ->
                                            api.deletehydrant(note.id!!).enqueue(object :
                                                Callback<PostDataResponse> {
                                                override fun onResponse(
                                                    call: Call<PostDataResponse>,
                                                    response: Response<PostDataResponse>
                                                ) {
                                                    try {
                                                        if (response.body()!!.sukses == 1) {
                                                            loading(false)
                                                            toast("hapus Hydrant berhasil")
                                                            onStart()
                                                        } else {
                                                            loading(false)
                                                            toast("hapus Hydrant gagal")
                                                        }


                                                    }catch (e : Exception){
                                                        progressDialog.dismiss()
                                                        info { "dinda ${e.message }${response.code()} " }
                                                    }

                                                }

                                                override fun onFailure(call: Call<PostDataResponse>, t: Throwable) {
                                                    loading(false)
                                                    toast("kesalahan jaringan")

                                                }

                                            })
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

                override fun onFailure(call: Call<ItemHydrantResponse>, t: Throwable) {
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