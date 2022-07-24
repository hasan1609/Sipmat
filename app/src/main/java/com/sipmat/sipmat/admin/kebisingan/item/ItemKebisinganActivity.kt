package com.sipmat.sipmat.admin.kebisingan.item

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.sipmat.sipmat.R
import com.sipmat.sipmat.adapter.kebisingan.ItemKebisinganAdapter
import com.sipmat.sipmat.databinding.ActivityItemKebisinganBinding
import com.sipmat.sipmat.model.PostDataResponse
import com.sipmat.sipmat.model.kebisingan.KebisinganModel
import com.sipmat.sipmat.model.kebisingan.KebisinganResponse
import com.sipmat.sipmat.webservice.ApiClient
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ItemKebisinganActivity : AppCompatActivity(),AnkoLogger {
    lateinit var binding : ActivityItemKebisinganBinding
    private lateinit var mAdapter: ItemKebisinganAdapter
    var api = ApiClient.instance()
    lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_item_kebisingan)
        binding.lifecycleOwner = this
        progressDialog = ProgressDialog(this)

        binding.btntambah.setOnClickListener {
            startActivity<TambahKebisinganActivity>()
        }

    }

    override fun onStart() {
        super.onStart()
        getkebisingan()
    }

    fun getkebisingan(){
        binding.rvitemkebisingan.layoutManager = LinearLayoutManager(this)
        binding.rvitemkebisingan.setHasFixedSize(true)
        (binding.rvitemkebisingan.layoutManager as LinearLayoutManager).orientation =
            LinearLayoutManager.VERTICAL
        api.getkebisingan()
            .enqueue(object : Callback<KebisinganResponse> {
                override fun onResponse(
                    call: Call<KebisinganResponse>,
                    response: Response<KebisinganResponse>
                ) {
                    try {
                        if (response.isSuccessful) {
                            val notesList = mutableListOf<KebisinganModel>()
                            val data = response.body()
                            for (hasil in data!!.data!!) {
                                notesList.add(hasil)
                                mAdapter = ItemKebisinganAdapter(notesList, this@ItemKebisinganActivity)
                                binding.rvitemkebisingan.adapter = mAdapter
                                mAdapter.setDialog(object : ItemKebisinganAdapter.Dialog{
                                    override fun onClick(position: Int, note : KebisinganModel) {
                                        val builder = AlertDialog.Builder(this@ItemKebisinganActivity)
                                        builder.setMessage("Hapus kebisingan ? ")
                                        builder.setPositiveButton("Edit kebisingan") { dialog, which ->
                                            val gson = Gson()
                                            val noteJson = gson.toJson(note)
                                            startActivity<TambahKebisinganActivity>("KebisinganModel" to noteJson)

                                        }


                                        builder.setNegativeButton("Hapus kebisingan ?") { dialog, which ->
                                            api.deletekebisingan(note.id!!).enqueue(object :
                                                Callback<PostDataResponse> {
                                                override fun onResponse(
                                                    call: Call<PostDataResponse>,
                                                    response: Response<PostDataResponse>
                                                ) {
                                                    try {
                                                        if (response.body()!!.sukses == 1) {
                                                            loading(false)
                                                            toast("hapus kebisingan berhasil")
                                                            onStart()
                                                        } else {
                                                            loading(false)
                                                            toast("hapus kebisingan gagal")
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

                override fun onFailure(call: Call<KebisinganResponse>, t: Throwable) {
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