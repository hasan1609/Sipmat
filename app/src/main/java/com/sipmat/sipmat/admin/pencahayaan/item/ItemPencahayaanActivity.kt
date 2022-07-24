package com.sipmat.sipmat.admin.pencahayaan.item

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.sipmat.sipmat.R
import com.sipmat.sipmat.adapter.pencahayaan.ItemPencahayaanAdapter
import com.sipmat.sipmat.databinding.ActivityItemPencahayaanBinding
import com.sipmat.sipmat.model.PostDataResponse
import com.sipmat.sipmat.model.pencahayaan.PencahayaanModel
import com.sipmat.sipmat.model.pencahayaan.PencahayaanResponse
import com.sipmat.sipmat.webservice.ApiClient
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ItemPencahayaanActivity : AppCompatActivity(), AnkoLogger {
    lateinit var binding : ActivityItemPencahayaanBinding
    private lateinit var mAdapter: ItemPencahayaanAdapter
    var api = ApiClient.instance()
    lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_item_pencahayaan)
        binding.lifecycleOwner = this
        progressDialog = ProgressDialog(this)

        binding.btntambah.setOnClickListener {
            startActivity<TambahPencahayaanActivity>()
        }

    }

    override fun onStart() {
        super.onStart()
        getpencahayaan()
    }

    fun getpencahayaan(){
        binding.rvitempencahayaan.layoutManager = LinearLayoutManager(this)
        binding.rvitempencahayaan.setHasFixedSize(true)
        (binding.rvitempencahayaan.layoutManager as LinearLayoutManager).orientation =
            LinearLayoutManager.VERTICAL
        api.getpencahayaan()
            .enqueue(object : Callback<PencahayaanResponse> {
                override fun onResponse(
                    call: Call<PencahayaanResponse>,
                    response: Response<PencahayaanResponse>
                ) {
                    try {
                        if (response.isSuccessful) {
                            val notesList = mutableListOf<PencahayaanModel>()
                            val data = response.body()
                            for (hasil in data!!.data!!) {
                                notesList.add(hasil)
                                mAdapter = ItemPencahayaanAdapter(notesList, this@ItemPencahayaanActivity)
                                binding.rvitempencahayaan.adapter = mAdapter
                                mAdapter.setDialog(object : ItemPencahayaanAdapter.Dialog{
                                    override fun onClick(position: Int, note : PencahayaanModel) {
                                        val builder = AlertDialog.Builder(this@ItemPencahayaanActivity)
                                        builder.setMessage("Hapus pencahayaan ? ")
                                        builder.setPositiveButton("Edit pencahayaan") { dialog, which ->
                                            val gson = Gson()
                                            val noteJson = gson.toJson(note)
                                            startActivity<TambahPencahayaanActivity>("PencahayaanModel" to noteJson)

                                        }


                                        builder.setNegativeButton("Hapus pencahayaan ?") { dialog, which ->
                                            api.deletepencahayaan(note.id!!).enqueue(object :
                                                Callback<PostDataResponse> {
                                                override fun onResponse(
                                                    call: Call<PostDataResponse>,
                                                    response: Response<PostDataResponse>
                                                ) {
                                                    try {
                                                        if (response.body()!!.sukses == 1) {
                                                            loading(false)
                                                            toast("hapus pencahayaan berhasil")
                                                            onStart()
                                                        } else {
                                                            loading(false)
                                                            toast("hapus pencahayaan gagal")
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

                override fun onFailure(call: Call<PencahayaanResponse>, t: Throwable) {
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