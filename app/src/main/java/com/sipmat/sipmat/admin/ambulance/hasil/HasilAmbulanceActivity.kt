package com.sipmat.sipmat.admin.ambulance.hasil

import android.Manifest
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.gcacace.signaturepad.views.SignaturePad
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sipmat.sipmat.R
import com.sipmat.sipmat.adapter.ambulance.HasilAmbulanceAdapter
import com.sipmat.sipmat.adapter.damkar.HasilDamkarAdapter
import com.sipmat.sipmat.adapter.edgblok1.HasilEdgblokAdapter
import com.sipmat.sipmat.adapter.edgblok2.HasilEdgblok2Adapter
import com.sipmat.sipmat.admin.ambulance.schedule.CekAmbulanceAdminActivity
import com.sipmat.sipmat.admin.damkar.schedule.CekDamkarAdminActivity
import com.sipmat.sipmat.admin.edgblok1.schedule.CekEdgblok1AdminActivity
import com.sipmat.sipmat.admin.edgblok2.schedule.CekEdgblok2AdminActivity
import com.sipmat.sipmat.admin.ffblok1.schedule.CekFFblokAdminActivity
import com.sipmat.sipmat.databinding.*
import com.sipmat.sipmat.model.PostDataResponse
import com.sipmat.sipmat.model.ambulance.AmbulanceModel
import com.sipmat.sipmat.model.ambulance.AmbulanceResponse
import com.sipmat.sipmat.model.damkar.DamkarModel
import com.sipmat.sipmat.model.damkar.DamkarResponse
import com.sipmat.sipmat.model.edgblok1.EdgBlokModel
import com.sipmat.sipmat.model.edgblok1.EdgBlokResponse
import com.sipmat.sipmat.model.edgblok2.EdgBlok2Model
import com.sipmat.sipmat.model.edgblok2.EdgBlok2Response
import com.sipmat.sipmat.webservice.ApiClient
import kotlinx.android.synthetic.main.activity_hasil_apar.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File

class HasilAmbulanceActivity : AppCompatActivity(), AnkoLogger {
    private lateinit var mAdapter: HasilAmbulanceAdapter
    lateinit var progressDialog: ProgressDialog
    var api = ApiClient.instance()
    var triwulan: String? = null
    lateinit var binding: ActivityHasilAmbulanceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hasil_ambulance)
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
        api.gethasil_ambulance(tw, tahun)
            .enqueue(object : Callback<AmbulanceResponse> {
                override fun onResponse(
                    call: Call<AmbulanceResponse>,
                    response: Response<AmbulanceResponse>
                ) {
                    try {
                        if (response.isSuccessful) {
                            val notesList = mutableListOf<AmbulanceModel>()
                            val data = response.body()
                            if (data!!.data!!.isEmpty()) {
                                toast("data kosong")
                            } else {
                                for (hasil in data.data!!) {
                                    notesList.add(hasil)
                                    mAdapter =
                                        HasilAmbulanceAdapter(notesList, this@HasilAmbulanceActivity)
                                    binding.rvhasilffblok.adapter = mAdapter
                                    mAdapter.setDialog(object : HasilAmbulanceAdapter.Dialog {

                                        override fun onClick(position: Int, note: AmbulanceModel) {
                                            val builder =
                                                AlertDialog.Builder(this@HasilAmbulanceActivity)
                                            builder.setMessage("Cek Mobil Ambulane ? ")
                                            builder.setPositiveButton("Cek Ambulance") { dialog, which ->
                                                val gson = Gson()
                                                val noteJson = gson.toJson(note)
                                                startActivity<CekAmbulanceAdminActivity>("ambulance" to noteJson)
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

                override fun onFailure(call: Call<AmbulanceResponse>, t: Throwable) {
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