package com.sipmat.sipmat.admin.ffblok2.hasil

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
import com.sipmat.sipmat.adapter.ffblok.HasilFFblokAdapter
import com.sipmat.sipmat.adapter.ffblok2.HasilFFblok2Adapter
import com.sipmat.sipmat.admin.ffblok1.schedule.CekFFblokAdminActivity
import com.sipmat.sipmat.admin.ffblok2.schedule.CekFFblok2AdminActivity
import com.sipmat.sipmat.databinding.ActivityHasilFfblok2Binding
import com.sipmat.sipmat.databinding.ActivityHasilFfblokBinding
import com.sipmat.sipmat.model.ffblok.FFBlokModel
import com.sipmat.sipmat.model.ffblok.FFBlokResponse
import com.sipmat.sipmat.model.ffblok2.FFBlok2Model
import com.sipmat.sipmat.model.ffblok2.FFBlok2Response
import com.sipmat.sipmat.webservice.ApiClient
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HasilFFblok2Activity : AppCompatActivity(), AnkoLogger {
    private lateinit var mAdapter: HasilFFblok2Adapter
    lateinit var progressDialog: ProgressDialog
    var api = ApiClient.instance()
    var triwulan: String? = null
    lateinit var binding: ActivityHasilFfblok2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hasil_ffblok2)
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
        api.gethasil_ffblok2(tw, tahun)
            .enqueue(object : Callback<FFBlok2Response> {
                override fun onResponse(
                    call: Call<FFBlok2Response>,
                    response: Response<FFBlok2Response>
                ) {
                    try {
                        if (response.isSuccessful) {
                            val notesList = mutableListOf<FFBlok2Model>()
                            val data = response.body()
                            if (data!!.data!!.isEmpty()) {
                                toast("data kosong")
                            } else {
                                for (hasil in data.data!!) {
                                    notesList.add(hasil)
                                    mAdapter =
                                        HasilFFblok2Adapter(notesList, this@HasilFFblok2Activity)
                                    binding.rvhasilffblok.adapter = mAdapter
                                    mAdapter.setDialog(object : HasilFFblok2Adapter.Dialog {

                                        override fun onClick(position: Int, note: FFBlok2Model) {
                                            val builder =
                                                AlertDialog.Builder(this@HasilFFblok2Activity)
                                            builder.setMessage("Cek ffblok 2 ? ")
                                            builder.setPositiveButton("Cek ffblok 2") { dialog, which ->
                                                val gson = Gson()
                                                val noteJson = gson.toJson(note)
                                                startActivity<CekFFblok2AdminActivity>("cekffblok2" to noteJson)
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

                override fun onFailure(call: Call<FFBlok2Response>, t: Throwable) {
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