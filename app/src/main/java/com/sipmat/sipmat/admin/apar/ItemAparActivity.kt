package com.sipmat.sipmat.admin.apar

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.sipmat.sipmat.R
import com.sipmat.sipmat.adapter.AparAdapter
import com.sipmat.sipmat.model.AparModel
import com.sipmat.sipmat.model.AparResponse
import com.sipmat.sipmat.model.PostDataResponse
import com.sipmat.sipmat.webservice.ApiClient
import kotlinx.android.synthetic.main.activity_item_apar.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ItemAparActivity : AppCompatActivity(),AnkoLogger {
    private lateinit var mAdapter: AparAdapter
    var api = ApiClient.instance()
    lateinit var progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_apar)
        progressDialog = ProgressDialog(this)

        btntambah.setOnClickListener {
            startActivity<TambahAparActivity>()
        }
    }

    override fun onStart() {
        super.onStart()
        getapar()
    }

    fun getapar(){
        rvitemapar.layoutManager = LinearLayoutManager(this)
        rvitemapar.setHasFixedSize(true)
        (rvitemapar.layoutManager as LinearLayoutManager).orientation =
            LinearLayoutManager.VERTICAL
        api.getapar()
            .enqueue(object : Callback<AparResponse> {
                override fun onResponse(
                    call: Call<AparResponse>,
                    response: Response<AparResponse>
                ) {
                    try {
                        if (response.isSuccessful) {
                            val notesList = mutableListOf<AparModel>()
                            val data = response.body()
                            for (hasil in data!!.data!!) {
                                notesList.add(hasil)
                                mAdapter = AparAdapter(notesList, this@ItemAparActivity)
                                rvitemapar.adapter = mAdapter
                                mAdapter.setDialog(object : AparAdapter.Dialog{
                                    override fun onClick(position: Int, note : AparModel) {
                                        val builder = AlertDialog.Builder(this@ItemAparActivity)
                                        builder.setTitle("Data APAR")
                                        builder.setMessage("Hapus gaji bulan ini ? ")
                                        builder.setPositiveButton("Edit APAR") { dialog, which ->
                                            startActivity<TambahAparActivity>(
                                                "jenis" to note.jenis,
                                                "id" to note.id,
                                                "kode" to note.kode,
                                                "lokasi" to note.lokasi,
                                                "tanggal" to note.tglPengisian
                                            )
                                        }


                                        builder.setNegativeButton("Hapus APAR ?") { dialog, which ->
                                            api.hapusapar(note.id!!).enqueue(object : Callback<PostDataResponse> {
                                                override fun onResponse(
                                                    call: Call<PostDataResponse>,
                                                    response: Response<PostDataResponse>
                                                ) {
                                                    try {
                                                        if (response.body()!!.sukses == 1) {
                                                            loading(false)
                                                            toast("hapus apar berhasil")
                                                            onStart()
                                                        } else {
                                                            loading(false)
                                                            toast("hapus apar gagal")
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

                override fun onFailure(call: Call<AparResponse>, t: Throwable) {
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