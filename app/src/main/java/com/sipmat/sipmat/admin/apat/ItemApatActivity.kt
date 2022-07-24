package com.sipmat.sipmat.admin.apat

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.sipmat.sipmat.R
import com.sipmat.sipmat.adapter.apat.ApatAdapter
import com.sipmat.sipmat.admin.apar.TambahAparActivity
import com.sipmat.sipmat.databinding.ActivityItemApatBinding
import com.sipmat.sipmat.model.AparResponse
import com.sipmat.sipmat.model.PostDataResponse
import com.sipmat.sipmat.model.apat.ApatModel
import com.sipmat.sipmat.model.apat.ApatResponse
import com.sipmat.sipmat.webservice.ApiClient
import kotlinx.android.synthetic.main.activity_item_apar.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ItemApatActivity : AppCompatActivity(),AnkoLogger {
    lateinit var binding : ActivityItemApatBinding
    private lateinit var mAdapter: ApatAdapter
    var api = ApiClient.instance()
    lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_item_apat)
        binding.lifecycleOwner = this
        progressDialog = ProgressDialog(this)

        binding.btntambah.setOnClickListener {
            startActivity<TambahApatActivity>()
        }

    }

    override fun onStart() {
        super.onStart()
        getapat()
    }

    fun getapat(){
        binding.rvitemapat.layoutManager = LinearLayoutManager(this)
        binding.rvitemapat.setHasFixedSize(true)
        (binding.rvitemapat.layoutManager as LinearLayoutManager).orientation =
            LinearLayoutManager.VERTICAL
        api.getapat()
            .enqueue(object : Callback<ApatResponse> {
                override fun onResponse(
                    call: Call<ApatResponse>,
                    response: Response<ApatResponse>
                ) {
                    try {
                        if (response.isSuccessful) {
                            val notesList = mutableListOf<ApatModel>()
                            val data = response.body()
                            for (hasil in data!!.data!!) {
                                notesList.add(hasil)
                                mAdapter = ApatAdapter(notesList, this@ItemApatActivity)
                                binding.rvitemapat.adapter = mAdapter
                                mAdapter.setDialog(object : ApatAdapter.Dialog{
                                    override fun onClick(position: Int, note : ApatModel) {
                                        val builder = AlertDialog.Builder(this@ItemApatActivity)
                                        builder.setTitle("Data APAT")
                                        builder.setMessage("Hapus gaji bulan ini ? ")
                                        builder.setPositiveButton("Edit APAT") { dialog, which ->
                                            val gson = Gson()
                                            val noteJson = gson.toJson(note)
                                            startActivity<TambahApatActivity>("apatmodel" to noteJson)

                                        }


                                        builder.setNegativeButton("Hapus APAT ?") { dialog, which ->
                                            api.hapusapat(note.id!!).enqueue(object :
                                                Callback<PostDataResponse> {
                                                override fun onResponse(
                                                    call: Call<PostDataResponse>,
                                                    response: Response<PostDataResponse>
                                                ) {
                                                    try {
                                                        if (response.body()!!.sukses == 1) {
                                                            loading(false)
                                                            toast("hapus apat berhasil")
                                                            onStart()
                                                        } else {
                                                            loading(false)
                                                            toast("hapus apat gagal")
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

                override fun onFailure(call: Call<ApatResponse>, t: Throwable) {
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