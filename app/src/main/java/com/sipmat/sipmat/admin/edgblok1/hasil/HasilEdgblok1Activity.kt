package com.sipmat.sipmat.admin.edgblok1.hasil

import android.Manifest
import android.app.ProgressDialog
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.sipmat.sipmat.R
import com.sipmat.sipmat.adapter.edgblok1.HasilEdgblokAdapter
import com.sipmat.sipmat.admin.edgblok1.schedule.CekEdgblok1AdminActivity
import com.sipmat.sipmat.admin.ffblok1.schedule.CekFFblokAdminActivity
import com.sipmat.sipmat.databinding.ActivityHasilEdgblok1Binding
import com.sipmat.sipmat.model.edgblok1.EdgBlokModel
import com.sipmat.sipmat.model.edgblok1.EdgBlokResponse
import com.sipmat.sipmat.webservice.ApiClient
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HasilEdgblok1Activity : AppCompatActivity(), AnkoLogger {
    private lateinit var mAdapter: HasilEdgblokAdapter
    lateinit var progressDialog: ProgressDialog
    var api = ApiClient.instance()
    var triwulan: String? = null
    lateinit var binding: ActivityHasilEdgblok1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hasil_edgblok1)
        binding.lifecycleOwner = this
        progressDialog = ProgressDialog(this)

        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ), PackageManager.PERMISSION_GRANTED
        )

        binding.btncari.setOnClickListener {
            val tahun = binding.edttahun.text.toString().trim()
            if (tahun.isNotEmpty()) {
                gethasil(binding.spntw.selectedItem.toString(), tahun)
            }
        }
    }

    fun gethasil(tw: String, tahun: String) {
        binding.rvhasilffblok.layoutManager = LinearLayoutManager(this)
        binding.rvhasilffblok.setHasFixedSize(true)
        (binding.rvhasilffblok.layoutManager as LinearLayoutManager).orientation =
            LinearLayoutManager.VERTICAL
        api.gethasil_edgblok1(tw, tahun)
            .enqueue(object : Callback<EdgBlokResponse> {
                override fun onResponse(
                    call: Call<EdgBlokResponse>,
                    response: Response<EdgBlokResponse>
                ) {
                    try {
                        if (response.isSuccessful) {
                            val notesList = mutableListOf<EdgBlokModel>()
                            val data = response.body()
                            if (data!!.data!!.isEmpty()) {
                                toast("data kosong")
                            } else {
                                for (hasil in data.data!!) {
                                    notesList.add(hasil)
                                    mAdapter =
                                        HasilEdgblokAdapter(notesList, this@HasilEdgblok1Activity)
                                    binding.rvhasilffblok.adapter = mAdapter
                                    mAdapter.setDialog(object : HasilEdgblokAdapter.Dialog {

                                        override fun onClick(position: Int, note: EdgBlokModel) {
                                            val builder =
                                                AlertDialog.Builder(this@HasilEdgblok1Activity)
                                            builder.setMessage("Cek Edg Blok 1 ? ")
                                            builder.setPositiveButton("Cek Edg Blok 1") { dialog, which ->
                                                val gson = Gson()
                                                val noteJson = gson.toJson(note)
                                                startActivity<CekEdgblok1AdminActivity>("cekedgblok1" to noteJson)
                                            }


                                            builder.setNegativeButton("Cancel ?") { dialog, which ->

                                            }

                                            builder.show()

                                        }

                                    })
                                    mAdapter.notifyDataSetChanged()
                                }

                            }
                        } else {
                            toast("gagal mendapatkan response")
                        }
                    } catch (e: Exception) {
                        info { "dinda ${e.message}" }
                    }
                }

                override fun onFailure(call: Call<EdgBlokResponse>, t: Throwable) {
                    info { "dinda ${t.message}" }
                }

            })


    }


    fun loading(status: Boolean) {
        if (status) {
            progressDialog.setTitle("Loading...")
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()
        } else {
            progressDialog.dismiss()
        }
    }

}