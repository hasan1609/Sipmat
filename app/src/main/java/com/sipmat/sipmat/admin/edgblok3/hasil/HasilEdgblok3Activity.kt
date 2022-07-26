package com.sipmat.sipmat.admin.edgblok3.hasil

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
import com.sipmat.sipmat.adapter.edgblok3.HasilEdgblok3Adapter
import com.sipmat.sipmat.admin.edgblok3.schedule.CekEdgblok3AdminActivity
import com.sipmat.sipmat.admin.ffblok1.schedule.CekFFblokAdminActivity
import com.sipmat.sipmat.databinding.ActivityHasilEdgblok1Binding
import com.sipmat.sipmat.databinding.ActivityHasilEdgblok2Binding
import com.sipmat.sipmat.databinding.ActivityHasilEdgblok3Binding
import com.sipmat.sipmat.model.edgblok1.EdgBlokModel
import com.sipmat.sipmat.model.edgblok1.EdgBlokResponse
import com.sipmat.sipmat.model.edgblok2.EdgBlok2Model
import com.sipmat.sipmat.model.edgblok2.EdgBlok2Response
import com.sipmat.sipmat.model.edgblok3.EdgBlok3Model
import com.sipmat.sipmat.model.edgblok3.EdgBlok3Response
import com.sipmat.sipmat.webservice.ApiClient
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HasilEdgblok3Activity : AppCompatActivity(), AnkoLogger {
    private lateinit var mAdapter: HasilEdgblok3Adapter
    lateinit var progressDialog: ProgressDialog
    var api = ApiClient.instance()
    var triwulan: String? = null
    lateinit var binding: ActivityHasilEdgblok3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hasil_edgblok3)
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
        api.gethasil_edgblok3(tw, tahun)
            .enqueue(object : Callback<EdgBlok3Response> {
                override fun onResponse(
                    call: Call<EdgBlok3Response>,
                    response: Response<EdgBlok3Response>
                ) {
                    try {
                        if (response.isSuccessful) {
                            val notesList = mutableListOf<EdgBlok3Model>()
                            val data = response.body()
                            if (data!!.data!!.isEmpty()) {
                                toast("data kosong")
                            } else {
                                for (hasil in data.data!!) {
                                    notesList.add(hasil)
                                    mAdapter =
                                        HasilEdgblok3Adapter(notesList, this@HasilEdgblok3Activity)
                                    binding.rvhasilffblok.adapter = mAdapter
                                    mAdapter.setDialog(object : HasilEdgblok3Adapter.Dialog {

                                        override fun onClick(position: Int, note: EdgBlok3Model) {
                                            val builder =
                                                AlertDialog.Builder(this@HasilEdgblok3Activity)
                                            builder.setMessage("Cek Edg Blok 3 ? ")
                                            builder.setPositiveButton("Cek Edg Blok 3") { dialog, which ->
                                                val gson = Gson()
                                                val noteJson = gson.toJson(note)
                                                startActivity<CekEdgblok3AdminActivity>("cekedgblok3" to noteJson)
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

                override fun onFailure(call: Call<EdgBlok3Response>, t: Throwable) {
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